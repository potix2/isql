(ns isql.components.mainmenu
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]
            [cljs.core.async :refer [put! chan <! alts!]]))

(defn run []
  (.log js/console "run"))

(defn new-query []
  (.log js/console "new-query"))

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
                          (when (= command :new-query) (new-query))
                          (when (= command :run) (run))
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