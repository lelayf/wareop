(ns wareop.views.actions.main
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
         {:url "/app/actions" :text "Actions" :cls "active"}])

;; Partials

;; Emailing 

(defpage "/app/actions" {}
      (common/app-layout bc
          "side story"
          "Rule-based emailing, texting, ad serving, ..."))

