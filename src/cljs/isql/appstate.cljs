(ns isql.appstate)

(def app-state
  (atom
   {:query-result
    {:columns []
     :rows []}

    :edit-session
    {:content ""
     :theme "chrome"
     ;:theme "solarized_dark"
     :mode "sql"
     :key-bindings "ace"}

    :main-menu-items
    {:new-query {:text "New Query" :command :new-query}
     :run {:text "Run" :command :run}}
    }))
