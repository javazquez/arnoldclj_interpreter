(ns arnoldclj_s.feature-test
 (:use midje.sweet)
 (:use arnoldclj_s.lexr))


(fact "print fibonacci numbers from 1 to 10"
   (lights-camera-action "IT'S SHOWTIME\n" 
                         "HEY CHRISTMAS TREE prev\n" 
                         "YOU SET US UP -1\n" 
                         "HEY CHRISTMAS TREE result\n" 
                         "YOU SET US UP 1\n" 
                         "HEY CHRISTMAS TREE sum\n" 
                         "YOU SET US UP 0\n" 
                         "HEY CHRISTMAS TREE loop\n" 
                         "YOU SET US UP @NO PROBLEMO\n" 
                         "HEY CHRISTMAS TREE index\n" 
                         "YOU SET US UP 0\n" 
                         "HEY CHRISTMAS TREE limit\n" 
                         "YOU SET US UP 10\n" 
                         "STICK AROUND loop\n" 
                         "GET TO THE CHOPPER sum\n" 
                         "HERE IS MY INVITATION result\n" 
                         "\tGET UP prev\n" 
                         "\tENOUGH TALK\n" 
                         "\n\tGET TO THE CHOPPER prev\n" 
                         "\tHERE IS MY INVITATION result\n" 
                         "\tENOUGH TALK\n\t" 
                         "\n\tGET TO THE CHOPPER result\n" 
                         "\tHERE IS MY INVITATION sum\n" 
                         "\tENOUGH TALK\n\t" 
                         "\n\tGET TO THE CHOPPER index\n" 
                         "\tHERE IS MY INVITATION index\n" 
                         "\tGET UP 1\n" 
                         "\tENOUGH TALK\n\t" 
                         "\n\tGET TO THE CHOPPER loop\n" 
                         "\tHERE IS MY INVITATION limit\n" 
                         "\tLET OFF SOME STEAM BENNET index\n" 
                         "\tENOUGH TALK\n\t" 
                         "\n\tTALK TO THE HAND sum\n" 
                         "CHILL\n" 
                         "\nYOU HAVE BEEN TERMINATED") => 
[:Program 
 [:begin-main
  [:statement 
   [:decvar [:variable  "prev"] [:init-val [:number "-1"]]]]
  [:statement [:decvar [:variable "result"] [:init-val [:number "1"]]]]
  [:statement [:decvar [:variable "sum"] [:init-val [:number "0"]]]] 
  [:statement [:decvar [:variable "loop"] [:init-val [:bool [:true 1]]]]] 
  [:statement [:decvar [:variable "index"] [:init-val [:number "0"]]]] 
  [:statement [:decvar [:variable "limit"] [:init-val [:number "10"]]]] 
  [:statement 
   [:while [:variable "loop"]
    [:statement [:assignment [:variable "sum"]
                 [:set-val [:variable "result"] 
                  [:arithmetic-op [:plus [:variable "prev"]]]]]]
    [:statement [:assignment [:variable "prev"]
                 [:set-val [:variable "result"]]]]
    [:statement [:assignment [:variable "result"]
                 [:set-val [:variable "sum"]]]] 
    [:statement [:assignment [:variable "index"]
                            [:set-val [:variable "index"]
                             [:arithmetic-op [:plus [:number "1"]]]]]]
               [:statement
                [:assignment [:variable "loop"]
                 [:set-val [:variable "limit"]
                  [:logical-op [:gt [:variable "index"]]]]]]
    [:statement [:printing [:variable "sum"]]]]]]]
)

