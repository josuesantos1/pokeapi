(ns app.view
  (:require [helix.core :refer [defnc]]
            [helix.dom :as d]))

(def items ["Item 1" "Item 2" "Item 3" "Item 4"])

(defnc ListView []
  (d/div {:class "flex flex-col space-y-4 p-4 bg-gray-100 rounded shadow-lg"}
         (map-indexed
          (fn [idx item]
            (d/div {:key idx
                    :class "flex items-center justify-between px-3 bg-white rounded-lg shadow-md hover:bg-gray-50"}
                   (d/span {:class "text-gray-700 font-medium"} item)))
          items)))
