(ns app.core
  (:require
   ["react-dom/client" :as rdom]
   [helix.core :refer [$ defnc]]
   [helix.dom :as d]
   [app.view :as view]))

(defnc App []
  (d/div
   ($ view/ListView)))

(defn ^:export init []
  (let [root (rdom/createRoot (js/document.getElementById "app"))]
    (.render root ($ App))))
