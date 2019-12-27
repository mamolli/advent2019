(ns advent2019.day2a
  (:require [clojure.string :as str]))

(defn get-codes
  "Get codes from path"
  [path]
  (map #(Long/parseLong %) (str/split (slurp path) #",")))

(def codes (vec (get-codes "src/day2/input-data.txt")))
(def codes-fixed (-> codes
                     (assoc 1 12)
                     (assoc 2 2)))
(def ops {1 +, 2 *, 99 :halt})

(defn run-op [codes part]
  (let [[opcode, ix0, ix1] part
        op (ops opcode)
        val0 (nth codes ix0)
        val1 (nth codes ix1)]
; lots of uncovered corner cases here
    (if (= op :halt)
      nil
      (op val0 val1))))

(defn process-codes
  [codes]
  (loop [ix 0
          c codes]
    (let [slice-start (* 4 ix)
          part (subvec codes slice-start (+ slice-start 4))
          i (nth part 3)
          value (run-op c part)]
      (if (nil? value)
        c
        (recur (inc ix) (assoc c i value))))))

(first (process-codes codes-fixed))