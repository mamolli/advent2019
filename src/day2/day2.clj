(ns advent2019.day2
  (:require [clojure.string :as str])
  (:require [clojure.core.reducers :as r]))

  

(defn get-input
  "Get codes from path"
  [path]
  (mapv #(Long/parseLong %) (str/split (slurp path) #",")))

(def memory (get-input "src/day2/input-data.txt"))
(def memory-A (-> memory
                  (assoc 1 12)
                  (assoc 2 2)))

(defn change-memory 
  [c noun verb]
  (-> c
      (assoc 1 noun)
      (assoc 2 verb)))

(def ops {1 +, 2 *, 99 :halt})

(defn run-op'
  [memory instruction]
  (let [[opcode ix0 ix1 out-ix] instruction
        op (ops opcode)
        val0 (get memory ix0)
        val1 (get memory ix1)]
; lots of uncovered corner cases here   
    (if (= op :halt)
      memory
      (assoc memory out-ix (op val0 val1)))))
    

(count (partition-all 4 memory))
; not allowing for turing completeness?
(defn process-memory
  [memory]
  (loop [ix 0
         mem memory]
    (let [chunked-memory (partition-all 4 mem)
          length (count chunked-memory)
          ins (nth chunked-memory ix)]
      (if (< ix (dec length))
        (recur (inc ix) (run-op' mem ins))
        mem))))

(def test-values
  (for [noun (range 100) 
        verb (range 100)]
    [noun verb]))


(defn get-0-val-from-processing [noun verb]
  [(first (process-memory (change-memory memory noun verb))) noun verb])


; SOLUTION A
(first (process-memory memory-A))

; SOLUTION B
(first
  (filter #(= 19690720 (get % 0)) (map #(apply get-0-val-from-processing %) test-values)))


