(ns arnoldclj_s.method-test
  (:use midje.sweet)
  (:use arnoldclj_s.lexr))

(fact "evalute method other than main" 
  (lights-camera-action "LISTEN TO ME VERY CAREFULLY mymethod\n" 
                        "HASTA LA VISTA, BABY\n" 
                        "IT'S SHOWTIME\n" 
                        "TALK TO THE HAND \"Hello\"\n" 
                        "YOU HAVE BEEN TERMINATED\n")=> [:Program 
                                                         [:method-declaration [:variable "mymethod"] ]
                                                         [:begin-main 
                                                          [:statement [:printing [:quotedstring "Hello"]]]]])
    ;getOutput(code) should equal("Hello\n")
        

(fact "evalute method other than main2" 
  (lights-camera-action "LISTEN TO ME VERY CAREFULLY mymethod\n" 
                        "HASTA LA VISTA, BABY\n" 
                        "IT'S SHOWTIME\n" 
                        "TALK TO THE HAND \"Hello\"\n" 
                        "YOU HAVE BEEN TERMINATED") => [:Program 
                                                        [:method-declaration [:variable "mymethod"] ]
                                                        [:begin-main
                                                         [:statement [:printing [:quotedstring "Hello"]]]]]) 
    ;getOutput(code) should equal("Hello\n")
  

(fact "evalute method other than main3" 
  (lights-camera-action "IT'S SHOWTIME\n" 
                        "TALK TO THE HAND \"Hello\"\n" 
                        "YOU HAVE BEEN TERMINATED\n" 
                        "LISTEN TO ME VERY CAREFULLY mymethod\n" 
                        "HASTA LA VISTA, BABY\n")=> [:Program 
                                                     [:begin-main
                                                      [:statement [:printing [:quotedstring "Hello"]]]] 
                                                     [:method-declaration [:variable "mymethod"] ]])
    ;getOutput(code) should equal("Hello\n")
  
;FIXME check that method name is in symbol table
(fact"evalute method other than main4" 
  (lights-camera-action "IT'S SHOWTIME\n" 
                        "TALK TO THE HAND \"Hello\"\n" 
                        "YOU HAVE BEEN TERMINATED\n" 
                        "LISTEN TO ME VERY CAREFULLY mymethod\n" 
                        "HASTA LA VISTA, BABY") => [:Program 
                                                   [:begin-main
                                                    [:statement [:printing [:quotedstring "Hello"]]]] 
                                                   [:method-declaration [:variable "mymethod"] ]])
    ;getOutput(code) should equal("Hello\n")
  

(fact "evalute a plain method call" 
  (lights-camera-action  "IT'S SHOWTIME\n" 
                         "DO IT NOW printHello\n" 
                         "YOU HAVE BEEN TERMINATED\n" 
                         "LISTEN TO ME VERY CAREFULLY printHello\n" 
                         "TALK TO THE HAND \"Hello\"\n" 
                         "HASTA LA VISTA, BABY") =>  [:Program
                                                      [:begin-main
                                                       [:statement [:call-method [:method-name "printHello"]]]] 
                                                      [:method-declaration 
                                                       [:variable "printHello"] 
                                                       [:statement [:printing [:quotedstring "Hello"]]] ]])
    ;getOutput(code) should equal("Hello\n")
  

(fact "evalute a method call that takes an argument" 
  (lights-camera-action  "IT'S SHOWTIME\n" 
                         "HEY CHRISTMAS TREE argument\n" 
                         "YOU SET US UP 123\n" 
                         "DO IT NOW printInteger argument\n" 
                         "YOU HAVE BEEN TERMINATED\n" 
                         "LISTEN TO ME VERY CAREFULLY printInteger\n" 
                         "I NEED YOUR CLOTHES YOUR BOOTS AND YOUR MOTORCYCLE value\n" 
                         "TALK TO THE HAND value\n" 
                         "HASTA LA VISTA, BABY") => 
[:Program 
 [:begin-main
  [:statement [:decvar [:variable "argument"] [:init-val [:number "123"]]]] 
  [:statement 
   [:call-method 
    [:method-name "printInteger"] 
    [:variable "argument"]]]]
 [:method-declaration [:variable "printInteger"]
  [:method-arg [:variable "value"]]
  [:statement [:printing [:variable "value"]]] ]])
    ;getOutput(code) should equal("123\n")
  

