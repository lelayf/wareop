(ns wareop.database.conf)

(def redis-conf {:host "127.0.0.1" :port 6379 :db 0})

(def jdbc-templates {
                     :oraclethin {:classname "oracle.jdbc.driver.OracleDriver"
                                  :url "jdbc:oracle:thin:@<host>:<port>:<sid>" 
                                  :desc "Thin driver connection. SQL Net is not required."
                                  :form { :host "Server network name or ip address"
                                          :port "Listener port number (usually 1521)"
                                          :sid  "Instance service name (Oracle SID)"}}
                     :mysql      {}})

