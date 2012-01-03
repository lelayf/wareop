(ns wareop.views.topology.main
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

(def sidebar-locations {:prefix "/app/topology/locations"
                        :links [{:url "jdbc" :text "JDBC"}
                                {:url "files" :text "Files"}
                                {:url "s3" :text "Amazon S3"}
                                {:url "hdfs" :text "Hadoop FS"}]})

(def sidebar-profiling {:prefix "/app/topology/profiling"
                        :links [{:url "stats" :text "Stats"}
                                {:url "alerts" :text "Alerts"}]})

(def bc [{:url "/app" :text "Home"}
         {:url "/app/topology" :text "Topology"}])

;; Partials

(defpartial sidebar []
    [:h5 "Locations"]
      [:ul
        (common/link-items sidebar-locations)]
    [:h5 "Profiling"]
      [:ul
        (common/link-items sidebar-profiling)])

(defpartial content []
              [:div.hero-unit
                [:h1 "Hello world"]
                [:p "Vestibulum id ligula porta felis euismod semper. Integer posuere erat a ante venenatis dapibus posuere velit"]]
              [:div.row
                 [:div.span16 "ETL App Yeah"]])

;; page - CRUD

(defpage "/app/topology" []
    (common/app-layout bc
      (sidebar)
      (content)))


