(defn checkLen [data] (or (every? number? data) (every? #(= (count (first data)) (count %)) data)))
(defn apTo [f] (fn [& data] {:pre [(every? vector? data) (checkLen data)]} (apply mapv f data)))
(defn mulNums [nums] (apply * nums))
(def v+ (apTo +))
(def v- (apTo -))
(def v* (apTo *))
(defn scalar [& v] (apply + (apply v* v)))
(comment ":NOTE: still need pass both vectors and indices")
(defn vect [& v] {:pre[(checkLen v)]} (reduce (fn [a b]
                                        (letfn [(minor [i j] (- (* (a i) (b j)) (* (a j) (b i))))]
                                        (vector (minor 1 2) (minor 2 0) (minor 0 1)))) v))
(defn v*s [v & scs] {:pre [(vector? v) (every? number? scs)]} (let [sum (mulNums scs)] (mapv #(* %1 sum) v)))
(def m+ (apTo v+))
(def m- (apTo v-))
(def m* (apTo v*))
(defn m*s [m & scs] {:pre [(vector? m) (every? number? scs)]} (let [sum (mulNums scs)] (mapv #(v*s %1 (mulNums scs)) m)))
(defn transpose [m] {:pre [(vector? m)]} (apply mapv vector m))

(defn m*v [m v] {:pre [(vector? m) (vector? v) (every? #(= (count v) (count %) ) m)]} (mapv #(scalar % v) m))
(comment ":NOTE: too many transposes")
(defn m*m [& m]
    (reduce (fn  [m1 m2] {:pre [(vector? m1)(vector? m2)]} (mapv #(m*v (transpose m2) %) m1))
            (first m) (rest m)))


(defn tensor? [t]
    (if (every? number? t)
        true
        (if (every? vector? t)
            (if (checkLen t)
                (every? tensor? (apply mapv vector t))
                false)
            false)))

(defn rt [f & t] {:pre [(checkLen t)] } (
    if (every? number? t)
        (apply f t)
        (apply mapv (partial rt f) t)
))

(defn tensor [f & t] {:pre [(every? tensor? t)]}
    (apply rt f t))

(def t+ (partial tensor + ))
(def t- (partial tensor - ))
(def t* (partial tensor * ))
