(ns pokeapi.integration
  (:require
   [clj-http.client :as client]
   [clojure.data.json :as json]))

(def ^:private pokeapi-base-url "https://pokeapi.co/api/v2/")

(defn- get-request [url]
  (-> (client/get (str pokeapi-base-url url))
      :body
      json/read-str))

(defn get-pokemon [pokemon]
  (-> (str "pokemon/" pokemon)
      (get-request)))
