(ns wareop.utils
    (:require [clojure.java.jdbc :as sql]))


(defn sort-maps-by [coll k]
  (sort #(compare (%1 k) (%2 k)) coll))

