(ns blog.core
  (:require [clojure.java.io :as io]
            [markdown.core :as md]
            [clojure.string :as str]
            [clojure.instant :as inst]))

;; Let's set the directory for the blog post markdown files
(def posts-dir "./resources/posts/")

;; Let's set the directory for the output of the build website
(def output-dir "./resources/output/")

;; Let's set the directory for static resources to be used when building the blog
(def static-resources-dir "./resources/static/")

;; Let's define the HTML that goes above the blog posts html
(def html-header
  (slurp (str static-resources-dir "header.html")))

;; Let's define the HTML that goes below the blog posts html
(def html-footer
  (slurp (str static-resources-dir "footer.html")))

; Let's attempt to load the directory for the posts directory
#_(map #(.toString %) (.list (io/file posts-dir)))

;; Let's load all the markdown files in the directory passed
(defn get-md-files
  "Return a sequence of all markdown files in dir"
  [dir]
  (->> (io/file dir)
       (file-seq)
       (filter #(str/ends-with? (.getName %) ".md"))))

;; Let's get the date from the file name of a file
(defn extract-date-from-filename
  "Will extract the date from a file with the format `date#filename`"
  [file]
  (first (str/split (.getName file) #"#")))

;; Let's read the blog posts and provide a date keyword that we can use to sort the posts by
(defn process-blog-post
  "Read the blog post markdown file and return a map where
  the first line of the file is the :date keyword and the rest
  of the file is the :content keyword"
  [file]
  {:date (inst/read-instant-date (extract-date-from-filename file))
   :content (md/md-to-html-string (slurp file))})

;; Let's generate the HTML for all the blog posts in the posts directory and return it as a string
(defn generate-blog-post-html [posts-dir]
  (->> posts-dir
       (get-md-files)
       (map process-blog-post)
       (sort-by :date #(compare %2 %1))
       (map :content)
       (str/join "<hr />")))


;; Let's generate the full HTML for the blog page
(defn create-blog-html []
  (str html-header
       (generate-blog-post-html posts-dir)
       html-footer))

;; Let's create the blog HTML and output it as a file to the output-dir
(defn build-blog
  "Build the static blog website HTML"
  []
  (spit (str output-dir "blog.html") (create-blog-html)))

;; Let's actually do it
(build-blog)
