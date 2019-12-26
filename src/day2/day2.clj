(ns advent2019.day2
  (:require [clojure.string :as str]))

(defn get-mods-masses
  "Get input from url"
  [path]
  (map #(Long/parseLong %) (str/split-lines (slurp path))))

(defn mod-fuel
  "Calculate required fuel based on mass(int)"
  [module-mass]
  (int (- (Math/floor (/ module-mass 3)) 2)))

(defn mods-fuel
  "Calculate required fuel for all modules"
  [module-masses]
  (map mod-fuel (get-mods-masses module-masses)))

(reduce + (mods-fuel "input-data.txt"))