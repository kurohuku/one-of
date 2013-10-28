(ns one-of.core
  (:require clojure.string re-rand))

(defprotocol POneOf
  (one-of [x]))

(defprotocol PFixConst
 (fix [x])
 (const [x]))

(extend-type Object
  POneOf
  (one-of [x] x)
  PFixConst
  (fix [x] (let [fixed (one-of x)]
             (reify POneOf
               (one-of [_] fixed))))
  (const [x] (reify POneOf
               (one-of [_] x))))

(extend-protocol POneOf
  clojure.lang.Seqable
  (one-of [x] (rand-nth x))
  java.util.regex.Pattern
  (one-of [x] (let [ret (re-rand/re-rand x)]
                (if (vector? ret)
                  (first ret)
                  ret))))

(defn funcall [f & args]
  (let [as (map one-of args)]
    [f as (apply f as)]))
