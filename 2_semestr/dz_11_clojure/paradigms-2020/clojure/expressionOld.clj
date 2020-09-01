; hw 10 & hw 11
(defn makeOper [function] (fn [& fs] (fn [var] (apply function (mapv #(% var) fs)))))
(def constant constantly)
(defn variable [var] (fn [vars] (get vars (str var))))
(def add (makeOper +))
(def subtract (makeOper -))
(comment ":NOTE: same as subtract (copy-paste)")
(def negate (makeOper -))
(def multiply (makeOper *))
(def divide (makeOper (fn [num & denoms] (/ (double num) (apply * denoms)))))
(def avg (makeOper (fn [& args] (/ (double (apply + args)) (count args)) ) ))
(def med (makeOper (fn [& args] (nth (sort args) (/ (count args) 2) )) ))

(def opersF {'+ add '- subtract '* multiply '/ divide 'negate negate 'avg avg 'med med})
(defn parseFunction [str]
    (condp #(%1 %2) str
        symbol? (variable str)
        string? (parseFunction (read-string str))
        number? (constant str)
        list? (apply (opersF (first str)) (mapv parseFunction (rest str)))))
;
;!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
;
;           hw 11
;
;!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
;
(definterface Expression
    (evaluate [var])
    (string [])
    (diff [diffed])
    (diffArgs [var])
    (infix [])
)

(defn evaluate [this vars] (.evaluate this vars))
(defn toString [this] (.string this))
(defn diff [this var] (.diff this (.diffArgs this var)))
(defn toStringInfix [this] (.infix this))

(declare Zero)
(declare One)

(deftype CVProto [value]
    Expression
    (evaluate [this vars] (if (number? value)
                         value
                         (get vars (str value))))
    (string [this] (if (number? value)
                         (format "%.1f" value)
                         (str value)))
    (diff [this diffed] diffed)
    (diffArgs [this var] (if (number? value)
                          Zero
                          (if (= value var) One Zero )))
    (infix [this] (.string this)))

(deftype binOperProto [op, ch, fdiff, fs]
    Expression
    (evaluate [this vars] (apply op (mapv #(evaluate % vars) fs ) ) )
    (string [this] (str "(" ch (apply str (mapv #(str " " (toString %)) fs)) ")"))
    (diff [this diffed] (fdiff fs diffed ) )
    (diffArgs [this var] (mapv #(diff % var) fs))
    (infix [this] (str "(" (toStringInfix (first fs)) " " ch " " (toStringInfix (second fs)) ")")))

(deftype unOperProto [op, ch, fdiff, f]
    Expression
    (evaluate [this vars] (op (evaluate f vars)))
    (string [this] (str "(" ch " " (toString f) ")"))
    (diff [this diffed] (fdiff f diffed))
    (diffArgs [this var] (diff f var))
    (infix [this] (str ch "(" (toStringInfix f) ")")))

(defn Constant [value] (CVProto. value))
(def Zero (Constant 0))
(def One (Constant 1))
(defn Variable [name] (CVProto. name))

(defn binCreator [op, ch, fdiff] (fn [& f] (binOperProto. op ch fdiff f)))
(defn unoCreator [op, ch, fdiff] (fn [f] (unOperProto. op ch fdiff f)))

(def Add (binCreator + "+" (fn [fs diffed] (apply Add diffed))))
(def Sum (binCreator + "sum" (fn [fs diffed] (apply Add diffed))))
(def Subtract (binCreator - "-" (fn [fs diffed] (apply Subtract diffed))))
(def Multiply (binCreator * "*" (fn [fs diffed] (letfn [(rd [args difd]
                    (if (= (count args) 1 )
                    (first difd)
                    (Add (Multiply (first difd) (apply Multiply (rest args)))
                         (Multiply (first args) (rd (rest args) (rest difd) )))))] (rd fs diffed)))))
(def Divide (binCreator (fn [num & denoms] (/ (double num) (apply * denoms))) "/"
                    (fn [args difd]
                                            (reduce
                                                (fn [[a ad] [b bd]]
                                                    (Divide
                                                        (Subtract (Multiply ad b) (Multiply bd a) )
                                                        (Multiply b b)))
                                                 (mapv vector args difd)))))
(def Negate (unoCreator - "negate" (fn [f diffed] (Negate diffed))))
(def Avg (binCreator (fn [& fs] (/ (apply + fs) (count fs))) "avg" (fn [fs diffed] (apply Avg diffed))))
(def Pow (binCreator (fn [base power] (Math/pow base power)) "**"
                (fn [fs diffed]
                    (Multiply
                        (Multiply
                            (second fs)
                            (Pow (first fs)
                                (Subtract
                                    (second fs)
                                (Constant 1))))
                        (second diffed)))))
(def Log (binCreator
    (fn [num base] (/ (Math/log (Math/abs base)) (Math/log (Math/abs num)))) "//"
    (fn [fs diffed]
        (Divide
            (Multiply
                (first diffed)
                (second diffed))
            (Multiply
                (first fs)
                (Log (second fs) (Constant (Math/exp 1))))))))

(def opersO {'+ Add '- Subtract '* Multiply '/ Divide 'negate Negate 'sum Sum 'avg Avg})
(defn parseObject [inp]
    (condp #(%1 %2) inp
        symbol? (Variable (str inp))
        string? (parseObject (read-string inp))
        number? (Constant inp)
        list? (apply (opersO (first inp)) (mapv parseObject (rest inp)))))

;
; //hw 12
;
(defn -return [value tail] {:value value :tail tail})
(def -valid? boolean)
(def -value :value)
(def -tail :tail)
(defn _show [result]
    (if (-valid? result) (str "-> " (pr-str (-value result)) " | " (pr-str (apply str (-tail result))))
                        "!"))
(defn tabulate [parser inputs]
    (run! (fn [input] (printf "    %-10s %s\n" (pr-str input) (_show (parser input)))) inputs))
(defn _empty [value] (partial -return value))
(defn _char [p]
    (fn [[c & cs]]
        (if (and c (p c)) (-return c cs))))
(defn _map [f result]
           (if (-valid? result)
             (-return (f (-value result)) (-tail result))))
(defn _combine [f a b]
    (fn [str]
        (let [ar ((force a) str)]
            (if (-valid? ar)
                (_map (partial f (-value ar))
                    ((force b) (-tail ar)))))))
(defn _either [a b]
    (fn [str]
        (let [ar ((force a) str)]
            (if (-valid? ar) ar ((force b) str)))))
(defn _parser [p]
    (fn [input]
        (-value ((_combine (fn [v _] v) p (_char #{\u0000})) (str input \u0000)))))
(defn +char [chars] (_char (set chars)))
(defn +char-not [chars] (_char (comp not (set chars))))
(defn +map [f parser] (comp (partial _map f) parser))
(def +parser _parser)
(def +ignore (partial +map (constantly 'ignore)))
(defn iconj [coll value]
    (if (= value 'ignore) coll (conj coll value)))
(defn +seq [& ps]
    (reduce (partial _combine iconj) (_empty []) ps))
(defn +seqf [f & ps] (+map (partial apply f) (apply +seq ps)))
(defn +seqn [n & ps] (apply +seqf (fn [& vs] (nth vs n)) ps))
(defn +or [p & ps] (reduce _either p ps))
(defn +opt [p] (+or p (_empty nil)))
(defn +star [p] (letfn [(rec [] (+or (+seqf cons p (delay (rec))) (_empty ())))] (rec)))
(defn +plus [p] (+seqf cons p (+star p)))
(defn +str [p] (+map (partial apply str) p))

(def *space (+char " \t\n\r"))
(def *ws (+ignore (+star *space)))
(def *digit (+char "0123456789"))
(def *number (+map read-string (+str (+plus *digit))))


(def *double (+map read-string (+seqf str (+opt (+char "-")) *number (+opt (+seqf str (+char ".") *number)))))
(defn +word [st] (apply +seq (mapv #(+char (str %)) st)))
(defn inWs [op] (+seq *ws op *ws))

(def opersP {
             '+ Add
             '- Subtract
             '* Multiply
             '/ Divide
             '** Pow
             (symbol "//") Log
             'negate Negate})
(defn baseOpPrior [& args] (+seqf #(get opersP (symbol (apply str %))) *ws (apply +or (mapv +word args)) *ws ))
(def *var (+seqf #(Variable (str %)) (+char "xyz")))
(def *const (+seqf #(Constant (double %)) *double))
(def *prior0 (+or *const *var))
(def *prior1 (baseOpPrior "+" "-"))
(def *prior2 (baseOpPrior "*" "/"))
(def *prior3 (baseOpPrior "**" "//"))
(def *prior0u (baseOpPrior "negate"))

(defn single_func [args] ((first args) (nth args 1)))
(defn side_abstract_func [right?] (fn [a other] (letfn
    [(f [a other]
        (if (= (count other) 2)
            ((first other) a (nth other 1))
            (if right?
                (f ((first other) a (nth other 1)) (rest (rest other)))
                ((first other) a (f (nth other 1) (rest (rest other)))) )))]
    (if (> (count other) 1) (f a other) a)))  )

(def right_func (side_abstract_func true))
(def left_func (side_abstract_func false))
(defn flat [& args] (flatten args))
(declare *highestLvl)

(def *deepestLvl (+or
    (inWs *prior0)
    (+seqf single_func (+seqf flat (inWs *prior0u) (delay *deepestLvl) ))
    (+seqn 1 (inWs (+char "(")) (delay *highestLvl) (inWs (+char ")"))) ))
(defn lvl [func argType prior] (+seqf #(func (first %) (rest %) ) (+seqf flat argType (+star (+seqf flat prior argType)))))
(def *firstLvl (lvl left_func *deepestLvl *prior3))
(def *secondLvl (lvl right_func *firstLvl *prior2))
(def *highestLvl (lvl right_func *secondLvl *prior1))
(defn parseObjectInfix[expr] ((_parser *highestLvl) expr))
