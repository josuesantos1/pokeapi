(ns app.view
  (:require
   [helix.core :refer [defnc]]
   [helix.dom :as d]
   [helix.hooks :as hooks]
   [promesa.core :as p]))

(defnc Pokemon []
  (let [[{:keys [response] :as state} set-state] (hooks/use-state {:response []
                                            :pokemon nil})
        fetch-slug (fn []
                     (p/let [_response (js/fetch (str "/api/pokemon/" (:pokemon state)) (clj->js {:headers {:Content-Type "application/json"}
                                                                                                  :method "GET"}))
                             response (.json _response)
                             data (js->clj response :keywordize-keys true)]
                       (set-state assoc :response data)))]
    (d/div {:class "flex h-full flex-col space-y-4 p-4 bg-gray-100 rounded shadow-lg"}
           (d/div {:class "flex flex-col items-center justify-between p-5 bg-gray-50 rounded-lg shadow-md"}
            (d/div
             (d/input {:placeholder "Buscar pokemon"
                       :class-name "border border-gray-300 rounded px-4 py-2"
                       :on-change #(set-state assoc :pokemon (.. % -target -value))})
             (d/button {:class "px-4 py-2 bg-gray-500 hover:bg-gray-300 text-white hover:text-gray-500 rounded"
                        :onClick #(fetch-slug)} "Buscar"))
            (some->> (:name response) (d/h1 {:class "text-5xl m-5"}))
            (some->> (:weight response) (d/strong "weight: "))
            (d/img {:src (:sprite response)
                    :class "w-1/5 h-auto rounded-lg shadow-lg"}))
           (cond
             (= "Not Found" (:message response))
             (d/div {:class "flex min-h-svh flex-col items-center justify-center space-y-4 p-4 m-3 bg-gray-100 rounded shadow-lg"}
                    (d/h1 {:class "text-5xl"}
                          "Pokemon Not Found"))
             (seq response)
             (d/div
              (d/div {:class "flex flex-col space-y-4 p-4 m-3 bg-gray-100 rounded shadow-lg"}
                     (d/strong {:class "text-lg"} "Abilities")
                     (map
                      (fn [item]
                        (d/div {:key item
                                :class "flex items-center justify-between p-5 bg-white rounded-lg shadow-md hover:bg-gray-50"}
                               (d/span {:class "text-gray-700 font-medium"} item)))
                      (:abilities response)))
              (d/div {:class "flex flex-col space-y-4 p-4 m-3 bg-gray-100 rounded shadow-lg"}
                     (d/strong {:class "text-lg"} "Stats")
                     (map
                      (fn [item]
                        (d/div {:key item
                                :class "flex items-center justify-between p-5 bg-white rounded-lg shadow-md hover:bg-gray-50"}
                               (d/span {:class "text-gray-700 font-medium"} (:name item))
                               (d/span {:class "text-gray-700 font-medium"} (:base-stat item))))
                      (:stats response))))))))
