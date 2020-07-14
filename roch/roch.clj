(ns roch
  (:require [clojure.java.io :as io]
            [clojure.data.csv :as csv]))


;; recursively descend into folders and find invoices with Camp or Roch in the title.
;; parse the date of the file name it will be either 20150425 format or 2015-04-25 format.
;; input: directory where the parser will start to look
;; output: a csv file with date, filename, link to file.

(defn file-tree
  [^java.io.File path]
  (let [children (.listFiles path)
        dir? (.isDirectory path)]
    (cond->
        {:name (.getName path)
         :type (if dir? "directory" "file")}
      dir? (assoc :contents
                  (map file-tree children)))))

;; main function
(let [[path] *command-line-args*]
  (when (empty? path)
    (println "Usage: bb -f roch.clj <path>")
    (System/exit 1))
  (file-tree (io/file path)))

(comment
  (def dir "/Users/zand/tmp/receipts/")
  (def path (io/file dir))
  (.isDirectory path)
  (def children (.listFiles path))
  (file-tree (io/file dir)))

