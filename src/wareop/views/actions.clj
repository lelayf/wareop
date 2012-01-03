(ns wareop.views.actions
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

;(def connection-actions [{:url "/app/datasources/connection/add" :text "Add a connection"}])
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

;; Emailing 

(defpage "/app/actions" {}
      (common/app-layout
          "side story"
          "Rule-based emailing, texting, ad serving, ..."))

