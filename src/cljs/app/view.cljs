(ns app.view
  (:require [helix.core :refer [defnc]]
            [helix.dom :as d]
            [promesa.core :as p]
            [helix.hooks :as hooks]))

(defnc ListView []
  (let [[state set-state] (hooks/use-state {:response []
                                            :pokemon nil})
        fetch-slug (fn []
                     (prn state)
                     (p/let [_response (js/fetch (str "/api/pokemon/" (:pokemon state)) (clj->js {:headers {:Content-Type "application/json"}
                                                                                :method "GET"}))
                             response (.json _response)
                             data (js->clj response :keywordize-keys true)]
                       (set-state assoc :response data)))]
    (d/div {:class "flex flex-col space-y-4 p-4 bg-gray-100 rounded shadow-lg"}
           (d/div
            (d/input {:placeholder "Buscar pokemon"
                       :class-name "form-control border border-solid border-gray-600"
                      :on-change #(set-state assoc :pokemon (.. % -target -value))}) 
            (d/button {:onClick #(fetch-slug)} "click"))
             (map-indexed
              (fn [idx item]
                (d/div {:key idx
                        :class "flex items-center justify-between px-3 bg-white rounded-lg shadow-md hover:bg-gray-50"}
                       (d/span {:class "text-gray-700 font-medium"} item)))
              (:abilities (:response state))))))
