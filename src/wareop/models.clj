(ns wareop.models
    (:require [wareop.models.users :as users]
              [wareop.models.connections :as connections]))

(defn initialize []
  "Placeholder to perform various init things."
  (do    
      (users/init!)            
      (connections/init!)))