;works for 0 and 1 
(fact "print fibonacci when using recursion"
  (lights-camera-action "IT'S SHOWTIME\n" 
                        "HEY CHRISTMAS TREE result\n" 
                        "YOU SET US UP 0\n" 
                        "GET YOUR ASS TO MARS result\n" 
                        "DO IT NOW fib 2\n" 
                        "TALK TO THE HAND result\n" 
                        "YOU HAVE BEEN TERMINATED\n" 
                        "\nLISTEN TO ME VERY CAREFULLY fib\n" 
                        "I NEED YOUR CLOTHES YOUR BOOTS AND YOUR MOTORCYCLE val\n" 
                        "GIVE THESE PEOPLE AIR\n" 
                        "\tHEY CHRISTMAS TREE endrecursion\n" 
                        "\tYOU SET US UP @I LIED\n" 
                        "\tGET TO THE CHOPPER endrecursion\n" 
                        "\tHERE IS MY INVITATION 2\n" 
                        "\tLET OFF SOME STEAM BENNET val\n" 
                        "\tENOUGH TALK\n\n" 
                        "\tBECAUSE I'M GOING TO SAY PLEASE endrecursion\n" 
                        "\t\tI'LL BE BACK val\t\n" 
                        "\tBULLSHIT\n" 
                        "\t\tHEY CHRISTMAS TREE temp1\n" 
                        "\t\tYOU SET US UP 0\n" 
                        "\t\tHEY CHRISTMAS TREE temp2\n" 
                        "\t\tYOU SET US UP 0\n\n" 
                        "\t\tGET TO THE CHOPPER val\n" 
                        "\t\tHERE IS MY INVITATION val\n" 
                        "\t\tGET DOWN 1\n" 
                        "\t\tENOUGH TALK\n" 
                        "\t\tGET YOUR ASS TO MARS temp1\n" 
                        "\t\tDO IT NOW fib val\n" 
                        "\t\tGET TO THE CHOPPER val\n" 
                        "\t\tHERE IS MY INVITATION val\n" 
                        "\t\tGET DOWN 1\n" 
                        "\t\tENOUGH TALK\n" 
                        "\t\tGET YOUR ASS TO MARS temp2\n" 
                        "\t\tDO IT NOW fib val\n" 
                        "\t\tGET TO THE CHOPPER val\n" 
                        "\t\tHERE IS MY INVITATION temp1\n" 
                        "\t\tGET UP temp2\n" 
                        "\t\tENOUGH TALK\n" 
                        "\t\tI'LL BE BACK val\n" 
                        "\t\tYOU HAVE NO RESPECT FOR LOGIC\n\n" 
                        "\nHASTA LA VISTA, BABY") =>
 [:Program 
  [:begin-main 
   [:statement
    [:decvar [:variable "result"] 
     [:init-val [:number "0"]]]] 
   [:statement 
    [:assign-var-from-method-call
     [:variable "result"]
     [:call-method 
      [:method-name "fib"] 
      [:number "2"]]]]
   [:statement 
    [:printing 
     [:variable "result"]]]]
  [:method-declaration
   [:variable "fib"] 
   [:method-arg [:variable "val"]] 
   [:non-void-method 
    [:method-statement 
     [:decvar [:variable  "endrecursion"] [:init-val [:bool [:false 0]]]]]
    [:method-statement 
     [:assignment
      [:variable "endrecursion"] 
      [:set-val 
       [:number "2"]
       [:logical-op [:gt [:variable "val"]]]]]] 
    [:method-statement 
     [:if
      [:variable "endrecursion"] 
      [:statement 
       [:return [:variable "val"]]]
      [:else-if
       [:statement 
        [:decvar [:variable  "temp1"] 
         [:init-val [:number "0"]]]]
       [:statement
        [:decvar [:variable  "temp2"] 
         [:init-val [:number "0"]]]] 
       [:statement 
        [:assignment 
         [:variable "val"]
         [:set-val [:variable "val"] 
          [:arithmetic-op 
           [:minus 
            [:number "1"]]]]]]
       [:statement
        [:assign-var-from-method-call
         [:variable "temp1"] 
         [:call-method 
          [:method-name "fib"] 
          [:variable "val"]]]]
       [:statement 
        [:assignment 
         [:variable "val"] 
         [:set-val [:variable "val"]
          [:arithmetic-op [:minus [:number "1"]]]]]]
       [:statement 
       [:assign-var-from-method-call 
        [:variable "temp2"]
        [:call-method [:method-name "fib"];<---problem is that walker changes this 
         [:variable "val"]]]]
       [:statement
        [:assignment [:variable "val"]
         [:set-val
          [:variable "temp1"]
          [:arithmetic-op [:plus [:variable "temp2"]]]]]]
       [:statement
        [:return [:variable "val"]]]]]]]]])


