(ns wareop.views.topology.locations.jdbc.main
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
         {:url "/app/topology/locations" :text "Locations"}
         {:url "/app/topology/locations/jdbc" :text "JDBC" :cls "active"}])

(def sidebar-jdbc {:prefix "/app/topology/locations/jdbc"
                   :links [{:url "list" :text "List Connections"}
                           {:url "create" :text "Create Connection"}]})

;; Partials

(defpartial sidebar []
            [:h5 "JDBC"]
              [:ul
               (common/link-items sidebar-jdbc)])

;; Locations - CRUD

(defpage "/app/topology/locations/jdbc" []
    (common/app-layout bc 
      (sidebar)
      (topo-main/content)))


