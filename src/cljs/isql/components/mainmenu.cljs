(ns isql.components.mainmenu
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]
            [cljs.core.async :refer [put! chan <! alts!]]
            [ajax.core :as http]))

(def empty-result
  (atom
   {:columns [] :rows []}))

(defn exec-query-handler [app result]
  (let [cols (get result "columns")
        rows (get result "rows")]
    (om/update! app [:query-result] (om/value {:columns cols :rows rows}))))

(defn run [app owner]
  (.log js/console "run")
  (let [content (get-in @app [:edit-session :content])]
    (.log js/console content)
    (http/POST "http://localhost:3000/queries" {:params {:query content}
                                                :handler
                                                (fn [result]
                                                  (exec-query-handler app result))})))

(defn new-query [app owner]
  (.log js/console "new-query")
  (om/update! app [:query-result] @empty-result))

(defn menu-item-component [item owner]
  (reify
    om/IInitState
    (init-state [_] {})

    om/IRenderState
    (render-state [this {:keys [clickchan]}]
                  (dom/li nil
                          (dom/a
                           #js {:href "#"
                                :onClick (fn [e] (put! clickchan {:command (:command @item)
                                                                  :owner owner}))}
                           (:text item))))))

(defn nav-view [app owner]
  (reify
    om/IInitState
    (init-state [_]
                {:clickchan (chan)})

    om/IWillMount
    (will-mount [_]
                (let [clickchan (om/get-state owner :clickchan)]
                  (go (loop []
                        (let [e (<! clickchan)
                              command (:command e)
                              origin-owner (:owner e)]
                          (when (= command :new-query) (new-query app owner))
                          (when (= command :run) (run app owner))
                          (recur))))))

    om/IRenderState
    (render-state [this {:keys [clickchan]}]
                  (dom/div #js {:className "navbar navbar-inverse navbar-fixed-top"}
                           (dom/div #js {:className "navbar-inner"}
                                    (dom/div #js {:className "container"}
                                             (dom/a #js {:className "brand" :href "#"} "iSQL")
                                             (dom/ul #js {:className "nav"}
                                                     (om/build menu-item-component
                                                               (get-in app [:main-menu-items :new-query])
                                                               {:init-state {:clickchan clickchan}})
                                                     (om/build menu-item-component
                                                               (get-in app [:main-menu-items :run])
                                                               {:init-state {:clickchan clickchan}})
                                                     )))))))
