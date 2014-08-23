(ns isql.core
  (:require [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]
            [isql.appstate :as app]
            [isql.components.editor :as editor]
            [isql.components.mainmenu :as mainmenu]
            [isql.components.table :as isqltable]))

(enable-console-print!)

(om/root
 mainmenu/nav-view
 app/app-state
 {:target (. js/document (getElementById "navbar"))})

(om/root
 editor/editor-view
 app/app-state
 {:target (. js/document (getElementById "ace-editor"))})

(om/root
 isqltable/query-result-view
 app/app-state
 {:target (. js/document (getElementById "query-result"))})