(fact "evalute multiple method calls" 
  (lights-camera-action "IT'S SHOWTIME\n" 
                        "DO IT NOW printHello\n" 
                        "DO IT NOW printCheers\n" 
                        "YOU HAVE BEEN TERMINATED\n" 
                        "LISTEN TO ME VERY CAREFULLY printHello\n" 
                        "TALK TO THE HAND \"Hello\"\n" 
                        "HASTA LA VISTA, BABY\n" 
                        "LISTEN TO ME VERY CAREFULLY printCheers\n" 
                        "TALK TO THE HAND \"Cheers\"\n" 
                        "HASTA LA VISTA, BABY") => [:Program 
                                                   [:begin-main
                                                    [:statement [:call-method [:method-name "printHello"]]]
                                                    [:statement [:call-method [:method-name "printCheers"]]]] 
                                                   [:method-declaration [:variable "printHello"]
                                                    [:statement [:printing [:quotedstring "Hello"]]] ]
                                                   [:method-declaration [:variable "printCheers"]
                                                    [:statement [:printing [:quotedstring "Cheers"]]] ]])
    ;getOutput(code) should equal("Hello\nCheers\n")
  

(fact "evalute method calls inside method calls" 
  (lights-camera-action "IT'S SHOWTIME\n" 
                        "DO IT NOW printHello\n" 
                        "YOU HAVE BEEN TERMINATED\n" 
                        "LISTEN TO ME VERY CAREFULLY printHello\n" 
                        "TALK TO THE HAND \"Hello\"\n" 
                        "DO IT NOW printCheers\n" 
                        "DO IT NOW printHejsan\n" 
                        "HASTA LA VISTA, BABY\n" 
                        "LISTEN TO ME VERY CAREFULLY printCheers\n" 
                        "TALK TO THE HAND \"Cheers\"\n" 
                        "HASTA LA VISTA, BABY\n" 
                        "LISTEN TO ME VERY CAREFULLY printHejsan\n" 
                        "TALK TO THE HAND \"Hejsan\"\n" 
                        "HASTA LA VISTA, BABY") => [:Program 
                                                    [:begin-main [:statement [:call-method [:method-name "printHello"]]]]
                                                    [:method-declaration [:variable "printHello"]
                                                     [:statement [:printing [:quotedstring "Hello"]]]
                                                     [:statement [:call-method [:method-name "printCheers"]]] 
                                                     [:statement [:call-method [:method-name "printHejsan"]]] ]
                                                    [:method-declaration [:variable "printCheers"]
                                                     [:statement [:printing [:quotedstring "Cheers"]]] ]
                                                    [:method-declaration [:variable "printHejsan"]
                                                     [:statement [:printing [:quotedstring "Hejsan"]]] ]])
    ;getOutput(code) should equal("Hello\nCheers\nHejsan\n")
  

(fact"evalute a return statement in void calls" 
  (lights-camera-action  "IT'S SHOWTIME\n" 
                         "DO IT NOW method\n" 
                         "YOU HAVE BEEN TERMINATED\n" 
                         "LISTEN TO ME VERY CAREFULLY method\n" 
                         "I'LL BE BACK\n" 
                         "HASTA LA VISTA, BABY\n") =>
 [:Program
  [:begin-main 
   [:statement [:call-method [:method-name "method"]]]] 
  [:method-declaration [:variable "method"] 
   [:statement [:return]] ]])

    ;getOutput(code) should equal("")
  

(fact "evalute multiple return statemenents in void calls" 
  (lights-camera-action "IT'S SHOWTIME\n" 
                        "DO IT NOW printboolean @NO PROBLEMO\n" 
                        "DO IT NOW printboolean @I LIED\n" 
                        "YOU HAVE BEEN TERMINATED\n" 
                        "LISTEN TO ME VERY CAREFULLY printboolean\n" 
                        "I NEED YOUR CLOTHES YOUR BOOTS AND YOUR MOTORCYCLE value\n" 
                        "BECAUSE I'M GOING TO SAY PLEASE value\n" 
                        "TALK TO THE HAND \"true\"\n" 
                        "I'LL BE BACK\n" 
                        "BULLSHIT\n" 
                        "TALK TO THE HAND \"false\"\n" 
                        "I'LL BE BACK\n" 
                        "YOU HAVE NO RESPECT FOR LOGIC\n" 
                        "HASTA LA VISTA, BABY\n") =>
 [:Program 
  [:begin-main 
   [:statement [:call-method [:method-name "printboolean"] [:true 1]]] 
   [:statement [:call-method [:method-name "printboolean"] [:false 0]]]]
  [:method-declaration [:variable "printboolean"]
   [:method-arg [:variable "value"]]
   [:statement [:if [:variable "value"] 
                [:statement [:printing [:quotedstring "true"]]] 
                [:statement [:return]]
                [:else-if
                 [:statement [:printing [:quotedstring "false"]]] 
                 [:statement [:return]]] 
                ]] ]])

    ;getOutput(code) should equal("true\nfalse\n")
  

