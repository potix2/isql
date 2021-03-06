(ns isql.server.logger
  (:use compojure.core
        [clojure.string :only [upper-case]])
  (:require [clojure.tools.logging :as log]))

(defn wrap-request-logger [handler]
  (fn [req]
    (let [{remote-addr :remote-addr request-method :request-method uri :uri} req]
      (log/info remote-addr (upper-case (name request-method)) uri)
      (handler req))))

(defn wrap-response-logger [handler]
  (fn [req]
    (let [response (handler req)
          {remote-addr :remote-addr request-method :request-method uri :uri} req
          {status :status} response]
      (log/info remote-addr (upper-case (name request-method)) uri "->" status)
      response)))
