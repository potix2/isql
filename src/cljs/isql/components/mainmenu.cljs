(ns isql.components.mainmenu
  (:require [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]))

(defn active-item [active]
  (if active
    "active"
    ""))

(defn nav-items-view [item owner]
  (reify
    om/IRender
    (render [_]
            (dom/li nil
                    (dom/a #js {:href "#"} (:name item))))))

(defn nav-view [app owner]
  (reify
    om/IRender
    (render [_]
            (dom/div #js {:className "navbar navbar-inverse navbar-fixed-top"}
                     (dom/div #js {:className "navbar-inner"}
                              (dom/div #js {:className "container"}
                                       (dom/a #js {:className "brand" :href "#"} "iSQL")
                                       (apply dom/ul #js {:className "nav"}
                                              (om/build-all nav-items-view [{:name "NewQuery"}
                                                                            {:name "Run"}]))))))))