(fact "evalute multiple return statemenents in void calls permutation2" 
  (lights-camera-action "IT'S SHOWTIME\n" 
                        "DO IT NOW printboolean @NO PROBLEMO\n" 
                        "DO IT NOW printboolean @I LIED\n" 
                        "YOU HAVE BEEN TERMINATED\n" 
                        "LISTEN TO ME VERY CAREFULLY printboolean\n" 
                        "I NEED YOUR CLOTHES YOUR BOOTS AND YOUR MOTORCYCLE value\n" 
                        "BECAUSE I'M GOING TO SAY PLEASE value\n" 
                        "TALK TO THE HAND \"true\"\n" 
                        "BULLSHIT\n" 
                        "TALK TO THE HAND \"false\"\n" 
                        "YOU HAVE NO RESPECT FOR LOGIC\n" 
                        "HASTA LA VISTA, BABY\n") => 
 [:Program 
  [:begin-main 
   [:statement [:call-method [:method-name "printboolean"] [:true 1]]]
   [:statement [:call-method [:method-name "printboolean"] [:false 0]]]]
  [:method-declaration [:variable "printboolean"] 
   [:method-arg [:variable "value"]] 
   [:statement [:if [:variable "value"] 
                [:statement [:printing [:quotedstring "true"]]]
                [:else-if [:statement [:printing [:quotedstring "false"]]]] ]] ]])
    ;getOutput(code) should equal("true\nfalse\n")
  

(fact"evalute multiple return statemenents in void calls permutation3" 
  (lights-camera-action "IT'S SHOWTIME\n" 
                        "DO IT NOW printboolean @NO PROBLEMO\n" 
                        "DO IT NOW printboolean @I LIED\n" 
                        "YOU HAVE BEEN TERMINATED\n" 
                        "LISTEN TO ME VERY CAREFULLY printboolean\n" 
                        "I NEED YOUR CLOTHES YOUR BOOTS AND YOUR MOTORCYCLE value\n" 
                        "BECAUSE I'M GOING TO SAY PLEASE value\n" 
                        "TALK TO THE HAND \"true\"\n" 
                        "BULLSHIT\n" 
                        "TALK TO THE HAND \"false\"\n" 
                        "YOU HAVE NO RESPECT FOR LOGIC\n" 
                        "I'LL BE BACK\n" 
                        "I'LL BE BACK\n" 
                        "HASTA LA VISTA, BABY\n") =>
 [:Program 
  [:begin-main
   [:statement [:call-method [:method-name "printboolean"] [:true 1]]] 
   [:statement [:call-method [:method-name "printboolean"] [:false 0]]]]
  [:method-declaration [:variable "printboolean"] 
   [:method-arg [:variable "value"]]
   [:statement [:if 
                [:variable "value"]
                [:statement [:printing [:quotedstring "true"]]] 
                [:else-if 
                 [:statement [:printing [:quotedstring "false"]]]] 
                ]]
   [:statement [:return]] 
   [:statement [:return]] ]])
 ;getOutput(code) should equal("true\nfalse\n")
  


(fact "evalute multiple return statemenents in void calls with unreachable code" 
  (lights-camera-action "IT'S SHOWTIME\n" 
                        "DO IT NOW method\n" 
                        "YOU HAVE BEEN TERMINATED\n" 
                        "LISTEN TO ME VERY CAREFULLY method\n" 
                        "TALK TO THE HAND \"reached codeblock\"\n" 
                        "I'LL BE BACK\n" 
                        "TALK TO THE HAND \"unreached codeblock\"\n" 
                        "HASTA LA VISTA, BABY\n") => 
[:Program 
 [:begin-main 
  [:statement [:call-method [:method-name "method"]]]]
 [:method-declaration [:variable "method"]
  [:statement [:printing [:quotedstring "reached codeblock"]]]
  [:statement [:return]]
  [:statement [:printing [:quotedstring "unreached codeblock"]]] ]]
)

    ;getOutput(code) should equal("reached codeblock\n")
  

