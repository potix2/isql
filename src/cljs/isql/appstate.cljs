(ns isql.appstate)

(def app-state
  (atom
   {:query-result
    {:columns []
     :rows []}

    :main-menu-items
    {:new-query {:text "New Query" :command :new-query}
     :run {:text "Run" :command :run}}
    }))
