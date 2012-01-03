(ns wareop.views.common
  (use noir.core
       hiccup.core
       hiccup.page-helpers))

(defn include-less
    "Include a list of external less stylesheet files."
    [& styles]
    (for [style styles]
          [:link {:href style, :rel "stylesheet/less"}]))

;; Links and includes
(def main-links {:prefix "/app"
                  :links [{:url "topology" :text "Topology"}
                          {:url "analytics" :text "Analytics"}
                          {:url "actions" :text "Actions"}
                          {:url "admin" :text "Admin"}]})


(def includes {
               :jquery (include-js "http://ajax.googleapis.com/ajax/libs/jquery/1.6.1/jquery.min.js")
               :bootstrap (include-less "/lib/bootstrap.less")
               :less (include-js "/js/less.js")
               :bootstrap-alerts (include-js "/js/bootstrap-alerts.js")
               :d3 (include-js "/js/d3.min.js")
               :common (include-css "/css/common.css")})

;; Helper partials

(defpartial build-head [incls]
            [:head
             [:title "Wareop - Next best analytics and actions"]
             (map #(get includes %) incls)])

; To be used in a map call with a simple links hashmap
(defpartial link-item [{:keys [url cls text]}]
            [:li
             (link-to {:class cls} url text)])

; Processes the evolved version of links hashmaps which hold prefix separately to avoid redundancy
(defpartial link-items [{:keys [prefix links]}]
            (map (fn [{:keys [url cls text]}] [:li (link-to {:class cls} (str prefix "/" url) text)]) links))

(defpartial empty-row [n]
            (for [i (range n)] [:div.row [:div.span16 "&nbsp;"]]))

;; Layouts

; Used by loggedin home page to display topnav and dashboards

(defpartial main-layout [& content]
            (html5
              (build-head [:bootstrap :less :common :d3])
              [:body
               [:div.topbar
                [:div.topbar-inner
                ;[:div.fill {:style "height:46px;padding-top:6px"}
                 [:div.container-fluid
                  [:a.brand {:href "/app"} "wareop"]
                  [:ul.nav
                    (link-items main-links)]
                  [:p.pull-right
                    [:a {:href "/app/logout" :class "btn small danger"} "Logout"]]
                  ]]]
               content]))

; Used by all app modules
; sidebar + content

(defpartial app-layout [sidebar & content]
            (html5
              (build-head [:bootstrap :less :common])
              [:body
               [:div.topbar
                [:div.topbar-inner
                 [:div.container-fluid
                  [:a.brand {:href "/app"} "wareop"]
                  [:ul.nav
                   (link-items main-links)]
                  [:p.pull-right
                  [:a {:href "/app/logout" :class "btn small danger"} "Logout"]]
                  ]]]
               [:div.container-fluid
                [:div.sidebar
                 [:div.well
                  sidebar]]
                [:div.content
                  content]]]))


; Used by login page 
; no side bar 

(defpartial home-layout [& content]
              (html5
                (build-head [:bootstrap :less :jquery :bootstrap-alerts])
                [:body
                  [:div.topbar
                    [:div.topbar-inner ;{:style "height:46px;padding-top:6px"}
                      [:div.container
                        [:a.brand {:href "#"} "wareop"]]]]
                        content]))
                                  

