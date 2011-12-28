(ns wareop.models.connections
  (:use wareop.settings)
  (:use wareop.models.keys)
  (:use [aleph.redis :only (redis-client)]))


(def r (redis-client {:host "localhost" :password redis-pass}))

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


(defn connection-get [id]
  (let [c (apply hash-map @(r [:hgetall (key-connection id)]))]
    (when (not (empty? connection))
      {:id (c "id")
       :title (c "title")
       :image (c "image")
       :latest (c "latest")
       :release-date (c "release-date")
       :url (c "url")})))

(defn show-set-id! [id new-id]
  @(r [:hset (key-show id) "id" new-id]))

(defn show-set-title! [id new-title]
  @(r [:hset (key-show id) "title" new-title]))

(defn show-set-latest! [id new-latest]
  @(r [:hset (key-show id) "latest" new-latest]))

(defn show-set-release-date! [id new-release-date]
  @(r [:hset (key-show id) "release-date" new-release-date]))

(defn show-set-image! [id new-image]
  @(r [:hset (key-show id) "image" new-image]))

(defn show-set-url! [id new-url]
  @(r [:hset (key-show id) "url" new-url]))


(defn show-get-version [id]
  @(r [:hget "shows:versions" id]))

(defn show-set-version-maybe! [id release-date]
  @(r [:hsetnx "shows:versions" id release-date]))

(defn show-set-version! [id release-date]
  @(r [:hset "shows:versions" id release-date]))


(defn show-get-watchers [show-id]
  @(r [:smembers (key-show-watchers show-id)]))

(defn show-add-to-check! [id]
  @(r [:sadd "shows:to-check" id]))

(defn shows-get-to-check []
  @(r [:smembers "shows:to-check"]))


(defn store-raw-show [show]
  (let [id (show "artistId")]
    (show-set-id! id id)
    (show-set-title! id (show "artistName"))
    (show-set-latest! id (show "collectionName"))
    (show-set-release-date! id (show "releaseDate"))
    (show-set-image! id (show "artworkUrl100"))
    (show-set-url! id (show "artistViewUrl"))))

(defn store-raw-shows [seasons]
  (dorun (map store-raw-show seasons)))
