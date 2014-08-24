(defproject isql "0.1.0-SNAPSHOT"
  :description "online sql editor"
  :url "http://potix2.github.io/isql"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/clojurescript "0.0-2311"]
                 [org.clojure/core.async "0.1.267.0-0d7780-alpha"]
                 [om "0.7.1"]
                 [cljs-ajax "0.2.6"]

                 [ring/ring-jetty-adapter "1.2.1"]
                 [fogus/ring-edn "0.2.0"]
                 [compojure "1.1.6"]
                 [org.xerial/sqlite-jdbc "3.7.2"]
                 [mysql/mysql-connector-java "5.1.25"]
                 [org.clojure/java.jdbc "0.3.5"]
                 [org.clojure/tools.logging "0.3.0"]]

  :plugins [[lein-cljsbuild "1.0.4-SNAPSHOT"]
            [lein-ring "0.8.11"]]

  :ring {:handler isql.server.core/app}

  :source-paths ["src/clj"]

  :cljsbuild {
    :builds [{:id "dev"
              :source-paths ["src/cljs"]
              :compiler {
                :output-to  "resources/public/js/app.js"
                :output-dir "resources/public/js/out"
                :optimizations :none
                :source-map true}}]})
