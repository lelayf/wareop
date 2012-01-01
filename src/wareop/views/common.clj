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
(def main-links [{:url "/app/connection" :text "Data Sources"}
                 {:url "/app" :text "Application Home"}
                 {:url "/app/users" :text "Users"}
                 {:url "/app/logout" :text "Logout"}])

(def includes {:jquery (include-js "http://ajax.googleapis.com/ajax/libs/jquery/1.6.1/jquery.min.js")
               ;:default (include-css "/css/default.css")
               ;:reset (include-css "/css/reset.css")
               :bootstrap (include-less "/lib/bootstrap.less")
               :less (include-js "/js/less.js")
               :app.js (include-js "/js/app.js")})

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
              (build-head [:bootstrap :less :jquery :app.js])
              [:body
               [:div.topbar
                [:div.fill
                 [:div.container
                  [:a.brand {:href "#"} "wareop"]
                  [:ul.nav
                    (map link-item main-links)]]]]
               content]))

(defpartial home-layout [& content]
              (html5
                (build-head [:bootstrap :less :jquery :app.js])
                [:body
                  [:div.topbar
                    [:div.fill
                      [:div.container
                        [:a.brand {:href "#"} "wareop"]]]]
                        content]))
                                  

