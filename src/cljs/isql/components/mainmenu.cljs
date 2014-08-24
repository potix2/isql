(ns isql.components.mainmenu
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]
            [cljs.core.async :refer [put! chan <! alts!]]))

(def sample-result
  (atom
   {:columns ["col1" "col2" "col3" "col4" "col5" "col6" "col7" "col8" "col9" "col10"]
    :rows [
           [1 2 3 4 5 6 7 8 9 10]
           [1 2 3 4 5 6 7 8 9 10]
           [1 2 3 4 5 6 7 8 9 10]
           [1 2 3 4 5 6 7 8 9 10]
           [1 2 3 4 5 6 7 8 9 10]
           [1 2 3 4 5 6 7 8 9 10]
           [1 2 3 4 5 6 7 8 9 10]
           ]}
   ))

(def empty-result
  (atom
   {:columns [] :rows []}))

(defn run [app owner]
  (.log js/console "run")
  (.log js/console (get-in @app [:edit-session :content]))
  (om/update! app [:query-result] @sample-result))

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
