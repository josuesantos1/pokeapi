(ns pokeapi.core
  (:gen-class)
  (:require
   [muuntaja.core :as m]
   [reitit.coercion.malli]
   [reitit.ring :as ring]
   [pokeapi.controller]
   [reitit.ring.middleware.muuntaja :as muuntaja]
   [ring.adapter.jetty :as ring.jetty]))

(defonce server (atom nil))

(def app
  (ring/ring-handler
   (ring/router
    ["/api" 
     ["/ping" {:handler (fn [_] {:body "pong" :status 200})}]
     ["/pokemon/:pokemon" {:get {:handler #(pokeapi.controller/get-pokemon %)}}]]
    {:data {:muuntaja m/instance
            :middleware [muuntaja/format-middleware]}})))

(defn start []
  (ring.jetty/run-jetty #'app {:port  3000
                               :join? false}))

(defn -main
  [& _]
  (prn "[+] starting server...")
  (reset! server (start)))

(comment
  (def server (start))

  (.stop server)
  )