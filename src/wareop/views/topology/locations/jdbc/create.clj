(ns wareop.views.topology.locations.jdbc.create
  (:use noir.core
        hiccup.core
        hiccup.page-helpers
        hiccup.form-helpers)
  (:require [noir.session :as session]
            [noir.validation :as vali]
            [noir.response :as resp]
            [clojure.string :as string]
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

(defpartial connection-fields [{:keys [id name classname subprotocol port sid ip user pass db context]}]
             [:div.clearfix (label "driverSelect" "Select a JDBC Driver")
              [:div.input (drop-down "driverSelect"
                                     ["MySQL" "PostgreSQL" "Oracle"])]]
             [:div.clearfix (label "ipAddress" "IP address or host")
              [:div.input (text-field "ipAddress" ip)]])

;; Locations - CRUD

(defpage "/app/topology/locations/jdbc/create" {:as conn}
    (common/app-layout bc 
      (sidebar)
      [:h3 "Create new JDBC connection"]
      [:ul.tabs {:data-tabs "tabs" }
        [:li {:class ""} (link-to "#oracle" "Oracle")]
        [:li {:class ""} (link-to "#mysql" "MySQL")]]
      [:div.tab-content {:id "my-tab-content"}                 
        [:div {:id "oracle" :class "tab-pane active"}
          [:div.span12                 
            (form-to [:post "/app/topology/locations/jdbc/create"]
                [:fieldset
                  (connection-fields conn)
                  [:div.actions (submit-button {:class "btn primary submit"} "Create")]])]]
       [:div {:id "mysql" :class "tab-pane"} "MySQL"]
       ]))

(defpage [:post "/app/topology/locations/jdbc/create"] {:as conn}
      (if (connections/create! conn)
        (resp/redirect "/app/topology/locations/jdbc")
        (render "/app/topology/locations/jdbc/create" conn)))


