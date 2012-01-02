(ns wareop.init
  (:use [wareop.models.users]
        [wareop.database.conf])
  (:require [noir.util.crypt :as crypt]
            [redis.core :as redis]))

(def internal (user :new))
(internal :set! :username "internal")
(internal :set! :password (crypt/encrypt "wareop")) ; $2a$10$qSwX8362kFY1ifQCv0yGDOlyho9JKHycoD95ByesuMQ8vRjWGg3Ym
(internal :set! :read "all") 
(internal :set! :write "all")
(internal :set! :del "all") 
(redis/with-server redis-conf (internal :save!))

