(ns isql.components.queryrunner
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require
   [om.core :as om :include-macros true]
   [om.dom :as dom :include-macros true]
   [ajax.core :as http]
   [cljs.core.async :refer [put! chan <! alts!]]))

(def query-chan (chan))

(def empty-result
  (atom
   {:columns [] :rows []}))

(defn new-query [app]
  (.log js/console "new-query")
  (om/update! app [:query-result] @empty-result))

(defn exec-query-handler [app result]
  (let [cols (get result :columns)
        rows (get result :rows)]
    (om/update! app [:query-result] (om/value {:columns cols :rows rows}))))

(defn run [app]
  (.log js/console "run")
  (let [content (get-in @app [:edit-session :content])]
    (.log js/console content)
    (http/POST "http://localhost:3000/queries" {:params {:query content}
                                                :handler
                                                (fn [result]
                                                  (exec-query-handler app result))})))

(defn handle-query-event [app command]
  (cond
    (= command :run) (run app)
    (= command :new-query) (new-query app)))

(defn query-runner-component [app owner]
  (reify
    om/IWillMount
    (will-mount [_]
                (let [querychan (om/get-shared owner :querychan)]
                  (go
                   (while true
                     (let [[v ch] (alts! [querychan])]
                       (when (= ch querychan)
                         (do (handle-query-event app v))))))))
    om/IRender
    (render [this]
            (dom/div nil ""))))
