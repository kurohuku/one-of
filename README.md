# one-of

データ集合から要素を1つ選択する関数.

## Usage

```clojure
(ns example
 (:require one-of.core))
 
 (boolean (#{1 2 3} (one-of.core/one-of [1 2 3])))
 ;; => true
 
(repeatedly 3 #(one-of.core/one-of #"[a-z]{1,3}"))
;; => ("hz" "vyx" "ji")

(->> (repeatedly #(one-of.core/funcall * (range 10) (range 10)))
     (filter #(= (third %) 81))
	 first
     second)
;; => [9 9]
```

## License

Copyright © 2013 K.Takahashi

Distributed under the Eclipse Public License, the same as Clojure.
