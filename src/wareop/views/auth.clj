(ns wareop.views.auth
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

;; Force you to be logged in to use app/*

(pre-route "/app*" {}
  (when-not (users/logged-in?)
           (resp/redirect "/")))

(defpage "/" {:as user}
    (if (users/logged-in?)
      (resp/redirect "/app")
      (common/home-layout
         (common/empty-row 10)
         [:div.container 
              (form-to [:post "/"]
              (user-fields user) 
              [:br]         
              [:div.row [:div {:class "span1 offset9" :style "text-align:left"} (submit-button {:class "btn primary submit"} "Login")]])])))

(defpage [:post "/"] {:as user}
      (if (users/login! user)
          (resp/redirect "/app")
          (render "/" user)))

(defpage "/app/logout" {}
           (session/clear!)
           (resp/redirect "/"))

