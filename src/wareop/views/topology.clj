(ns wareop.views.topology
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
            [wareop.views.common :as common]))

;; Links

(def sidebar-locations {:prefix "/app/topology/locations"
                        :links [{:url "jdbc" :text "JDBC"}
                                {:url "files" :text "Files"}
                                {:url "s3" :text "Amazon S3"}
                                {:url "hdfs" :text "Hadoop FS"}]})

;(def user-actions [{:url "/app/users/add" :text "Add a user"}])


;; Partials

(defpartial error-text [errors]
              [:div.row [:div {:class "span8 offset4"} [:div {:class "alert-message error in fade" :data-alert "alert"}; :style "text-align:left"}
                [:a.close {:href "#"} "x"]
                [:p (string/join "" errors)]]]])

(defpartial user-fields [{:keys [username] :as usr}]
              (vali/on-error :username error-text)
              [:div.row [:div {:class "span6 offset3"} [:div.input (text-field {:class "xlarge" :placeholder "Username"} :username username)]]]
              [:br]
              [:div.row [:div {:class "span6 offset3"} [:div.input (password-field {:class "xlarge" :placeholder "Password"} :password)]]])


;; page - CRUD

;(defpartial sidebar  

; TODO : use map to manage this
(defpartial sidebar []
    [:h5 "Locations"]
      [:ul
        (common/link-items sidebar-locations)]
    [:h5 "Profiling"]
      [:ul
       [:li "Jobs"]
       [:li "Stats"]
       [:li "Alerts"]
       [:li "Lineage"]])


(defpartial content []
              [:div.hero-unit
                [:h1 "Hello world"]
                [:p "Vestibulum id ligula porta felis euismod semper. Integer posuere erat a ante venenatis dapibus posuere velit"]]
              [:div.row
                 [:div.span16 "ETL App Yeah"]])


(defpage "/app/topology" []
    (common/app-layout 
      (sidebar)
      (content)))


