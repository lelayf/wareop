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
(def main-links [{:url "/app/datasources" :text "Data Sources"}
                 {:url "/app/mappings" :text "Business Mappings"}
                 {:url "/app/reports" :text "Reporting"}
                 {:url "/app/emailing" :text "Email Marketing"}
                 {:url "/app/adserve" :text "Ad Serving"}
                 {:url "/app/weba" :text "Web Analytics"}
                 {:url "/app/users" :text "Users"}
                 {:url "/app/logout" :text "Logout"}])

(def includes {:jquery (include-js "http://ajax.googleapis.com/ajax/libs/jquery/1.6.1/jquery.min.js")
               ;:default (include-css "/css/default.css")
               ;:reset (include-css "/css/reset.css")
               :bootstrap (include-less "/lib/bootstrap.less")
               :less (include-js "/js/less.js")
               :bootstrap-alerts (include-js "/js/bootstrap-alerts.js")})
               ;:app.js (include-js "/js/app.js")})

;; Helper partials

(defpartial build-head [incls]
            [:head
             [:title "Wareop - Next best analytics and actions"]
             (map #(get includes %) incls)])

(defpartial link-item [{:keys [url cls text]}]
            [:li
             (link-to {:class cls} url text)])

(defpartial empty-row [n]
            (for [i (range n)] [:div.row [:div.span16 "&nbsp;"]]))

;; Layouts

(defpartial main-layout [& content]
            (html5
              (build-head [:bootstrap :less :jquery])
              [:body
               [:div.topbar
                [:div.fill
                 [:div.container
                  [:a.brand {:href "/app"} "wareop"]
                  [:ul.nav
                    (map link-item main-links)]]]]
               content]))

(defpartial app-layout [& content]
              (html5
                (build-head [:bootstrap :less :jquery])
                [:body
                  [:div.topbar
                    [:div.fill
                      [:div.container
                        [:a.brand {:href "/app"} "wareop"]
                        [:ul.nav
                          (map link-item main-links)]]]]
                 [:div.container-fluid
                  [:div.sidebar]
                  [:div.content
                     content]]]))                                                                       

(defpartial home-layout [& content]
              (html5
                (build-head [:bootstrap :less :jquery :bootstrap-alerts])
                [:body
                  [:div.topbar
                    [:div.fill
                      [:div.container
                        [:a.brand {:href "#"} "wareop"]]]]
                        content]))
                                  

