(defproject wareop "1.0.0-SNAPSHOT"
  :description "Plug and play operational datawarehouse"
  :dependencies [[org.clojure/clojure "1.3.0"]
		 [org.clojure/clojure-contrib "1.2.0"]
                 [org.clojure/java.jdbc "0.1.1"]
                 [noir "1.2.0"]
                 [org.clojars.tavisrudd/redis-clojure "1.3.1-SNAPSHOT"] 
                 [postmark "1.0.0"]
                 [cheshire "2.0.2"]
                 [clj-http "0.2.1"]
                 [aleph "0.2.0-beta2"]]
  :dev-dependencies [[lein-marginalia "0.6.0"]]
  :main wareop.server)
