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


;; Force you to be logged in to use app/*

(pre-route "/app*" {}
      (resp/redirect "/"))

(defpage "/" []
  (common/main-layout))

;; Connections pages - CRUD

(defpage "/app/connection" {}
         "Display all connections")


;; User pages - CRUD

(defpage "/app/users" {}
         "Display all users")

