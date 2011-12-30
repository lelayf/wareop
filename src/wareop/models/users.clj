(ns wareop.models.users
    (:require [redis.core :as redis])
    (:require [noir.util.crypt :as crypt])
    (:use wareop.database.redis-datamapper))

(defn init! [] (println "Initialized user model"))

(def-redis-type user (string-type :email :pass)
                     (list-type :read :write :del)
                     (primary-key :email))


;; Getters

(defn user-get [email] (redis/with-server (def d (user :find :email email))))


;; Mutations and Checkers


;; Operations


