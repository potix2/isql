(ns isql.components.table
  (:require [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]))

(defn query-result-view [app owner]
  (reify
    om/IRender
    (render [_]
            (let [result (:query-result app)]
              (dom/div #js {:className "table-responsive"}
                       (dom/table #js {:className "table table-striped"}
                                  (dom/thead nil
                                             (apply dom/tr nil
                                                    (map #(dom/th nil %) (:columns result))))
                                  (apply dom/tbody nil
                                         (map (fn [row]
                                                (apply dom/tr nil
                                                       (map #(dom/td nil %) row))) (:rows result)))))))))
