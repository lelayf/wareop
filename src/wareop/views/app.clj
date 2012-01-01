(ns wareop.views.app
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

(def connection-actions [{:url "/app/connection/add" :text "Add a connection"}])
(def user-actions [{:url "/app/user/add" :text "Add a user"}])


;; Partials

(defpartial error-text [errors]
              [:div {:class "alert-message error" :style "text-align:left"}
                [:a.close {:href "#"} "x"]
                [:p (string/join "" errors)]])

(defpartial user-fields [{:keys [username] :as usr}]
              (vali/on-error :username error-text)
              [:div.input (text-field {:class "xlarge" :placeholder "Username"} :username username)]
              [:br]
              [:div.input (password-field {:class "xlarge" :placeholder "Password"} :password)])

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
        [:div.row
          [:div.span4]
          [:div.span8 {:style "text-align:right"}
                    (form-to [:post "/"]
                          (user-fields user)
                          [:br]   
                          (submit-button {:class "submit"} "Login"))]
          [:div.span4]]])))

(defpage [:post "/"] {:as user}
      (if (users/login! user)
          (resp/redirect "/app")
          (render "/" user)))

;; Application home - let's use it to display a dashboard

(defpage "/app" []
  (common/main-layout))

;; Connections pages - CRUD

(defpage "/app/connection" {}
    (common/main-layout 
      "Display all connections"))


;; User pages - CRUD

(defpage "/app/users" {}
    (common/main-layout     
        "Display all users"))

