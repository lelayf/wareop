(ns wareop.utils
    (:require [clojure.java.jdbc :as sql]))

(def db { :classname    "oracle.jdbc.driver.OracleDriver"
          :subprotocol  "oracle:thin"
          :subname      "@10.10.7.1:1521:DW2"
          :user         "DTM_PROD"
          :password     "DTM_PROD" })

(defn fetch-data [s]
    (sql/with-connection db 
           (sql/with-query-results rs s
                   (into [] rs)))) 

(def s0 ["select * from tf_order_items where date_key > 20111206 and rownum<=10000 order by order_id asc"])

(def orderitems (vec (fetch-data s0)))

(defn sort-maps-by [coll k]
  (sort #(compare (%1 k) (%2 k)) coll))

