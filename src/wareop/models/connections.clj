(ns wareop.models.connections
  (:use [wareop.database.conf]
        [wareop.database.redis-datamapper]))

(defn init! [] (println "Initialized connections model"))

; ORACLE
; (def db { :classname    "oracle.jdbc.driver.OracleDriver"
;           :subprotocol  "oracle:thin"
;           :subname      "@10.10.7.1:1521:DW2"
;           :user         "DTM_PROD"
;           :password     "DTM_PROD" })

(def-redis-type connection (string-type :id :name :classname :subprotocol :port :sid :ip :user :pass :db :context) 
                           (list-type :read-by :write-by :del-by)
                           (primary-key :id))

(defn create! [] (println ""))

