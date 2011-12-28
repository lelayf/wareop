(ns wareop.models.connections
    (:use wareop.settings)
    (:require [noir.util.crypt :as crypt])
    (:use wareop.middleware.redis-datamapper))


(def-redis-type user (string-type :email :pass)
                     (primary-key :email))

(defmacro rds [command-name args]
  `(redis/with-server  {:host "127.0.0.1" :port 6379 :db 0} (redis/~command-name ~args)))


