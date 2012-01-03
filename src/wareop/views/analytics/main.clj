(ns wareop.views.analytics.main
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
         {:url "/app/analytics" :text "Analytics" :cls "active"}])

;; Partials


;; Reports

(defpage "/app/analytics" {}
    (common/app-layout bc
      "hohoho"  
      "Reporting app based on D3 (and Pinot?)"))


