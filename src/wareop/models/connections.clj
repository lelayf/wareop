(ns wareop.models.connections
  (:use wareop.settings)
  (:use wareop.middleware.redis-datamapper))

; connections:<Connection ID> = {
;     id: connection id
;     name: custom connection name
;     classname: driver class
;     subprotocol: jdbc protocol ("oracle:thin" for instance)
;     port:
;     sid:
;     ip:
;     user:
;     password:
;     db:
;     context: production|development 
; }
;
; subprotocol:<subprotocol value>:connections = (<Connection ID>, ...) (Set)
; context:<context value>:connections = (<Connection ID>, ...) (Set)
;

(def-redis-type connection (string-type :id :name :classname :subprotocol :port :sid :ip :user :pass :db :context) 
                           (primary-key :id))


