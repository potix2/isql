(defproject isql "0.1.0-SNAPSHOT"
  :description "FIXME: write this!"
  :url "http://example.com/FIXME"

  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/tools.reader "0.8.3"]

                 ;; CLJ
                 [ring/ring-core "1.2.0"]
                 [compojure "1.1.6"]
                 [cheshire "5.3.1"]

                 ;; CLJS
                 [org.clojure/clojurescript "0.0-2268"]
                 [org.clojure/core.async "0.1.319.0-6b1aca-alpha"]
                 [cljs-http "0.1.2"]
                 [om "0.1.7"]]

  :plugins [[lein-cljsbuild "1.0.4-SNAPSHOT"]
            [lein-ring "0.8.7"]]

  :ring {:handler isql.core/app
         :init    isql.core/init}

  :source-paths ["src/clj"]

  :cljsbuild {
    :builds [{:id "dev"
              :source-paths ["src/cljs"]
              :compiler {
                :output-to "resources/public/js/app.js"
                :output-dir "resources/public/js/out"
                :optimizations :none
                :source-map true
                :externs ["om/externs/react.js"]
                                        }}]})
