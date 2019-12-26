(ns advent2019.day1
  (:require [clojure.string :as str]))

(defn get-mods-masses
  "Get input from url"
  [path]
  (map #(Long/parseLong %) (str/split-lines (slurp path))))

(defn mod-fuel
  "Calculate required fuel based on mass(int)"
  [mod-mass]
  (int (- (Math/floor (/ mod-mass 3)) 2)))

(defn mods-fuel
  "Calculate required fuel for all modules"
  [mods-masses]
  (map mod-fuel (get-mods-masses mods-masses)))

(reduce + (mods-fuel "input-data.txt"))