(fact "evalute void method calls returning from branched statements" 
  (lights-camera-action "IT'S SHOWTIME\n" 
                        "DO IT NOW reverse @NO PROBLEMO\n" 
                        "YOU HAVE BEEN TERMINATED\n" 
                        "LISTEN TO ME VERY CAREFULLY reverse\n" 
                        "I NEED YOUR CLOTHES YOUR BOOTS AND YOUR MOTORCYCLE value\n" 
                        "BECAUSE I'M GOING TO SAY PLEASE value\n" 
                        "TALK TO THE HAND \"evaluated\"\n" 
                        "I'LL BE BACK\n" 
                        "YOU HAVE NO RESPECT FOR LOGIC\n" 
                        "TALK TO THE HAND \"not evaluated\"\n"
                        "I'LL BE BACK\n" 
                        "HASTA LA VISTA, BABY\n") =>
 [:Program 
  [:begin-main 
   [:statement
    [:call-method
     [:method-name "reverse"] 
     [:true 1]]]]
  [:method-declaration 
   [:variable "reverse"] 
   [:method-arg
    [:variable "value"]] 
   [:statement 
    [:if 
     [:variable "value"]
     [:statement [:printing [:quotedstring "evaluated"]]] 
     [:statement [:return]]]]
   [:statement [:printing [:quotedstring "not evaluated"]]] 
   [:statement [:return]] ]])
    ;getOutput(code) should equal("evaluated\n")
  
 
(fact "evalute non void method calls returning from branched statements" 
  (lights-camera-action "IT'S SHOWTIME\n" 
                        "DO IT NOW reverse @NO PROBLEMO\n" 
                        "YOU HAVE BEEN TERMINATED\n" 
                        "LISTEN TO ME VERY CAREFULLY reverse\n" 
                        "I NEED YOUR CLOTHES YOUR BOOTS AND YOUR MOTORCYCLE value\n" 
                        "GIVE THESE PEOPLE AIR\n" 
                        "BECAUSE I'M GOING TO SAY PLEASE value\n" 
                        "TALK TO THE HAND \"evaluated\"\n" 
                        "I'LL BE BACK 0\n" 
                        "TALK TO THE HAND \"evaluated\"\n" 
                        "YOU HAVE NO RESPECT FOR LOGIC\n" 
                        "TALK TO THE HAND \"not evaluated\"\n"
                        "I'LL BE BACK 0\n" 
                        "HASTA LA VISTA, BABY\n") => 
[:Program 
 [:begin-main 
  [:statement [:call-method [:method-name "reverse"] [:true 1]]]]
 [:method-declaration 
  [:variable "reverse"] 
  [:method-arg [:variable "value"]] 
  [:non-void-method 
   [:method-statement
    [:if 
     [:variable "value"] 
     [:statement [:printing [:quotedstring "evaluated"]]] 
     [:statement [:return [:number "0"]]]
     [:statement [:printing [:quotedstring "evaluated"]]] ]]
   [:method-statement 
    [:printing [:quotedstring "not evaluated"]]]
   [:method-return [:number "0"]] ]
  ]])
                                       ;getOutput(code) should equal("evaluated\n")
  

(fact "evalute assignments to variables from method calls " 
  (lights-camera-action "IT'S SHOWTIME\n" 
                        "HEY CHRISTMAS TREE result\n" 
                        "YOU SET US UP 0\n" 
                        "GET YOUR ASS TO MARS result\n" 
                        "DO IT NOW square 7\n" 
                        "TALK TO THE HAND result\n" 
                        "YOU HAVE BEEN TERMINATED\n" 
                        "LISTEN TO ME VERY CAREFULLY square\n" 
                        "I NEED YOUR CLOTHES YOUR BOOTS AND YOUR MOTORCYCLE value\n" 
                        "GIVE THESE PEOPLE AIR\n" 
                        "GET TO THE CHOPPER value\n" 
                        "HERE IS MY INVITATION value\n" 
                        "YOU'RE FIRED value\n" 
                        "ENOUGH TALK\n" 
                        "I'LL BE BACK value\n" 

                        "HASTA LA VISTA, BABY\n") =>
[:Program [:begin-main [:statement [:decvar [:variable "result"] [:init-val [:number "0"]]]] [:statement [:assign-var-from-method-call [:variable "result"] [:call-method [:method-name "square"] [:number "7"]]]] [:statement [:printing [:variable "result"]]]] [:method-declaration [:variable "square"] [:method-arg [:variable "value"]] [:non-void-method [:method-statement [:assignment [:variable "value"] [:set-val [:variable "value"] [:arithmetic-op [:mult [:variable "value"]]]]]] [:method-return [:variable "value"]]]]]


)
    ;getOutput(code) should equal("49\n")
  