(fact "printf modulos when a modulo function is defined"
  (lights-camera-action "IT'S SHOWTIME\n" 
                        "HEY CHRISTMAS TREE result1\n" 
                        "YOU SET US UP 0\n" 
                        "HEY CHRISTMAS TREE result2\n" 
                        "YOU SET US UP 0\n" 
                        "HEY CHRISTMAS TREE result3\n" 
                        "YOU SET US UP 0\n" 
                        "HEY CHRISTMAS TREE result4\n" 
                        "YOU SET US UP 0\n" 
                        "GET YOUR ASS TO MARS result1\n" 
                        "DO IT NOW modulo 9 4\n" 
                        "TALK TO THE HAND result1\n" 
                        "GET YOUR ASS TO MARS result2\n" 
                        "DO IT NOW modulo 4795 87\n" 
                        "TALK TO THE HAND result2\n" 
                        "GET YOUR ASS TO MARS result3\n" 
                        "DO IT NOW modulo 3978 221\n" 
                        "TALK TO THE HAND result3\n" 
                        "GET YOUR ASS TO MARS result4\n" 
                        "DO IT NOW modulo 5559 345\n" 
                        "TALK TO THE HAND result4\n" 
                        "YOU HAVE BEEN TERMINATED\n" 
                        "LISTEN TO ME VERY CAREFULLY modulo\n" 
                        "I NEED YOUR CLOTHES YOUR BOOTS AND YOUR MOTORCYCLE dividend\n" 
                        "I NEED YOUR CLOTHES YOUR BOOTS AND YOUR MOTORCYCLE divisor\n" 
                        "GIVE THESE PEOPLE AIR\n" 
                        "HEY CHRISTMAS TREE quotient\n" 
                        "YOU SET US UP 0\n" 
                        "HEY CHRISTMAS TREE remainder\n" 
                        "YOU SET US UP 0\n" 
                        "HEY CHRISTMAS TREE product\n" 
                        "YOU SET US UP 0\n" 
                        "GET TO THE CHOPPER quotient\n" 
                        "HERE IS MY INVITATION dividend\n" 
                        "HE HAD TO SPLIT divisor\n" 
                        "ENOUGH TALK\n" 
                        "GET TO THE CHOPPER product\n" 
                        "HERE IS MY INVITATION divisor\n" 
                        "YOU'RE FIRED quotient\n" 
                        "ENOUGH TALK\n" 
                        "GET TO THE CHOPPER remainder\n" 
                        "HERE IS MY INVITATION dividend\n" 
                        "GET DOWN product\n" 
                        "ENOUGH TALK\n" 
                        "I'LL BE BACK remainder\n" 
                        "HASTA LA VISTA, BABY ") => 
[:Program
 [:begin-main 
  [:statement
   [:decvar
    [:variable "result1"]
    [:init-val
     [:number "0"]]]]
  [:statement 
   [:decvar 
    [:variable "result2"]
    [:init-val 
     [:number "0"]]]] 
  [:statement
   [:decvar 
    [:variable "result3"]
    [:init-val 
     [:number "0"]]]]
  [:statement 
   [:decvar
    [:variable "result4"]
    [:init-val 
     [:number "0"]]]] 
  [:statement
   [:assign-var-from-method-call
    [:variable "result1"] 
    [:call-method 
     [:method-name "modulo"]
     [:number "9"] 
     [:number "4"]]]]
  [:statement 
   [:printing
    [:variable "result1"]]] 
  [:statement 
   [:assign-var-from-method-call 
    [:variable "result2"]
               [:call-method 
                [:method-name "modulo"]
                [:number "4795"] 
                [:number "87"]]]]
  [:statement
   [:printing 
    [:variable "result2"]]]
  [:statement 
   [:assign-var-from-method-call 
    [:variable "result3"]
    [:call-method
     [:method-name "modulo"]
     [:number "3978"] 
     [:number "221"]]]]
  [:statement 
   [:printing 
    [:variable "result3"]]]
  [:statement
   [:assign-var-from-method-call
    [:variable "result4"] 
    [:call-method 
     [:method-name "modulo"]
     [:number "5559"] 
     [:number "345"]]]]
  [:statement
   [:printing
    [:variable "result4"]]]] 
 [:method-declaration 
  [:variable "modulo"]
  [:method-arg 
   [:variable "dividend"]] 
  [:method-arg
   [:variable "divisor"]]
  [:non-void-method
   [:method-statement 
    [:decvar
     [:variable "quotient"]
     [:init-val 
      [:number "0"]]]]
   [:method-statement
    [:decvar
     [:variable "remainder"]
     [:init-val 
      [:number "0"]]]]
   [:method-statement 
    [:decvar
     [:variable "product"]
     [:init-val 
      [:number "0"]]]]
   [:method-statement
    [:assignment 
     [:variable "quotient"] 
     [:set-val [:variable "dividend"] 
      [:arithmetic-op 
       [:div [:variable "divisor"]]]]]] 
   [:method-statement 
    [:assignment 
     [:variable "product"]
     [:set-val 
      [:variable "divisor"] 
      [:arithmetic-op 
       [:mult
        [:variable "quotient"]]]]]] 
   [:method-statement 
    [:assignment 
     [:variable "remainder"] 
     [:set-val 
      [:variable "dividend"]
      [:arithmetic-op
       [:minus 
        [:variable "product"]]]]]] 
   [:method-return 
    [:variable "remainder"]]]]]
)


