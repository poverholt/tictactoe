(ns ttt.core
  (:gen-class))

;; Convert board representation from grid to linear format.
;; row-col converts from [[00 01 02][10 11 12][20 11 22]] to
;; [00 01 02 10 11 12 20 11 22]
(defn linear-board [board] (vec (flatten board)))

;; Indices within linear board format for sequence of three marks
;; along the three rows, three columns and two diagonals.
(def lines-of-three
  ['(0 1 2)
   '(3 4 5)
   '(6 7 8)
   '(0 3 6)
   '(1 4 7)
   '(2 5 8)
   '(0 4 8)
   '(2 4 6)])

;; Test boards
(def b1 [[:x :o :x][:x :o :o][:x :x :o]])
(def b2 [[:o :x :x][:x :o :x][:x :o :o]])
(def b3 [[:x :o :x][:x :o :x][:o :x :o]])
(def b4 [[:x :x :x][:o :o :o][:o :x :o]])
(def b5 [[nil nil nil][nil nil nil][nil nil nil]])
(def b6 [[nil nil nil][:x :x :x][:o :o nil]])

;; Get data for 1 line. Line is range 0 - 7 for 3 rows, 3 cols & 2 diags.
(defn get-line [board, line-num]
  (let [linear-board (linear-board board)
        line-indices (lines-of-three line-num)]
    (map #(linear-board %) line-indices)))

(defn winning-line? [player, line-data]
  (every? #(= player %) line-data))

;; player is :x or :o
(defn win-candidate? [board, player]
  (some #(winning-line? player %) (map #(get-line board %) (range 7))))

(defn ttt [board]
  (let [x-wins (win-candidate? board :x)
        o-wins (win-candidate? board :o)]
    (cond
      (and x-wins o-wins) :tie
      x-wins :x
      o-wins :o
      :else nil)))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (do 
    (println "Winner of b1 should be x. Winner is " (ttt b1))
    (println "Winner of b2 should be o. Winner is " (ttt b2))
    (println "Winner of b3 should be nil. Winner is " (ttt b3))
    (println "Winner of b4 should be tie. Winner is " (ttt b4))
    (println "Winner of b5 should be nil. Winner is " (ttt b5))
    (println "Winner of b6 should be x. Winner is " (ttt b6))))
