(ns wareop.models.keys)

(defn key-datasource [id]
  (str "datasources:" id))

(defn key-user [email]
  (str "users:" email))

(defn key-connection [id]
  (str "connections:" id)

(defn key-datasource-connections [id]
  (str "datasources:" id ":connections"))

(defn key-connection-datasources [id]
  (str "connections:" id ":datasources"))

