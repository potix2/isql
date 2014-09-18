(ns isql.appstate)

(def app-state
  (atom
   {:query-result
    {:columns [:col1 :col2 :col3]
     :rows [
            ["a" "b" "c"]
            ["a" "b" "c"]
            ["a" "b" "c"]
            ["a" "b" "c"]
            ["a" "b" "c"]
            ]}

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
