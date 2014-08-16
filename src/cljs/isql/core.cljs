(ns isql.app
  (:require-macros [cljs.core.async.macros :refer [go alt!]])
  (:require [goog.events :as events]
            [cljs.core.async :refer [put! <! >! chan timeout]]
            [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]
            [cljs-http.client :as http])
  (:import [goog History]
           [goog.history EventType]))

(enable-console-print!)

(def INITIAL[
             [12345678, "Lorem Ipsum", 23456789, "Lorem Ipsum", 0.123456, 1.234567, 2.345678, 1234567890],
             [12345678, "Lorem Ipsum", 23456789, "Lorem Ipsum", 0.123456, 1.234567, 2.345678, 1234567890],
             [12345678, "Lorem Ipsum", 23456789, "Lorem Ipsum", 0.123456, 1.234567, 2.345678, 1234567890]
             ])
(def app-state
  (atom {:rows INITIAL}))


(defn row-view
  [row owner]
  (reify
    om/IRender
    (render [this]
            (dom/tr nil
                    (apply dom/td nil row)))))

(defn result-table
  [rows owner]
  (reify
    om/IRender
    (render [this]
            (dom/div nil
                     (dom/table nil
                                (dom/tbody nil
                                           (om/build-all row-view rows)))))))

(defn editor-box
  [cursor owner]
  (reify
    om/IRender
    (render [this]
            (dom/div nil
                     (dom/textarea #js {:width "100%" :height "400px" :placeholder "Edit Query" :ref "query"})
                     ))))

(defn tutorial-app [app owner]
  (reify
    om/IRender
    (render [this]
            (dom/div nil
                     (dom/h2 nil "Query Editor")
                     (om/build editor-box app)
                     (om/build result-table (:rows app))
                     ))))

(om/root app-state tutorial-app (.getElementById js/document "content"))