(fact "print squares from 1 to 10"
  (lights-camera-action "IT'S SHOWTIME\n" 
                        "HEY CHRISTMAS TREE limit\n" 
                        "YOU SET US UP 10\n" 
                        "HEY CHRISTMAS TREE index\n" 
                        "YOU SET US UP 1\n" 
                        "HEY CHRISTMAS TREE squared\n" 
                        "YOU SET US UP 1\n" 
                        "HEY CHRISTMAS TREE loop\n" 
                        "YOU SET US UP @NO PROBLEMO \n\n" 
                        "STICK AROUND loop\n\n" 
                        "\tGET TO THE CHOPPER squared\n" 
                        "\tHERE IS MY INVITATION index\n" 
                        "\tYOU'RE FIRED index\n" 
                        "\tENOUGH TALK\n" 
                        "\tTALK TO THE HAND squared\n\t\n" 
                        "\tGET TO THE CHOPPER loop\n" 
                        "\tHERE IS MY INVITATION limit\n" 
                        "\tLET OFF SOME STEAM BENNET index\n" 
                        "\tENOUGH TALK\n\t\n" 
                        "\tGET TO THE CHOPPER index\n" 
                        "\tHERE IS MY INVITATION index\n" 
                        "\tGET UP 1\n" 
                        "\tENOUGH TALK\n\t\n" 
                        "CHILL\n" 
                        "YOU HAVE BEEN TERMINATED")=> 
[:Program
 [:begin-main 
  [:statement 
   [:decvar 
    [:variable "limit"]
    [:init-val 
     [:number "10"]]]]
  [:statement 
   [:decvar
    [:variable "index"]
    [:init-val 
     [:number "1"]]]]
  [:statement 
   [:decvar
    [:variable "squared"]
    [:init-val
     [:number "1"]]]]
  [:statement 
   [:decvar 
    [:variable "loop"]
    [:init-val 
     [:bool 
      [:true 1]]]]]
  [:statement 
   [:while 
    [:variable "loop"]
    [:statement 
     [:assignment 
      [:variable "squared"] 
      [:set-val 
       [:variable "index"] 
       [:arithmetic-op 
        [:mult 
         [:variable "index"]]]]]]
    [:statement
     [:printing
      [:variable "squared"]]] 
    [:statement
     [:assignment
      [:variable "loop"]
      [:set-val [:variable "limit"]
       [:logical-op
        [:gt
         [:variable "index"]]]]]]
    [:statement
     [:assignment
      [:variable "index"] 
      [:set-val
       [:variable "index"] 
       [:arithmetic-op 
        [:plus
         [:number "1"]]]]]]]]]])
