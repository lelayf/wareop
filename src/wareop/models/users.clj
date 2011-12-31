(ns wareop.models.users
    (:require [redis.core :as redis])
    (:require [noir.util.crypt :as crypt]
              [noir.session :as session]
              [noir.validation :as vali]
              [wareop.database.redis-datamapper :as dm]))

(defn init! [] (println "Initialized user model"))

(dm/def-redis-type user (string-type :username :password)
                     (list-type :read :write :del)
                     (primary-key :username))


;; Getters

(defn logged-in? []
    (session/get :logged-in))

(defn user-get [username] (redis/with-server (user :find :username username)))

;; Mutations and Checkers


;; Operations

(defn login! [{:keys [username password] :as user}]
    (let [{stored-pass :password} (user-get username)]
          (if (and stored-pass (crypt/compare password stored-pass))
              (do
                  (session/put! :logged-in true)
                  (session/put! :username username))
              (vali/set-error :username "Invalid username or password"))))

