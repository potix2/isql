(ns isql.app
  (:require [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]))

(enable-console-print!)

(def app-state
  (atom
   {:query-result
    {:columns ["col1" "col2" "col3" "col4" "col5" "col6" "col7" "col8" "col9" "col10"]
     :rows [
            [1 2 3 4 5 6 7 8 9 10]
            [1 2 3 4 5 6 7 8 9 10]
            [1 2 3 4 5 6 7 8 9 10]
            [1 2 3 4 5 6 7 8 9 10]
            [1 2 3 4 5 6 7 8 9 10]
            [1 2 3 4 5 6 7 8 9 10]
            [1 2 3 4 5 6 7 8 9 10]
            ]}}))

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

(om/root nav-view app-state
         {:target (. js/document (getElementById "navbar"))})

(om/root query-result-view app-state
         {:target (. js/document (getElementById "query-result"))})
