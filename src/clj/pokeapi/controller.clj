(ns pokeapi.controller
  (:require
   [pokeapi.adapter]
   [pokeapi.integration]
   [clojure.string :as cstr]))

(defn get-pokemon
  [{{:keys [pokemon]} :path-params}]
  (try
    {:body (-> pokemon
               (cstr/lower-case)
               (pokeapi.integration/get-pokemon)
               (pokeapi.adapter/data->payload))
     :status 200}
    (catch Exception e
      (let [{:keys [body status]} (ex-data e)]
        {:body   {:message body}
         :status status}))))
