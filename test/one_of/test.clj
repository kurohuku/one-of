(ns one-of.test
  (:use clojure.test
        one-of.core))

(defn third [x] (nth x 2))

(deftest test-one-of
  (testing "one-of/one-of"
    (is (= (one-of 0) 0))
    (is (= (one-of "abc") "abc"))
    (dotimes [_ 10]
      (is (#{1 2 3} (one-of [1 2 3]))))
    (dotimes [_ 10]
      (is (#{"ab" "Ab" "ac" "Ac"} (one-of #"[aA][bc]")))))

  (testing "one-of/fix"
    (let [x (fix (range 10))]
      (dotimes [_ 10] (is (one-of x) (one-of x))))
    (let [x (fix #"[a-z]{10}")]
      (dotimes [_ 10] (is (one-of x) (one-of x)))))

  (testing "one-of/const"
    (is (= [1 2] (one-of (const [1 2]))))
    (is (instance? java.util.regex.Pattern (one-of (const #"ab")))))
  
  (testing "one-of/funcall"
    (dotimes [_ 10]
      (is (#{"13" "14" "23" "24"} (third (funcall str [1 2] [3 4])))))
    (is (= [9 9]
           (->> (repeatedly #(funcall * (range 10) (range 10)))
                (filter #(= (third %) 81))
                first
                second)))))
