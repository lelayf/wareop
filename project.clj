(defproject wareop "1.0.0-SNAPSHOT"
  :description "Next best action, next best analytics"
  :dependencies [[org.clojure/clojure "1.3.0"]
		 [org.clojure/clojure-contrib "1.2.0"]
                 [org.clojure/java.jdbc "0.1.1"]
                 [noir "1.2.2"]
                 [org.clojars.tavisrudd/redis-clojure "1.3.1-SNAPSHOT"] 
                 [cheshire "2.0.2"]]
  :dev-dependencies [[lein-marginalia "0.6.0"]]
  :main wareop.server)

