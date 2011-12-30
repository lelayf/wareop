(ns wareop.server
  (:require [noir.server :as server]
            [wareop.models :as models]))

(println "Loading Views")

(server/load-views "src/wareop/views/")

(println "Starting server")

(defn -main [& m]
 "Noir server loop with default Ring session store" 
  (let [mode (keyword (or (first m) :dev))
        port (Integer. (get (System/getenv) "PORT" "8000"))]
    (models/initialize)
    (server/start port {:mode mode
                        :ns 'wareop})))

