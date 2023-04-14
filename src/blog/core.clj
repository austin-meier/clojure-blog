(ns blog.core
  (:require [clojure.java.io :as io]
            [markdown.core :as md]
            [clojure.string :as str]
            [clojure.instant :as inst]))

;; Set the directory for the blog post markdown files
(def blog-dir "./blog/")

;; Let's define the HTML that goes above the blog posts html
(def html-header
  (slurp (str blog-dir "static/header.html")))

;; Let's define the HTML that goes below the blog posts html
(def html-footer
  (slurp (str blog-dir "static/footer.html")))

; Let's attempt to load the directory for the posts directory
#_(map #(.toString %) (.list (io/file posts-dir)))

(defn get-md-files
  "Return a sequence of all markdown files in dir"
  [dir]
  (->> (io/file dir)
       (file-seq)
       (map #(.toString %))
       (filter #(str/ends-with? % ".md"))))


;;(map slurp (get-md-files posts-dir))

(defn process-blog-post
  "Read the blog post markdown file and return a map where
  the first line of the file is the :date keyword and the rest
  of the file is the :content keyword"
  [file]
  (with-open [rdr (io/reader file)]
    (let [lines (vec (line-seq rdr))]
      {:date (inst/read-instant-date (first lines))
       :content (md/md-to-html-string (str/join "\n" (rest lines)))})))



(defn run [posts-dir]
  (->> posts-dir
       (get-md-files)
       (map process-blog-post)
       (sort-by :date #(compare %2 %1))
       (map :content)
       (str/join "<hr />")))


(defn create-blog-html []
  (str html-header
       (run blog-dir)
       html-footer))

(defn build-blog
  "Build the static blog website HTML"
  []
  (spit "./blog/output/blog.html" (create-blog-html)))

(build-blog)
