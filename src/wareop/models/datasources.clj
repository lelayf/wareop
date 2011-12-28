(ns wareop.models.datasources
  (:use wareop.settings)
  (:use wareop.models.keys)
  (:use wareop.utils)
  (:require [noir.util.crypt :as crypt])
  (:use [aleph.redis :only (redis-client)]))

(def r (redis-client {:host "localhost" :password redis-pass}))

;
; Datasources are stored as Redis hashes
;
; datasource:<id> = {
;     id:
;     name: 
;     connections: #{prod: connection-id, ...}
; }
;
; connection:<id>:datasources = #{datasource-id, ...}
;

(defn datasource-get [id]
  (let [ds (apply hash-map @(r [:hgetall (key-datasource id)]))]
    (when (not (empty? ds))
      (merge {:email (user "email") :pass (user "pass")}
             {:connections (sort-maps-by (map show-get
                                        @(r [:smembers (key-user-shows email)]))
                                   :title)}))))

(defn user-set-email! [email new-email]
  @(r [:hset (key-user email) "email" new-email]))

(defn user-set-pass! [email new-pass]
  @(r [:hset (key-user email) "pass" (crypt/encrypt new-pass)]))

(defn user-add-show! [email show-id]
  @(r [:sadd (key-user-shows email) show-id])
  @(r [:sadd (key-show-watchers show-id) email]))

(defn user-rem-show! [email show-id]
  @(r [:srem (key-user-shows email) show-id])
  @(r [:srem (key-show-watchers show-id) email]))


(defn user-delete! [email]
  @(r [:del (key-user email)])
  @(r [:del (key-user-shows email)])
  (let [shows @(r [:smembers "shows:to-check"])]
    (dorun (map (fn [show-id]
                  @(r [:srem (key-show-watchers show-id) email]))
                shows))))
