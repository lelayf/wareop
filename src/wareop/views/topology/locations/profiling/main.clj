 
(comment

  http://stackoverflow.com/questions/8140867/clojure-hiccup-form-handler

(defn get-cols-nms [table] 
    (do (db/cols-list table)))

(defpartial form-dataset [cols-list]
    (text-field "dataset_nm" "Input here dataset name")[:br]
    (assoc-in (drop-down "table" tables-n) [1 :onclick] "this.form.submit()")[:br]
    [:input {:type "submit" :value "Submit" :name "name"}][:br]
    (mapcat #(vector (check-box %) % [:br]) cols-list) 
    )

(defpage "/dataset/create" []
    (common/layout
          (form-to [:post "/dataset/create"]
                         (form-dataset(get-cols-nms (first tables-n))))))

(defpage [:post "/dataset/create"] {:as ks}
    (common/layout
          (prn ks)
          (let [table (ks :table)]
                  (form-to [:post "/dataset/create"] 
                               (if (= (:name ks) nil)
                                     (form-dataset (get-col-nms table))
                                     [:p "It works!"])))))
)

