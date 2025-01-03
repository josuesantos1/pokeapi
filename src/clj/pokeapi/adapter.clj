(ns pokeapi.adapter)

(defn- get-ability
  [pokemon]
  (->> (get pokemon "abilities")
       (map #(get-in % ["ability" "name"]))
       sort))

(defn data->payload [pokemon]
  (let [abilities (get-ability pokemon)]
    {:abilities abilities}))
