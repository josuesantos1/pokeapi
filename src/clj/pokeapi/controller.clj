(ns pokeapi.controller
  (:require
   [pokeapi.adapter]
   [pokeapi.integration]))

(defn get-pokemon
  [{{:keys [pokemon]} :path-params}]
  (try
    {:body (-> (pokeapi.integration/get-pokemon pokemon)
               (pokeapi.adapter/data->payload))
     :status 200}
    (catch Exception e
      (let [{:keys [body status]} (ex-data e)]
        {:body   {:message body}
         :status status}))))
