(ns advent2019.day3
  (:require [clojure.string :as str])
  (:require [clojure.set :as set])
  (:require [clojure.math.numeric-tower :as math]))

;PART 1
(def initial [0 0])
(def directions {"R" [0 identity], "L" [0 -], "U" [1 identity], "D" [1 -]})

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
  ; [0 1] + [2 3] -> [2 4] gg
  (map + startp delta))

(defn line [start-point delta-op]
  "Take a start and endpoint(2-elem vectors) and return a line as vector of vectors(2-elem vectors)"
  (let [delta-point (apply-delta start-point delta-op)]
    (for 
      [x (irange (first start-point) (first delta-point))
       y (irange (second start-point) (second delta-point))]
      [x y])))

(defn reduce-points' [cable-delta-data]
  (reduce #(concat %1 (rest (line (last %1) %2))) [initial] cable-delta-data))

(defn split-delta-ins
  "Convert instruction into 2-point delta vector."
  [ins]
  (let [initial [0 0]
        direction (subs ins 0 1)
        distance (Integer. (subs ins 1))
        [axis operation] (directions direction)]          
    (assoc initial axis (operation distance))))

(def reduced-cables
  (->> (get-input "src/day3/input-data.txt")
       (map #(str/split % #","))
       (map #(map split-delta-ins %))
       (map #(reduce-points' %))))

(def intersections (disj (apply set/intersection (mapv set reduced-cables)) initial))

;clostest intersect = abs +
(defn abs-distance [point]
  "Calculate distance by adding absolutes of points"
  (+ (math/abs (first point)) (math/abs (second point))))

(->> intersections
     (sort #(compare (abs-distance %1) (abs-distance %2)))
     (first)
     (abs-distance))

; PART 2
; i wont comment on clojure's lack of index-of
(defn indexes-of [el coll] (keep-indexed #(if (= el %2) %1) coll)) 
(defn index-of [el coll]
  (first (indexes-of el coll)))

; TODO: this is clunky 
(->> intersections
     (map #(map (fn [cable] (index-of % cable)) reduced-cables))
     (map #(+ (first %) (second %)))
     (apply min))
