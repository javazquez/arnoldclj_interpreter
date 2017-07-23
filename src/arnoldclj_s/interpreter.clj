(ns arnoldclj_s.interpreter
 (:require [instaparse.core :as insta]
           [arnoldclj_s.lexr :as lexr] 
           [clojure.zip :as z]
           [clojure.walk :as w]))

(def symbol-table (atom {}))

(defn set-value [varname value]
  (if value
    (swap! symbol-table merge
           {(keyword varname) value})) 
  [])

(defn check-table [varitem]
  (let [variable (keyword varitem)]
    (if (contains? @symbol-table variable)
      (get @symbol-table variable) 
      varitem)))

(defmulti run (fn [s] (nth s 0)))

(defn run-statements 
  "runs statements until a return is found"
  [ [statement & statements :as all-stats]]
  (if (= :call-method statement)
    (run all-stats) ;dispatch to the call-method
    (let [return-val (run statement)]
      (if (or (and (map? return-val)
                   (contains? return-val :return))
              (empty? statements) 
              (empty? statement))
        return-val
        (recur statements)))))

(defmethod run :Program [[e  & rest :as statements]]
  (let [ {[bmain] true method-des false} (group-by #(= :begin-main (first %)) rest)]
    (try 
      (run-statements method-des)
      (run  bmain)
      (catch Exception e (throw e))
      (finally
        (reset! symbol-table {})))))

(defmethod run :begin-main [[e statement & rest]]
  (run statement)
  (run-statements rest))

(defmethod run :statement [[s statement]]
  (if (= (nth statement 0) :assign-var-from-method-call)
    (set-value (nth (nth statement 1) 1)
               (run (nth statement 2)))
    (run statement)))

(defmethod run :assignment [[e [_variable var] val]]
  (set-value var
             (run val))
  {(keyword var) (check-table var)})

(defn choose-op [kword]
  (case kword
    :plus +
    :minus -
    :mult *
    :modulo rem
    :div quot)) 

(defn bool->digit 
  "convert clojure true/false to arnoldc true/fale, which is 1 or 0"
  [value]
  (if (= value true)
    1
    0))

(defn digit->bool 
  "convert arnoldc true = 1 and false = 0 to clojure true/false"
  [value]
  (if (zero? value)
    false
    true))

(defn arnold-or [op1 op2] 
  (if (digit->bool op1)
    op1
    op2))

(defn arnold-and [op1 op2]
  (if (and (digit->bool op1) 
           (digit->bool op2))
    1
    0))

(defn choose-logic-op [kword]
  (case kword
    :or arnold-or
    :and arnold-and
    :equalto (fn [op1 op2]
               (bool->digit (= op1 op2)))
    :gt (fn [op1 op2] 
          (bool->digit(> op1 op2))))) 

(defn arithmetic-helper [op-rand arithmetic-op-statements] 
  (loop [ operand op-rand
         [arithmetic-op-statement & rest] arithmetic-op-statements]
    (if arithmetic-op-statement 
      (let [[arith-key [operator varnum-node]]  arithmetic-op-statement]
        (recur ( (case arith-key
                   :logical-op (choose-logic-op operator)
                   :arithmetic-op (choose-op operator)) 
                operand 
                (run varnum-node)) 
               rest))
      operand)))


(defmethod run :set-val [[sv statement & rest]]
  (arithmetic-helper (run statement) rest))  

(defmethod run :printing [[e val]] 
  (println (run  val)))

(defmethod run :decvar [[e var val]]  
  (if (contains? @symbol-table (keyword (nth var 1)))
    (throw (Exception. 
            (str  "WHAT THE FUCK DID I DO WRONG? You made duplicate variable declarations '" var "'")))  
    (set-value (nth var 1)
               (run  val))))

(defmethod run :while [[e  truthy & statements]]
  (loop [stats statements]
    (if (digit->bool (run truthy))
      (do
        (run-statements stats)
        (recur stats)))))

(defmethod run :zero [[e val]] 0)


(defmethod run :assign-var-from-method-call [[e variable method-call]] 
  (set-value (run variable)
             (:return (run-statements method-call))))

(defmethod run :default [[x]]
  [])

(defmethod run :init-val [[e val ]]
  (run val))

(defmethod run :bool [[e val ]]
  (run val))

(defmethod run :true [[e val]]
  1)

(defmethod run :false [[e val]]
  0)

(defmethod run :number [[e val]] 
  (read-string val))

(defmethod run :variable [[e val]]
  (let [ret (check-table val)]
    ret))

(defmethod run :method-name [[e name]] 
  (check-table name)) 

(defmethod run :quotedstring [[e val]]
  val)

(defmethod run :method-statement [[ms statement]] 
  (run statement))

(defn transform-method-variables [name-prefix statements ]
  (map #(w/postwalk (fn [node]
                      (if (and 
                           (vector? node)
                           (= (first node) :variable))
                        [:variable (str name-prefix (second node))]
                        node)) % ) statements))

(defmethod run :call-method [[e name-node & args]]
  (let [method-name (nth name-node 1)
        method (check-table (run name-node))
        name-prefix (gensym (str method-name "-"))
        { meth-args true statements false } (group-by #(= :method-arg (first %)) method)
        new-meth-args (map (fn [meth-arg] (str name-prefix (run meth-arg))) meth-args) 
        arg-values (map (fn [arg] (check-table (run arg))) args)
        arg-pairs (partition 2 (interleave new-meth-args arg-values))
        method-stats (transform-method-variables name-prefix statements)]
    (if args
      (do
        (run! (fn [[var value] ] 
                (set-value var
                           value))
              arg-pairs) 
        (run-statements method-stats))          
      (run-statements statements)))) 

(defmethod run :method-declaration [[e varname-node & rest]] 
  (set-value (run varname-node)
             rest))

(defmethod run :non-void-method [[e & statements]]
  (let [ret-val  (run-statements statements)]
    (if (map? ret-val) 
      (:return ret-val)
      ret-val)))

(defmethod run :method-return [[e varname]]
  {:return (run varname)})

(defmethod run :return [[e varname]]
  {:return (run varname)})

(defmethod run :if [[e truthy & statements]]
  (let [ [else-statements if-statements]  ((juxt filter remove) #(= :else-if (first %)) statements)]
    (if (digit->bool (run truthy))
      (run-statements  if-statements)
      (run-statements else-statements))))

(defmethod run :else-if [[e & stats]]
  (run-statements stats))

(defmethod run :method-arg [[e value]]
  (run value))

;;roll-credits to execute the program
(defn roll-credits [& string-text] 
  (let [program-nodes (lexr/lights-camera-action 
                       (clojure.string/join  string-text))]
    (run program-nodes)))
