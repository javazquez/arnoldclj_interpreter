(ns arnoldclj_s.pig-latin-lexr
 (:require [instaparse.core :as insta]
           [instaparse.failure :as fail]
           [arnoldclj_s.lexr :as arnie]))


(defn- transform-consonant-word[ cluster word]
  (let [reduced-word (clojure.string/replace-first word cluster "")]
    (str reduced-word cluster "AY")
    ))

(defn- pig-latin [word]
  "translate a word into pig latin
if word starts with consonant or consonant cluster add consonant or consonant clusters to the end and add 'ay' to the end
if word starts with a vowel add 'way' to the end 
"
  (let [uword (.toUpperCase word)
        consonant-cluster (re-find  #"^[^AEIOU]+" uword)]
    (if (not-empty consonant-cluster)
      (transform-consonant-word consonant-cluster uword )
      (str uword "WAY")
      ))
  )

(defn- update-map [m f]
"transform values within a map"
  (reduce-kv (fn [m k v]
               (assoc m k (f v))) {} m))

(defn- translate-to-pig-latin [string-of-text] 
  "translate a string of text into pig-latin"
  (->> (map pig-latin (clojure.string/split string-of-text #"\s+")) 
       (clojure.string/join " ")))

(def pig-latin-arnoldc
  (-> (update-map arnie/tokens translate-to-pig-latin)
      (arnie/arnold-grammar)
      (insta/parser)))

(defn ights-camera-actionlay [& expr]
"interpret pig-latin text"
  (try (->> (arnie/parser pig-latin-arnoldc (clojure.string/join expr))
            (insta/transform arnie/transform-ops))
       (catch Exception e 
         (throw (Exception.  (str "EREWHAY ETHAY UCKFAY IDDAY IWAY OGAY ONGWRAY?" (.getMessage e)))))))
