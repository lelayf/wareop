(ns wareop.views.topology.locations.main
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
            [wareop.views.common :as common]
            [wareop.views.topology.main :as topo-main]))

;; Links
(def bc [{:url "/app" :text "Home"}
         {:url "/app/topology" :text "Topology"}
         {:url "/app/topology/locations" :text "Locations" :cls "active"}])

;; Partials


;; Locations - CRUD

(defpage "/app/topology/locations" []
  (common/app-layout bc
    (topo-main/sidebar)
    (topo-main/content)))

