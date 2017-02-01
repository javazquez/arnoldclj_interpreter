(ns arnoldclj_s.interpret-branch-statement-test
(:use midje.sweet)
(:use arnoldclj_s.interpreter))

(fact "function using a simple if statement"
  (with-out-str (roll-credits
             "IT'S SHOWTIME
        HEY CHRISTMAS TREE vartrue 
        YOU SET US UP @NO PROBLEMO 
        BECAUSE I'M GOING TO SAY PLEASE vartrue
        TALK TO THE HAND \"this branch should be reached\"
        YOU HAVE NO RESPECT FOR LOGIC 
        YOU HAVE BEEN TERMINATED" )) => "this branch should be reached\n" 
)

(fact "function using simple if statement v2"
  (with-out-str (roll-credits "IT'S SHOWTIME\n" 
                              "HEY CHRISTMAS TREE vartrue\n" 
                              "YOU SET US UP @I LIED\n" 
                              "BECAUSE I'M GOING TO SAY PLEASE vartrue\n" 
                              "TALK TO THE HAND \"this branch should not be reached\"\n" 
                              "YOU HAVE NO RESPECT FOR LOGIC\n" 
                              "YOU HAVE BEEN TERMINATED\n")) => ""

)

(fact "function using simple if else statements"
  (with-out-str (roll-credits  "IT'S SHOWTIME\n" 
                               "HEY CHRISTMAS TREE vartrue\n" 
                               "YOU SET US UP @NO PROBLEMO\n" 
                               "BECAUSE I'M GOING TO SAY PLEASE vartrue\n" 
                               "TALK TO THE HAND \"this branch should be reached\"\n" 
                               "BULLSHIT\n" 
                               "TALK TO THE HAND \"this branch should not be reached\"\n" 
                               "YOU HAVE NO RESPECT FOR LOGIC\n" 
                               "YOU HAVE BEEN TERMINATED\n")) => "this branch should be reached\n")

(fact "function using simple if else statements vol2"
  (with-out-str (roll-credits "IT'S SHOWTIME\n" 
                              "HEY CHRISTMAS TREE varfalse\n" 
                              "YOU SET US UP @I LIED\n" 
                              "BECAUSE I'M GOING TO SAY PLEASE varfalse\n" 
                              "TALK TO THE HAND \"this branch should not be reached\"\n" 
                              "BULLSHIT\n" 
                              "TALK TO THE HAND \"this branch should be reached\"\n" 
                              "YOU HAVE NO RESPECT FOR LOGIC\n" 
                              "YOU HAVE BEEN TERMINATED\n")) => "this branch should be reached\n")

(fact "function using assigning variables in if statements"
  (with-out-str (roll-credits "IT'S SHOWTIME\n" 
                              "HEY CHRISTMAS TREE var\n" 
                              "YOU SET US UP 0\n" 
                              "HEY CHRISTMAS TREE vartrue\n" 
                              "YOU SET US UP @NO PROBLEMO\n" 
                              "BECAUSE I'M GOING TO SAY PLEASE vartrue\n" 
                              "GET TO THE CHOPPER var\n" 
                              "HERE IS MY INVITATION 3\n" 
                              "ENOUGH TALK\n" 
                              "YOU HAVE NO RESPECT FOR LOGIC\n" 
                              "TALK TO THE HAND var\n" 
                              "YOU HAVE BEEN TERMINATED\n")) => "3\n"
 )

(fact "function using stub while statement"
  (with-out-str (roll-credits "IT'S SHOWTIME\n" 
                              "HEY CHRISTMAS TREE varfalse\n" 
                              "YOU SET US UP @I LIED\n" ;revisit this
                              "STICK AROUND varfalse\n" 
                              "CHILL\n" 
                              "YOU HAVE BEEN TERMINATED\n"))=> "")

(fact "function using stub while statement vol2"
  (with-out-str (roll-credits   "IT'S SHOWTIME\n" 
                                "STICK AROUND @I LIED\n" ;revisit this!
                                "CHILL\n" 
                                "YOU HAVE BEEN TERMINATED\n")) => "") 

(fact "function when while loop executed once"
  (with-out-str (roll-credits "IT'S SHOWTIME\n" 
                              "HEY CHRISTMAS TREE varfalse\n" 
                              "YOU SET US UP @NO PROBLEMO\n" 
                              "STICK AROUND varfalse\n" 
                              "GET TO THE CHOPPER varfalse\n" 
                              "HERE IS MY INVITATION @I LIED\n" 
                              "ENOUGH TALK\n" 
                              "TALK TO THE HAND \"while statement printed once\"\n" 
                              "CHILL\n" 
                              "YOU HAVE BEEN TERMINATED\n")) => "while statement printed once\n" )

(fact "function when while loop executed consequently"
  (with-out-str (roll-credits  "IT'S SHOWTIME\n" 
                               "HEY CHRISTMAS TREE isLessThan10\n" 
                               "YOU SET US UP @NO PROBLEMO\n" 
                               "HEY CHRISTMAS TREE n\n" 
                               "YOU SET US UP 0\n" 
                               "STICK AROUND isLessThan10\n" 
                               "GET TO THE CHOPPER n\n" 
                               "HERE IS MY INVITATION n\n" 
                               "GET UP 1\n" 
                               "ENOUGH TALK\n" 
                               "TALK TO THE HAND n\n" 
                               "GET TO THE CHOPPER isLessThan10\n" 
                               "HERE IS MY INVITATION 10\n" 
                               "LET OFF SOME STEAM BENNET n\n" 
                               "ENOUGH TALK\n" 
                               "CHILL\n" 
                               "YOU HAVE BEEN TERMINATED\n")) => "1\n2\n3\n4\n5\n6\n7\n8\n9\n10\n" )


