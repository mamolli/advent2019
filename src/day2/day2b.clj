(ns advent2019.day2a
  (:require [clojure.string :as str]))

(defn get-input
  "Get codes from path"
  [path]
  (mapv #(Long/parseLong %) (str/split (slurp path) #",")))

(def memory (get-input "src/day2/input-data.txt"))
(defn change-memory [c noun verb]
  (-> c
      (assoc 1 noun)
      (assoc 2 verb)))

(def ops {1 +, 2 *, 99 :halt})

(defn run-op [memory instruction]
  (let [[opcode, ix0, ix1, out-ix] instruction
        op (ops opcode)
        val0 (get memory ix0)
        val1 (get memory ix1)]
; lots of uncovered corner cases here   
    (if (= op :halt)
      nil
      [(op val0 val1) out-ix])))

; the index move is very ugly
(defn process-memory
  [mem]
  (loop [ix 0
         c mem]
    (let [slice-start (* 4 ix)
          instruction (subvec mem slice-start (+ slice-start 4))
          [value out-ix] (run-op c instruction)]
      (if (nil? value)
        c
        (recur (inc ix) (assoc c out-ix value))))))

(def test-values
  (for [noun (range 100) 
        verb (range 100)]
    [noun verb]))


(defn get-0-val-from-processing [noun verb]
  [(first (process-memory (change-memory memory noun verb))) noun verb])

(filter #(= 19690720 (get % 0)) (map #(apply get-0-val-from-processing %) test-values))

