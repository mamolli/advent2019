(ns advent2019.day1
  (:require [clojure.string :as str]))

(defn get-mods-masses
  "Get input from path"
  [path]
  (map #(Long/parseLong %) (str/split-lines (slurp path))))

(defn fuel-mod
  "Calculate required fuel based on mass(int)"
  [mod-mass]
  ; negative fuel makes no sense
  (let [fuel-required (int (- (Math/floor (/ mod-mass 3)) 2))]
    (if (< fuel-required 0) 0 fuel-required)))

; (defn mods-fuel
;   "Calculate required fuel for all modules"
;   [mods-masses]
;   (map mod-fuel (get-mods-masses mods-masses)))

(defn fuel-mod-tanks
; strange recur TODO: find more idiomatic way?
  "Calculate required fuel based on mass(int) of fuel tank"
  ([fuel-mass]
   (fuel-mod-tanks 0 fuel-mass))
  ([fuel-mass-acc fuel-mass]
   (if (<= fuel-mass 0)
     fuel-mass-acc
     (let [current-fuel-mass (fuel-mod fuel-mass)]
       (recur (+ fuel-mass-acc current-fuel-mass) current-fuel-mass)))))

(defn fuel-mods-tanks
  "Calculate required mass of all fuel with fuel appended"
  [module-masses]
  (map fuel-mods-tanks (get-mods-masses module-masses)))

(reduce + (fuel-mods-tanks "src/day1/input-data.txt"))