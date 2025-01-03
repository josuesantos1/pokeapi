(ns pokeapi.core
  (:gen-class)
  (:require
   [clojure.java.io :as io]
   [muuntaja.core :as m]
   [pokeapi.controller]
   [reitit.coercion.malli]
   [reitit.ring :as ring]
   [reitit.ring.middleware.muuntaja :as muuntaja]
   [ring.adapter.jetty :as ring.jetty]))

(defonce server (atom nil))

(defn index []
  (slurp (io/resource "public/index.html")))


(def app
  (ring/ring-handler
   (ring/router
    ["/"
     ["/api"
      ["/ping" {:handler (fn [_] {:body "pong" :status 200})}]
      ["/pokemon/:pokemon" {:get {:handler #(pokeapi.controller/get-pokemon %)}}]]
     ["assets/*" (ring/create-resource-handler {:root "public/assets"})]
     ["" {:handler (fn [_] {:body (index) :status 200})}]]
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

  (.stop server))