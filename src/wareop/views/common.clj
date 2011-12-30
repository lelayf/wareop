(ns wareop.views.common
  (use noir.core
       hiccup.core
       hiccup.page-helpers))

;; Links and includes
(def main-links [{:url "/app/connection" :text "JDBC Connections"}
                 {:url "/app/" :text "Application Home"}
                 {:url "/app/users" :text "Users"}
                 {:url "/app/logout" :text "Logout"}])

(def includes {:jquery (include-js "http://ajax.googleapis.com/ajax/libs/jquery/1.6.1/jquery.min.js")
               :default (include-css "/css/default.css")
               :reset (include-css "/css/reset.css")
               :app.js (include-js "/js/app.js")})

;; Helper partials

(defpartial build-head [incls]
            [:head
             [:title "Wareop - Next best analytics and actions"]
             (map #(get includes %) incls)])

(defpartial link-item [{:keys [url cls text]}]
            [:li
             (link-to {:class cls} url text)])

;; Layouts

(defpartial main-layout [& content]
            (html5
              (build-head [:reset :default :jquery :app.js])
              [:body
               [:div#wrapper
                [:div.content
                 [:div#header
                  [:h1 (link-to "/app/" "Wareop")]
                  [:ul.nav
                   (map link-item main-links)]]
                 content]]]))

