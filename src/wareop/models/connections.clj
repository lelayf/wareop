(ns wareop.models.connections
  (:use wareop.settings)
  (:use wareop.middleware.redis-datamapper))

; ORACLE
; (def db { :classname    "oracle.jdbc.driver.OracleDriver"
;           :subprotocol  "oracle:thin"
;           :subname      "@10.10.7.1:1521:DW2"
;           :user         "DTM_PROD"
;           :password     "DTM_PROD" })

(def-redis-type connection (string-type :id :name :classname :subprotocol :port :sid :ip :user :pass :db :context) 
                           (primary-key :id))


