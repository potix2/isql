(ns isql.appstate)

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
            ]}
    :main-menu-items
    {:new-query {:text "New Query" :command :new-query}
     :run {:text "Run" :command :run}}
    }))
