(ns app.core
  (:require
   ["react-dom/client" :as rdom]
   [helix.core :refer [$ defnc]]
   [helix.dom :as d]))

(defnc App []
  (d/div
   (d/h1 "PokeApi")))

(defn ^:export init []
  (let [root (rdom/createRoot (js/document.getElementById "app"))]
    (.render root ($ App))))
