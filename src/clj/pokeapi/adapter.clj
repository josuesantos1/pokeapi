(ns pokeapi.adapter)

(defn- get-ability
  [pokemon]
  (->> (get pokemon "abilities")
       (map #(get-in % ["ability" "name"]))
       sort))

(defn- get-sprites 
  [pokemon]
  (get-in pokemon ["sprites" "front_shiny"]))

(defn- get-stats 
  [pokemon]
  (->>
   (get pokemon "stats")
   (map (fn [stat]
          {:name (get-in stat ["stat" "name"])
           :base-stat (get stat "base_stat")
           :effort (get stat "effort")}))))

(defn data->payload [pokemon]
  {:abilities (get-ability pokemon)
   :sprite (get-sprites pokemon)
   :stats (get-stats pokemon)
   :weight (get pokemon "weight")
   :height (get pokemon "height")
   :name (get pokemon "name")})
