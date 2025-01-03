(ns app.core
  (:require
   ["react-dom/client" :as rdom]
   [app.view :as view]
   [helix.core :refer [$ defnc]]
   [helix.dom :as d]))

(defnc App []
  (d/div
   ($ view/Pokemon)))

(defn ^:export init []
  (let [root (rdom/createRoot (js/document.getElementById "app"))]
    (.render root ($ App))))
