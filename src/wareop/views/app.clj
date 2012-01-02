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


;; Partials


;; Application home - let's use it to display a dashboard

(defpage "/app" []
  (common/main-layout
    [:iframe {:style "width:100%;height:800px" :src "http://www.forbes.com/sites/afontevecchia/2011/12/30/facebook-vs-google-the-battle-for-internet-dominance/"}]))

