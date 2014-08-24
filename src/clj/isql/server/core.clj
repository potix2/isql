(ns isql.server.core
  (:require [compojure.route :as route]
            [compojure.core :refer [GET POST defroutes]]
            [compojure.handler :as handler]
            [ring.util.response :refer [response]]
            [ring.adapter.jetty :as jetty]
            [ring.middleware.json :as middleware]))

(def dummy-result
  {:columns ["col1" "col2" "col3" "col4" "col5" "col6" "col7" "col8" "col9" "col10"]
   :rows [
          [1 2 3 4 5 6 7 8 9 10]
          [1 2 3 4 5 6 7 8 9 10]
          [1 2 3 4 5 6 7 8 9 10]
          [1 2 3 4 5 6 7 8 9 10]
          [1 2 3 4 5 6 7 8 9 10]
          [1 2 3 4 5 6 7 8 9 10]
          [1 2 3 4 5 6 7 8 9 10]
          ]})

(defroutes app-routes
  (POST "/queries" {query :query} (response dummy-result))
  (route/resources "/")
  (route/not-found "Page not found"))

(def app
  (-> (handler/api app-routes)
      (middleware/wrap-json-body)
      (middleware/wrap-json-response)))
