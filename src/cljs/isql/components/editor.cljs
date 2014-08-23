(ns isql.components.editor
  (:require [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]))

(defn editor-view [app owner]
  (reify
    om/IRender
    (render [_]
            (let [editor (. js/ace (edit "editor"))
                  session (. editor (getSession))]
              (. session (setMode "ace/mode/sql"))
              (dom/div nil)))))