(fact "detect unclosed main method" 
  (lights-camera-action "IT'S SHOWTIME\n" 
                        "LISTEN TO ME VERY CAREFULLY printHello\n" 
                        "TALK TO THE HAND \"Hello\"\n") => (throws Exception  "WHAT THE FUCK DID I DO WRONG? \nParse error at line 2, column 1:\nLISTEN TO ME VERY CAREFULLY printHello\n^\nExpected one of:\n\"I'LL BE BACK\"\n\"STICK AROUND\"\n\"BECAUSE I'M GOING TO SAY PLEASE\"\n\"DO IT NOW\"\n\"GET YOUR ASS TO MARS\"\n\"HEY CHRISTMAS TREE\"\n\"TALK TO THE HAND\"\n\"GET TO THE CHOPPER\"\n"))
    ;intercept[ParsingException] {
      ;getOutput(code)
    
  
(fact "detect unclosed methods" 
  (lights-camera-action "IT'S SHOWTIME\n" 
                        "YOU HAVE BEEN TERMINATED\n" 
                        "LISTEN TO ME VERY CAREFULLY printHello\n" 
                        "TALK TO THE HAND \"Hello\"\n") => (throws Exception  "WHAT THE FUCK DID I DO WRONG? \nParse error at line 2, column 1:\nYOU HAVE BEEN TERMINATED\n^\nExpected one of:\n\"I'LL BE BACK\"\n\"STICK AROUND\"\n\"BECAUSE I'M GOING TO SAY PLEASE\"\n\"DO IT NOW\"\n\"GET YOUR ASS TO MARS\"\n\"HEY CHRISTMAS TREE\"\n\"TALK TO THE HAND\"\n\"GET TO THE CHOPPER\"\n"))
    ;intercept[ParsingException] {
      ;getOutput(code)
    
  
;fixme, this is a runtime error
(fact "detect calls to methods that are not declared" 
  (lights-camera-action  "IT'S SHOWTIME\n" 
                         "DO IT NOW noSuchMethod\n" 
                         "YOU HAVE BEEN TERMINATED\n"))
    ;intercept[ParsingException] {
      ;getOutput(code)
    
  
;fixme !!
(fact "detect if void method tries to return a parameter" 
  (lights-camera-action "IT'S SHOWTIME\n" 
                        "DO IT NOW method\n" 
                        "YOU HAVE BEEN TERMINATED\n" 
                        "LISTEN TO ME VERY CAREFULLY method\n" 
                        "I'LL BE BACK 0\n" 
                        "HASTA LA VISTA, BABY\n"))

    ;intercept[ParsingException] ;
    
      ;getOutput(code)
    
  
;fixme
(fact "detect if a non-void method tries to return a without a parameter" 
  (lights-camera-action "IT'S SHOWTIME\n" 
                        "DO IT NOW method 0\n" 
                        "YOU HAVE BEEN TERMINATED\n" 
                        "LISTEN TO ME VERY CAREFULLY method\n" 
                        "I NEED YOUR CLOTHES YOUR BOOTS AND YOUR MOTORCYCLE value\n" 
                        "GIVE THESE PEOPLE AIR\n" 
                        "I'LL BE BACK\n" 
                        "HASTA LA VISTA, BABY\n") => (throws Exception   
#_"Exception WHAT THE FUCK DID I DO WRONG? \nParse error at line 8, column 15:\n
HASTA LA VISTA, BABY\n              ^\nExpected one of:\n\"HASTA LA VISTA, BABY\"\n\"I'LL BE BACK\"\n\"STICK AROUND\"\n\"BECAUSE I'M GOING TO SAY PLEASE\"\n\"DO IT NOW\"\n\"GET YOUR ASS TO MARS\"\n\"HEY CHRISTMAS TREE\"\n\"TALK TO THE HAND\"\n\"GET TO THE CHOPPER\"\n#\"\\\".*\\\"\"\n\"@NO PROBLEMO\"\n\"@I LIED\"\n#\"[a-zA-Z][a-zA-Z0-9]*\"\n#\"-?[0-9]+\"" ))

   ; ;intercept[ParsingException]
                                        ;getOutput(code)
    

