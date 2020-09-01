(defn makeOper [function] (fn [& fs] (fn [var] (apply function (mapv #(% var) fs)))))
(def constant constantly)
(defn variable [var] (fn [vars] (get vars (str var))))
(def add (makeOper +))
(def subtract (makeOper -))
(def negate (makeOper -))
(def multiply (makeOper *))
(def divide (makeOper (fn [num & denoms] (/ (double num) (apply * denoms)))))
;(def avg (makeOper (fn [& args] (/ (double (apply + (cons 0 args))) (max 1 (count args))) ) )) поддерживает 0 аргументов
(def avg (makeOper (fn [& args] (/ (double (apply + args)) (count args)) ) ))
(def med (makeOper (fn [& args] (nth (sort args) (/ (count args) 2) )) ))

(def opers {'+ add '- subtract '* multiply '/ divide 'negate negate 'avg avg 'med med})
(defn parseFunction [str]
    (condp #(%1 %2) str
        symbol? (variable str)
        string? (parseFunction (read-string str))
        number? (constant str)
        list? (apply (opers (first str)) (mapv parseFunction (rest str)))))

