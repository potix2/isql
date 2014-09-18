(ns isql.components.editor
  (:require [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]))

(defn editor-view [app owner]
  (reify
    om/IRender
    (render [_]
            (let [editor (. js/ace (edit "editor"))
                  session (. editor (getSession))
                  mode (get-in app [:edit-session :mode])
                  theme (get-in app [:edit-session :theme])]
              (.setTheme editor (str "ace/theme/" theme))
              (.setShowGutter (.-renderer editor) false)
              (.addEventListener editor "change" (fn [e target] (om/update! app [:edit-session :content] (.getValue target))))
              (.setMode session (str "ace/mode/" mode))
              (dom/div nil)))))
