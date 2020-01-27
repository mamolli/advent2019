(ns advent2019.day4
  (:require [clojure.string :as string])
  (:require [clojure.set :as set])
  (:require [clojure.math.numeric-tower :as math]))

;PART 1
(def input [138307 654504])
(def digit_vector (map #(Integer/parseInt %) (string/split (str (first input)) #"")))
(sorted? digit_vector)
