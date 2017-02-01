(ns arnoldclj_s.interpret-method-test
  (:use midje.sweet)
  (:use arnoldclj_s.interpreter))

(fact "evalute method other than main" 
  (with-out-str (roll-credits "LISTEN TO ME VERY CAREFULLY mymethod\n" 
                              "HASTA LA VISTA, BABY\n" 
                              "IT'S SHOWTIME\n" 
                              "TALK TO THE HAND \"Hello\"\n" 
                              "YOU HAVE BEEN TERMINATED\n"))=> "Hello\n")

(fact "evalute method other than main2" 
  (with-out-str  (roll-credits "LISTEN TO ME VERY CAREFULLY mymethod\n" 
                               "HASTA LA VISTA, BABY\n" 
                               "IT'S SHOWTIME\n" 
                               "TALK TO THE HAND \"Hello\"\n" 
                               "YOU HAVE BEEN TERMINATED")) => "Hello\n" )

(fact "evalute method other than main3" 
  (with-out-str (roll-credits "IT'S SHOWTIME\n" 
                              "TALK TO THE HAND \"Hello\"\n" 
                              "YOU HAVE BEEN TERMINATED\n" 
                              "LISTEN TO ME VERY CAREFULLY mymethod\n" 
                              "HASTA LA VISTA, BABY\n"))=> "Hello\n")

(fact "evalute method other than main4" 
  (with-out-str (roll-credits "IT'S SHOWTIME\n" 
                              "TALK TO THE HAND \"Hello\"\n" 
                              "YOU HAVE BEEN TERMINATED\n" 
                              "LISTEN TO ME VERY CAREFULLY mymethod\n" 
                              "HASTA LA VISTA, BABY")) => "Hello\n")

 
(fact "evalute a plain method call" 
  (with-out-str (roll-credits  "IT'S SHOWTIME\n" 
                               "DO IT NOW printHello\n" 
                               "YOU HAVE BEEN TERMINATED\n" 
                               "LISTEN TO ME VERY CAREFULLY printHello\n" 
                               "TALK TO THE HAND \"Hello\"\n" 
                               "HASTA LA VISTA, BABY")) => "Hello\n") 

(fact "evalute a method call that takes an argument" 
  (with-out-str (roll-credits  "IT'S SHOWTIME\n" 
                               "HEY CHRISTMAS TREE argument\n" 
                               "YOU SET US UP 123\n" 
                               "DO IT NOW printInteger argument\n" 
                               "YOU HAVE BEEN TERMINATED\n" 
                               "LISTEN TO ME VERY CAREFULLY printInteger\n" 
                               "I NEED YOUR CLOTHES YOUR BOOTS AND YOUR MOTORCYCLE value\n" 
                               "TALK TO THE HAND value\n" 
                               "HASTA LA VISTA, BABY")) => "123\n")
 
(fact "evalute multiple method calls" 
  (with-out-str (roll-credits "IT'S SHOWTIME\n" 
                              "DO IT NOW printHello\n" 
                              "DO IT NOW printCheers\n" 
                              "YOU HAVE BEEN TERMINATED\n" 
                              "LISTEN TO ME VERY CAREFULLY printHello\n" 
                              "TALK TO THE HAND \"Hello\"\n" 
                              "HASTA LA VISTA, BABY\n" 
                              "LISTEN TO ME VERY CAREFULLY printCheers\n" 
                              "TALK TO THE HAND \"Cheers\"\n" 
                              "HASTA LA VISTA, BABY")) => "Hello\nCheers\n") 


(fact "evalute method calls inside method calls" 
  (with-out-str (roll-credits "IT'S SHOWTIME\n" 
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
                              "HASTA LA VISTA, BABY")) => "Hello\nCheers\nHejsan\n")

(fact "evalute a return statement in void calls" 
  (with-out-str (roll-credits  "IT'S SHOWTIME\n" 
                               "DO IT NOW method\n" 
                               "YOU HAVE BEEN TERMINATED\n" 
                               "LISTEN TO ME VERY CAREFULLY method\n" 
                               "I'LL BE BACK\n" 
                               "HASTA LA VISTA, BABY\n")) => "")

 
(fact "evalute multiple return statemenents in void calls" 
  (with-out-str (roll-credits "IT'S SHOWTIME\n" 
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
                              "HASTA LA VISTA, BABY\n")) => "true\nfalse\n")

(fact "evalute multiple return statemenents in void calls permutation2" 
  (with-out-str (roll-credits "IT'S SHOWTIME\n" 
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
                              "HASTA LA VISTA, BABY\n")) => "true\nfalse\n")

(fact "evalute multiple return statemenents in void calls permutation3" 
  (with-out-str (roll-credits "IT'S SHOWTIME\n" 
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
                              "HASTA LA VISTA, BABY\n")) => "true\nfalse\n")
 
(fact "evalute multiple return statemenents in void calls with unreachable code" 
  (with-out-str (roll-credits "IT'S SHOWTIME\n" 
                              "DO IT NOW method\n" 
                              "YOU HAVE BEEN TERMINATED\n" 
                              "LISTEN TO ME VERY CAREFULLY method\n" 
                              "TALK TO THE HAND \"reached codeblock\"\n" 
                              "I'LL BE BACK\n" 
                              "TALK TO THE HAND \"unreached codeblock\"\n" 
                              "HASTA LA VISTA, BABY\n")) => "reached codeblock\n" )
 
(fact "evalute void method calls returning from branched statements" 
  (with-out-str (roll-credits "IT'S SHOWTIME\n" 
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
                              "HASTA LA VISTA, BABY\n")) => "evaluated\n")
   
(fact "evalute non void method calls returning from branched statements" 
  (with-out-str (roll-credits "IT'S SHOWTIME\n" 
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
                              "HASTA LA VISTA, BABY\n")) => "evaluated\n")
 
(fact "evalute assignments to variables from method calls " 
  (with-out-str (roll-credits "IT'S SHOWTIME\n" 
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
                              "HASTA LA VISTA, BABY\n")) => "49\n" )
