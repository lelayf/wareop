(ns wareop.middleware.redis-datamapper
  (:require [redis.core :as redis])
  (:use clojure.contrib.str-utils)
  (:use wareop.middleware.redis-persistence))

(defn primary-key-value [redis-obj]
  (let [pk-keys ((redis-obj :type) :primary-key)
	separator ((redis-obj :type) :key-separator)
	values (map #(redis-obj :get %) pk-keys)]
    (str-join separator values)))

(defn new-redis-object [redis-type]
  (let [state (ref {})]
    (fn thiz [accessor & args]
      (condp = accessor
        :type redis-type
        :set! (let [[k v] args] 
                (redis-type :valid-key? k)
                (dosync
                 (alter state assoc k v))
                v)
        :set-all! (let [[kv-map] args]
                    (doseq [kv kv-map]
                      (let [[k v] kv]
                        (thiz :set! k v))))
        :copy-from-redis-object (let [from (first args)
                                attribs (rest args)]
                            (doseq [attrib attribs]
                              (thiz :set! attrib (from :get attrib))))
        :add! (let [[k v] args
                    add-to-inner-list (fn [current-state ke valu] 
                                        (update-in current-state [ke] conj valu))]
                (dosync
                 (alter state add-to-inner-list k v))
                v)
        :get (let [[k] args]
               (redis-type :valid-key? k)
               (state k))
        :primary-key-value (primary-key-value thiz)
        :save! (persist thiz)
        :get-state @state
        :replace-state (let [[new-state] args] 
                         (dosync
                          (ref-set state new-state)))))))

(defn key-type-for [key-name string-types list-types]
  (if (some #(= % key-name) string-types) 
    :string-type
    (if (some #(= % key-name) list-types)
      :list-type)))

(defn keys-for [keys separator values]
  (let [pk-value (str-join separator values)]
    (map #(str pk-value separator %) keys)))

(defn check-key-validity [key redis-type string-attribs list-attribs]
  (if-not (some #(= % key) string-attribs)
    (if-not (some #(= % key) list-attribs)
      (throw (RuntimeException. (str "Attempt to use unknown key " key " in redis-object of type " (redis-type :name))))))
  true)

(defn new-redis-type [name separator format primary-keys string-attribs list-attribs]
  (fn redis-type [accessor & args]
    (condp = accessor
      :name name
      :format format
      :key-separator separator
      :primary-key primary-keys
      :key-type (let [[k] args]
                  (key-type-for k string-attribs list-attribs))
      :valid-key? (let [[key] args]
                    (check-key-validity key redis-type string-attribs list-attribs))
      :string-keys (let [[values] args] 
                     (keys-for string-attribs separator values))
      :list-keys (let [[values] args]
                   (keys-for list-attribs separator values))
      :new (new-redis-object redis-type)
      :new-with-state (let [[new-state] args
                            nh (new-redis-object redis-type)]
                        (nh :replace-state new-state)
                        nh)
      :find (find-by-primary-key redis-type args)
      :exists? (let [key-value (str-join separator args)
                     key-value (str key-value separator (first primary-keys))]
                 (redis/exists key-value))
      :attrib-exists? (let [attrib-key (first args)
                            pk-value (str-join separator (rest args))]
                        (redis/exists (str pk-value separator attrib-key))))))

(defn specs-for [redis-datatype specs]
  (let [type-spec? #(= redis-datatype (first %))
	extractor (comp next first)]
    (extractor (filter type-spec? specs))))

(defmacro def-redis-type [name & specs]
  (let [string-types (specs-for 'string-type specs)
	list-types (specs-for 'list-type specs)
	pk-keys (specs-for 'primary-key specs)
	format (or (first (specs-for 'format specs)) :clj-str)
	separator (or (first (specs-for 'key-separator specs)) "___")]
    `(def ~name 
	  (new-redis-type '~name ~separator ~format '~pk-keys '~string-types '~list-types))))
