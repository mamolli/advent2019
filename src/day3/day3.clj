(ns advent2019.day3
  (:require [clojure.string :as str])
  (:require [clojure.set :as set])
  (:require [clojure.math.numeric-tower :as math]))

(defn get-input
  "Get codes from path"
  [path]
  (str/split (slurp path) #"\n"))

(defn point-distance [point]
  (+ (math/abs (first point)) (math/abs (second point))))

;ranges work weirdly, will reimplement my own crap I guess
(defn irange
  [start end]
  (if (<= start end)
    (range start (inc end))
    (range start (dec end) -1)))

(defn apply-delta
  "Apply delta instruction and return vector of 2-point vectors."
  ; there should be some checking if points are forming a line
  [startp delta]
  (mapv + startp delta))

(defn dline [startp delta]
  "Take a start and endpoint(2-elem vectors) and return a line as vector of vectors(2-elem vectors)"
  (for 
    [x (irange (first startp) (first (apply-delta startp delta)))
      y (irange (second startp) (second (apply-delta startp delta)))]
    [x y]))

; using recursion, but it should be handled as reduction
(defn reduce-points [delta-points]
  "Reducing points based on delta"
  (loop [all-points (lazy-seq [[0 0]])
         deltas delta-points]
    (if (empty? deltas)
      all-points
      (recur
        (lazy-cat all-points (dline (last all-points) (first deltas)))
        (drop 1 deltas)))))

(defn split-delta-ins
  "Convert instruction into 2-point delta vector."
  [ins]
  (let [initial [0 0]
        direction (subs ins 0 1)
        distance (Integer. (subs ins 1))
        [axis operation] (directions direction)]          
    (assoc initial axis (operation distance))))



(def initial [0 0])
(def directions {"R" [0 identity], "L" [0 -], "U" [1 identity], "D" [1 -]})
(def cables-data (mapv #(str/split % #",") (get-input "src/day3/input-data.txt")))
(def cables-delta-data (mapv #(mapv split-delta-ins %) cables-data))

; result data
(def reduced-cables (map #(set (reduce-points %)) cables-delta-data))
(def intersection (disj (apply set/intersection reduced-cables) initial))
;clostest helper = abs +
(defn abs-distance [point]
  (+ (math/abs (first point)) (math/abs (second point))))

(def closest-point (first (sort #(compare (abs-distance %1) (abs-distance %2)) intersection)))
(def closest-point-distance (abs-distance closest-point))
; from [0 0]
