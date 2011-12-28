(ns wareop.server
  (:use wareop.settings)
  (:use [wareop.middleware.session.redis :only (redis-store)])
  (:require [noir.server :as server]))

(server/load-views "src/wareop/views/")

(defn -main [& m]
 "Noir server loop with plugged-in redis session store" 
  (let [mode (keyword (or (first m) :dev))
        port (Integer. (get (System/getenv) "PORT" "8000"))]
    (server/start port {:mode mode
                        :ns 'wareop
                        :session-store (redis-store "localhost" redis-pass)})))

