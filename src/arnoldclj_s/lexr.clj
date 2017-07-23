(ns arnoldclj_s.lexr
 (:require [instaparse.core :as insta]
           [instaparse.failure :as fail]))

(def transform-ops
  {
   :bool (fn [ value] [:bool value])
   :false (fn [value] [:false  0])
   :true (fn [value] [:true  1])
   :one (fn [ value] [:one  1])
   :quotedstring (fn [ text ]  
                   [:quotedstring (clojure.string/replace text #"\"" "")])
   })
    
(def tokens {
             :begin-main "IT\\'S SHOWTIME"
             :error "WHAT THE FUCK DID I DO WRONG"
             :decvar "HEY CHRISTMAS TREE"
             :init-val "YOU SET US UP"
             :plus "GET UP"
             :minus "GET DOWN"
             :mult "YOU\\'RE FIRED"
             :div "HE HAD TO SPLIT"
             :print "TALK TO THE HAND"
             :read "I WANT TO ASK YOU A BUNCH OF QUESTIONS AND I WANT TO HAVE THEM ANSWERED IMMEDIATELY"
             :assignment "GET TO THE CHOPPER"
             :set-val "HERE IS MY INVITATION"
             :end-assignment "ENOUGH TALK"
             :zero "@I LIED"
             :one "@NO PROBLEMO"
             :equalto "YOU ARE NOT YOU YOU ARE ME"
             :gt "LET OFF SOME STEAM BENNET"
             :or "CONSIDER THAT A DIVORCE"
             :and "KNOCK KNOCK"
             :if "BECAUSE I\\'M GOING TO SAY PLEASE"
             :else-if "BULLSHIT"
             :end-if "YOU HAVE NO RESPECT FOR LOGIC"
             :while "STICK AROUND"
             :end-while "CHILL"
             :method-declaration "LISTEN TO ME VERY CAREFULLY"
             :method-arg "I NEED YOUR CLOTHES YOUR BOOTS AND YOUR MOTORCYCLE"
             :return "I\\'LL BE BACK"
             :end-method "HASTA LA VISTA, BABY"
             :call-method "DO IT NOW"
             :non-void-method "GIVE THESE PEOPLE AIR"
             :assign-var-from-method-call "GET YOUR ASS TO MARS"
             :modulo   "I LET HIM GO"
             :end-main "YOU HAVE BEEN TERMINATED"})
            

(defn arnold-grammar 
  "create the arnoldc grammer using the tokens map that is passed in"
  [tokens]
 (str "Program = method-declaration* begin-main wspace  method-declaration*;
       begin-main = <'" (:begin-main tokens) "'> (statement wspace)* end-main ;"
      "<wspace> = <#'\\s*'>;"
      "statement = wspace  (assignment | printing | decvar |  assign-var-from-method-call | 
                                      call-method | if | while | call-method | return);"
      "method-statement = (assignment | printing | decvar  | assign-var-from-method-call | 
                                      call-method | if | while | call-method);"
      "call-method = <'"(:call-method tokens)"'> wspace  method-name  numvar* wspace;"
      "method-name = (number| #'[a-zA-Z][a-zA-Z0-9]*' |zero |false | true) "
      "assign-var-from-method-call = <'" (:assign-var-from-method-call tokens)"'> wspace variable wspace call-method;"
      "assignment = <'"(:assignment tokens)"'> numvar wspace set-val wspace end-assignment wspace;
       while= <'" (:while tokens) "'> numvar wspace statement* end-while ;
       <end-while>= <'" (:end-while tokens) "'>;
       set-val= <'" (:set-val tokens) "'> numvar (arithmetic-op|logical-op)*;
      <end-assignment>= <'"(:end-assignment tokens) "'>;
       printing = <'" (:print tokens)"'> wspace (variable | quotedstring| number) wspace;
       decvar = <'" (:decvar tokens)"'>  numvar wspace init-val;
       if  = <'" (:if tokens)"'> numvar statement* else-if* end-if;
       else-if  = <'" (:else-if tokens)"'>wspace statement*;
       <end-if>  = <'" (:end-if tokens)"'> wspace;
      <numvar> = wspace (number | variable | zero | false | true);
       zero = <'" (:zero tokens)"'>;           
       init-val = <'" (:init-val tokens)"'>  (numvar|bool) wspace;
       bool = wspace (true|false);
       true = '"(:one tokens)"';
       false ='"(:zero tokens)"';
       number = #'-?[0-9]+';
       variable = #'[a-zA-Z][a-zA-Z0-9]*';
       quotedstring = #'\".*\"';
       logical-op = wspace (gt | or | and | equalto );
       arithmetic-op = wspace ( plus | minus | mult | div | modulo) ;
       modulo = <'"(:modulo tokens) "'>   numvar;
       plus = <'"(:plus tokens) "'>   numvar;
       minus = <'"(:minus tokens) "'>  numvar;
       mult = <'"(:mult tokens) "'>  numvar;
       div = <'"(:div tokens )"'>   numvar;
       gt = <'"(:gt tokens )"'>   numvar;
       or = <'"(:or tokens )"'>   numvar;
       and = <'"(:and tokens )"'>   numvar;
       equalto = <'"(:equalto tokens )"'>  numvar;
       <end-main> = <'" (:end-main tokens)"'> ;
       method-declaration = <'"(:method-declaration tokens) "'> numvar wspace 
                                                                     method-arg* 
                                                                    ( non-void-method* | statement* wspace end-method) wspace;
           
       non-void-method = <'"(:non-void-method tokens)"'> wspace (method-statement|method-return)* end-method wspace;
       method-return = <'"(:return tokens) "'> (numvar | quotedstring)+ wspace  ;
       return = <'"(:return tokens) "'> (numvar | quotedstring)? wspace  ;
       method-arg = <'"(:method-arg tokens)"'> wspace variable wspace;
       <end-method> = <'"(:end-method tokens) "'>;"))


(def arnoldc
  (insta/parser (arnold-grammar tokens))) 

;http://stackoverflow.com/questions/26338945/how-to-test-for-texts-not-fitting-an-instaparse-grammar-clojure
; pretty-print a failure as a string
(defn- failure->string [result]
  (with-out-str (fail/pprint-failure result)))

; create an Exception with the pretty-printed failure message
(defn- failure->exn [result]
  (Exception. (failure->string result)))  

(defn parser [lexr expr]
  (let [result (lexr expr)]
    (if (insta/failure? result)
      (throw (failure->exn result))
      result)))

(defn lights-camera-action [& expr]
  (try (->> (parser arnoldc (clojure.string/join expr))
            (insta/transform transform-ops))
       (catch Exception e 
         (throw (Exception.  (str "WHAT THE FUCK DID I DO WRONG? \n" (.getMessage e)))))))


