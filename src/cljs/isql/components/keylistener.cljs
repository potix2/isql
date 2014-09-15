(ns isql.components.keylistener
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require
   [om.core :as om :include-macros true]
   [om.dom :as dom :include-macros true]
   [isql.components.queryrunner :as query]
   [cljs.core.async :refer [put! chan <! alts!]]))

(def ENTER-KEY 13)

(def key-chan (chan))

(defn handle-key-event [app event]
  (let [keyCode (.-keyCode event)
        metaKey (.-metaKey event)
        shiftKey (.-shiftKey event)
        ctrlKey (.-ctrlKey event)
        handler (cond
                 (and (= keyCode ENTER-KEY) shiftKey) (put! query/query-chan :run))
                ]))

(defn key-listener-component [app owner]
  (reify
    om/IWillMount
    (will-mount [_]
                (let [keychan (om/get-shared owner :keychan)]
                  (go
                   (while true
                     (let [[v ch] (alts! [keychan])]
                       (when (= ch keychan)
                         (do (handle-key-event app v))))))))
    om/IRender
    (render [this]
            (dom/div nil ""))))
