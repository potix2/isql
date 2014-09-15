(ns isql.core
  (:require
   [goog.events :as events]
   [om.core :as om :include-macros true]
   [om.dom :as dom :include-macros true]
   [isql.appstate :as app]
   [isql.components.editor :as editor]
   [isql.components.mainmenu :as mainmenu]
   [isql.components.table :as isqltable]
   [isql.components.keylistener :as keylistener]
   [isql.components.queryrunner :as queryrunner]
   [cljs.core.async :refer [put! chan <! alts!]]))

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

(om/root
 keylistener/key-listener-component
 app/app-state
 {:target (. js/document (getElementById "keylistener"))
  :shared {:keychan keylistener/key-chan}})

(om/root
 queryrunner/query-runner-component
 app/app-state
 {:target (. js/document (getElementById "queryrunner"))
  :shared {:querychan queryrunner/query-chan}})

(events/listen js/document "keydown" #(put! keylistener/key-chan %))
