(ns wareop.views.admin.main
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

(def bc [{:url "/app" :text "Home"}
         {:url "/app/admin" :text "Admin" :cls "active"}])

;; Partials


;; User pages - CRUD

(defpage "/app/admin" {}
    (common/app-layout bc 
      "Admin stuff"  
      "Display all users"))

