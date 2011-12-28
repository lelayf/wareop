(ns wareop.views.main
  (:use noir.core)
  (:use wareop.utils)
  (:require [noir.response :as resp])
  (:require [noir.session :as sess])
  (:require [noir.util.crypt :as crypt])
  (:require [wareop.templates.main :as t])
  (:require [wareop.models.datasources :as ds])i
  (:require [wareop.models.users :as users])i)


; Utils -----------------------------------------------------------------------
(def email-regex #"[a-zA-Z0-9._+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}")
(defn all-are [pred coll]
  (= (count coll)
    (count (filter pred coll))))

(defn flash! [message]
  (sess/flash-put! message)
  nil)


; Utils -----------------------------------------------------------------------
(defn unique-by
  "Turn a sequence of maps into a new sequence with duplicated removed, with
  uniqueness determined by the given keys.

  Ex:

  (def a {:foo 1 :bar 1 :baz 1})
  (def b {:foo 1 :bar 1 :baz 2})
  (def c {:foo 1 :bar 2 :baz 3})

  (unique-by [a b c] :foo)
  ;=> [c]
  (unique-by [a b c] :foo :bar)
  ;=> [b c]
  (unique-by [a b c] :baz)
  ;=> [a b c]
  "
  [coll & ks]
  (vals (reduce merge {} (map #(vector (select-keys % ks) %) coll))))

(defn unique-shows [seasons]
  (unique-by (sort-maps-by seasons "releaseDate") "artistId"))


; Authentication --------------------------------------------------------------
(defn force-login []
  (flash! "Please log in to view that page!")
  (resp/redirect "/"))

(defmacro login-required [& body]
  `(if-not (sess/get :email)
     (force-login)
     (do ~@body)))


(defn create-user [email password]
  (users/user-set-email! email email)
  (users/user-set-pass! email password)
  (sess/put! :email email)
  (users/user-get email))

(defn check-login [{:keys [email password]}]
  (if (some empty? [email password])
    (flash! "Both fields are required.  This really shouldn't be difficult.")
    (if-not (re-find email-regex email)
      (flash! "That's not an email address!")
      (if-let [user (users/user-get email)]
        (if (crypt/compare password (:pass user))
          (do
            (sess/put! :email email)
            user)
          (flash! "Invalid login!"))
        (create-user email password)))))


; Home ------------------------------------------------------------------------
(defpage "/" []
  (if-let [email (sess/get :email)]
    (resp/redirect (str "/" email))
    (t/home)))

(defpage [:post "/"] {:as login}
  (if (check-login login)
    (resp/redirect (str "/" (:email login)))
    (render "/" login)))


; User ------------------------------------------------------------------------
(defpage [:get ["/:email" :email email-regex]] {:keys [email]}
  (login-required
    (if (not= email (sess/get :email))
      (force-login)
      (let [user (users/user-get email)]
        (t/user user)))))


; Search ----------------------------------------------------------------------
(defpage "/search" {:keys [query]}
  (login-required
    (if (empty? query)
      (do (flash! "Please enter something to search for.")
        (resp/redirect "/")) 
      (let [results (unique-shows (itunes-search-show query))]
        (shows/store-raw-shows results)
        (t/search query results)))))


; Add -------------------------------------------------------------------------
(defpage [:post "/add"] {:keys [artist-id]}
  (login-required
    (users/user-add-show! (sess/get :email) artist-id)
    (shows/show-add-to-check! artist-id) 
    (let [show (shows/show-get artist-id)]
    (shows/show-set-version-maybe! artist-id
      (:release-date show)) 
    (flash! (str "Added &ldquo;" (:title show) "&rdquo; to your list.  "
                 "We'll email you if a new season hits iTunes."))) 
    (resp/redirect "/")))


; Remove ----------------------------------------------------------------------
(defpage [:post "/rem"] {:keys [artist-id]}
  (login-required
    (users/user-rem-show! (sess/get :email) artist-id)
    (flash! "Removed a show from your list.")
    (resp/redirect "/")))


; Change Password -------------------------------------------------------------
(defpage "/password" []
  (login-required
    (t/password)))


(defn- pass-valid? [password]
  (not (empty? password)))

(defpage [:post "/password"] {:keys [password]}
  (login-required
    (if-not (pass-valid? password)
      (render "/password" password)
      (do
        (users/user-set-pass! (sess/get :email) password)
        (resp/redirect "/")))))


; Log Out ---------------------------------------------------------------------
(defpage [:post "/logout"] []
  (sess/remove! :email)
  (resp/redirect "/"))


; Delete Account --------------------------------------------------------------
(defpage "/delete-account" []
  (login-required
    (t/delete-account)))

(defpage [:post "/delete-account"] []
  (login-required
    (users/user-delete! (sess/get :email))
    (sess/remove! :email)
    (flash! "Your account has been deleted.  You can sign up again later if you want, though!")
    (resp/redirect "/")))

