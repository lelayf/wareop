(ns wareop.views.topology.locations.jdbc.create
  (:use noir.core
        hiccup.core
        hiccup.page-helpers
        hiccup.form-helpers)
  (:require [noir.session :as session]
            [noir.validation :as vali]
            [noir.response :as resp]
            [clojure.string :as string]
            [clojure.contrib.str-utils :as stru]
            [clojure.java.jdbc :as jdbc]
            [wareop.models.connections :as connections]
            [wareop.models.users :as users]
            [wareop.views.common :as common]
            [wareop.views.topology.main :as topo-main]))

;; IDEA
;; use this to build paths based on namespace :
;; (clojure.string/join "/" (vec (concat ["/app"] (pop (clojure.string/split (str (ns-name *ns*)) #"\.")))))


;; Links
(def bc [{:url "/app" :text "Home"}
         {:url "/app/topology" :text "Topology"}
         {:url "/app/topology/locations" :text "Locations"}
         {:url "/app/topology/locations/jdbc" :text "JDBC" :cls "active"}])

(def sidebar-jdbc {:prefix "/app/topology/locations/jdbc"
                   :links [{:url "list" :text "List Connections"}
                           {:url "create" :text "Create Connection"}]})

;; Partials

(defpartial sidebar []
            [:h5 "JDBC"]
              [:ul
               (common/link-items sidebar-jdbc)])

(defpartial connection-fields [{:keys [id name classname port sid ip user pass db context]}]
             [:div.clearfix (label "classname" "JDBC driver class name")
              [:div.input (text-field "classname" "oracle.jdbc.driver.OracleDriver")]]
             [:div.clearfix (label "subprotocol" "JDBC driver subprotocol")
              [:div.input (text-field "subprotocol" "oracle:thin")]]
             [:div.clearfix (label "ipAddress" "IP address or host")
              [:div.input (text-field "ipAddress" ip)]])



;; Locations - CRUD

(defpage "/app/topology/locations/jdbc/create" {:as conn}
    (common/app-layout bc
      (sidebar)
      [:h3 "Create new JDBC connection"]
      [:ul.tabs {:data-tabs "tabs" }
        [:li.active [:a {:href "#oracle"} "Oracle"]]
        [:li [:a {:href "#mysql"} "MySQL"]]]
      [:div.tab-content {:id "my-tab-content"}                 
        [:div {:id "oracle" :class "tab-pane active"}             
            (form-to [:post "/app/topology/locations/jdbc/create"]
                [:fieldset
                  (connection-fields conn)
                  [:div.actions (submit-button {:class "btn primary submit"} "Create")]])]
       [:div {:id "mysql" :class "tab-pane"}
         "MySQL"]]
       ))

(defpage [:post "/app/topology/locations/jdbc/create"] {:as conn}
      (if (connections/create! conn)
        (resp/redirect "/app/topology/locations/jdbc")
        (render "/app/topology/locations/jdbc/create" conn)))

(defn db-spec
      "Create database specification map from inputs"
      [driver url user pw]
      (let [url-parts (.split #":" url)]
                { :classname driver
                  :subprotocol (second url-parts)
                  :subname (stru/str-join ":" (rest (rest url-parts)))
                  :user user
                  :password pw }))

(defn get-column-names
  "Take database spec, return all column names from the database metadata"
  [db]
  (jdbc/with-connection db
                (into #{}
                  (map #(str (% :table_name) "." (% :column_name))
                    (resultset-seq (->
                                      (jdbc/connection)
                                      (.getMetaData)
                                      (.getColumns nil nil nil "%")))))))



