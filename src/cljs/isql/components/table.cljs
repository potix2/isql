(ns isql.components.table
  (:require [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]))

(defn table-header [columns owner]
  (reify
    om/IRender
    (render [_]
            (dom/thead nil
                       (apply dom/tr nil
                              (map #(dom/th nil (subs (str %) 1)) columns))))))

(defn table-body [rows owner]
  (reify
    om/IRender
    (render [_]
            (apply dom/tbody nil
                   (map (fn [row]
                          (apply dom/tr nil
                                 (map #(dom/td nil %) row))) rows)))))

(defn query-result-view [app owner]
  (reify
    om/IRender
    (render [_]
            (dom/div #js {:className "table-responsive"}
                     (dom/table #js {:className "table table-striped"}
                                (om/build table-header
                                          (get-in app [:query-result :columns]))
                                (om/build table-body
                                          (get-in app [:query-result :rows])))))))
