(ns roch
  (:require [clojure.java.io :as io]
            [clojure.data.csv :as csv]
            [clojure.pprint :as pprint :refer [pprint]]))

;; recursively descend into folders and find invoices with Camp or Roch in the title.
;; skip tmp directories
;; parse the date of the file name it will be either 20150425 format or 2015-04-25 format
;; input: directory where the parser will start to look
;; output: a csv file with date, filename, link to file.

(def search-string "roch|Roch|fishing|Fishing|camp|Camp")
(def pattern (re-pattern search-string))

(defn file-tree
  [^java.io.File path]
  (let [children (.listFiles path)
        dir? (.isDirectory path)]
    (cond->
        {:name (.getName path)
         :type (if dir? "directory" "file")
         :path (.getAbsolutePath path)}
      dir? (assoc :contents
                  (map file-tree children)))))

(defn insert-dashes
  "Inserts dashes in a string at position"
  [s pos]
  (str (subs s 0 pos) "-" (subs s pos)))

(defn make-date
  "Takes a name of a file as a string a returns a string formatted as YYYY-MM-DD"
  [filename]
  (let [raw-date (re-find #"\d+" filename)]
    (if (nil? raw-date) "1946-10-30")))

;;(defn process-tree
;;  [tree]
;;  (cond->))
;;
;;(defn process-item
;;  "Returns a vector of date, filename and path for items that match pattern, else nil"
;;  [itm pat]
;;  (when (re-find pat (:name itm))
;;    [(make-date (:name itm)) (:name itm) (:path itm)]))
;;

(defn main []
  (let [[path] *command-line-args*]
    (when (empty? path)
      (println "Usage: bb -f roch.clj <path>")
      (System/exit 1))
    (file-tree (io/file path))))

(comment
  (def raw-date "20200201")
  (def year (subs raw-date 0 4))
  (def month (subs raw-date 4 6))
  (def day (subs raw-date 6 8))
  (str year "-" month "-" day)
  (subs "20200101" 4)
  (insert-dashes "20200101" 4)

  (def dir "/Users/zand/tmp/receipts/")
  (def path (io/file dir))
  (.isDirectory path)
  (def children (.listFiles path))
  (file-tree (io/file dir))
  #_ (:placeholder))


