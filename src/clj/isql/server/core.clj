(ns isql.server.core
  (:require [compojure.route :as route]
            [compojure.core :refer [GET POST defroutes]]
            [compojure.handler :as handler]
            [ring.util.response :refer [response]]
            [ring.adapter.jetty :as jetty]
            [ring.middleware.edn :as middleware]
            [ring.middleware.resource :as resource]
            [clojure.java.jdbc :as jdbc]
            [isql.server.logger :refer [wrap-request-logger wrap-response-logger]]
            [clojure.tools.logging :as log]))

(def sqlite-db {:subprotocol "sqlite"
                 :subname "resources/data/Chinook_Sqlite.sqlite"})

(defn fetch-result [result-set]
  (let [cols (keys (first result-set))
        rows (map (fn [row] (vals row)) result-set)
        numrows (count result-set)] ; FIXME
    {:columns cols :rows rows :numrows numrows}))

(defn exec-query [db sql]
  (jdbc/query db [sql] :result-set-fn fetch-result))

(defn generate-response [data & [status]]
  {:status (or status 2000)
   :headers {"Content-Type" "application/edn"}
   :body (pr-str data)})

(defroutes app-routes
  (POST "/queries" request
        (let [query (get-in request [:edn-params :query])]
          (log/info (str query))
          (generate-response
           (exec-query sqlite-db query))))
  (route/resources "/")
  (route/not-found "Page not found"))

(def app
  (-> (handler/api app-routes)
      (resource/wrap-resource "public")
      (wrap-request-logger)
      (wrap-response-logger)
      (middleware/wrap-edn-params)))

