(ns arnoldclj_s.interpret-arith-test
  (:use midje.sweet)
  (:use arnoldclj_s.interpreter)) 

(fact "function when a variable is declared"
  (with-out-str  (roll-credits  "IT'S SHOWTIME
         HEY CHRISTMAS TREE var
         YOU SET US UP 123
         YOU HAVE BEEN TERMINATED" )) => "") 

(fact "function when an integer is printed"
  (with-out-str  (roll-credits "IT'S SHOWTIME
          TALK TO THE HAND 123
          YOU HAVE BEEN TERMINATED")) => "123\n")

(fact "evaluate when a negative integer is printed"
  (with-out-str  (roll-credits  "IT'S SHOWTIME
            TALK TO THE HAND -111
            YOU HAVE BEEN TERMINATED")) => "-111\n")

(fact "evaluate when a 'boolean' is printed"
  (with-out-str (roll-credits "IT'S SHOWTIME
            HEY CHRISTMAS TREE varfalse
            YOU SET US UP @I LIED
            TALK TO THE HAND varfalse

            YOU HAVE BEEN TERMINATED")) =>  "0\n" )

(fact "evaluate when a string is printed"
  (with-out-str  (roll-credits "IT'S SHOWTIME\n
            TALK TO THE HAND \"this should be printed\"\n
            YOU HAVE BEEN TERMINATED")) => "this should be printed\n")

(fact "evaluate when a more exotic string is printed"
  (with-out-str (roll-credits "IT'S SHOWTIME\n
            TALK TO THE HAND \"!!! ??? äöäöäöä@#0123=-,.\"
            YOU HAVE BEEN TERMINATED")) => "!!! ??? äöäöäöä@#0123=-,.\n"  )

(fact "evaluate when an integer is declared and printed"
  (with-out-str (roll-credits "IT'S SHOWTIME\n
                        HEY CHRISTMAS TREE A\n  
                        YOU SET US UP 999\n 
                        HEY CHRISTMAS TREE B\n 
                        YOU SET US UP 555\n
                        TALK TO THE HAND A\n
                        TALK TO THE HAND B\n
                        YOU HAVE BEEN TERMINATED\n")) => "999\n555\n" )

(fact "evaluate when a negative integer is declared and printed"
  (with-out-str (roll-credits "IT'S SHOWTIME
                         HEY CHRISTMAS TREE a\n
                         YOU SET US UP -999\n
                         HEY CHRISTMAS TREE b\n 
                         YOU SET US UP -555\n 
                         TALK TO THE HAND a\n 
                         TALK TO THE HAND b\n 
                         YOU HAVE BEEN TERMINATED")) =>"-999\n-555\n")

(fact "evaluate when assigning a variable"
  (with-out-str (roll-credits "IT'S SHOWTIME\n 
                          HEY CHRISTMAS TREE var\n 
                          YOU SET US UP 22\n 
                          GET TO THE CHOPPER var\n 
                          HERE IS MY INVITATION 123\n
                          ENOUGH TALK\n
                          TALK TO THE HAND var\n
                          YOU HAVE BEEN TERMINATED")) => "123\n" )

(fact "evaluate when assigning multiple variables"
  (with-out-str (roll-credits "IT'S SHOWTIME\n 
                          HEY CHRISTMAS TREE var\n 
                          YOU SET US UP 22\n
                          HEY CHRISTMAS TREE var2\n
                          YOU SET US UP 27\n
                          GET TO THE CHOPPER var\n 
                          HERE IS MY INVITATION 123\n
                          ENOUGH TALK\n
                          GET TO THE CHOPPER var2\n
                          HERE IS MY INVITATION 707\n
                          ENOUGH TALK\n
                          GET TO THE CHOPPER var\n
                          HERE IS MY INVITATION var2\n
                          GET UP var\n
                          ENOUGH TALK\n 
                          TALK TO THE HAND var\n
                          YOU HAVE BEEN TERMINATED\n")) => "830\n" )

(fact "evaluate when an integer is incremented and printed"
  (with-out-str (roll-credits "IT'S SHOWTIME\n 
                          HEY CHRISTMAS TREE VAR\n
                          YOU SET US UP 22\n
                          GET TO THE CHOPPER VAR\n
                          HERE IS MY INVITATION VAR\n
                          GET UP 44\n
                          ENOUGH TALK\n
                          TALK TO THE HAND VAR\n
                          YOU HAVE BEEN TERMINATED")) => "66\n")

(fact "evaluate when an integer is decremented and printed" 
  (with-out-str  (roll-credits  "IT'S SHOWTIME 
                          HEY CHRISTMAS TREE VAR 
                          YOU SET US UP 22
                          GET TO THE CHOPPER VAR
                          HERE IS MY INVITATION VAR
                          GET DOWN 44
                          ENOUGH TALK
                          TALK TO THE HAND VAR
                          YOU HAVE BEEN TERMINATED")) => "-22\n")


(fact "evaluate when an integer is decremented with a negative value"
  (with-out-str (roll-credits "IT'S SHOWTIME\n 
                          HEY CHRISTMAS TREE VAR\n
                          YOU SET US UP 22\n
                          GET TO THE CHOPPER VAR\n 
                          HERE IS MY INVITATION VAR\n 
                          GET DOWN -44\n 
                          ENOUGH TALK\n 
                          TALK TO THE HAND VAR\n
                          YOU HAVE BEEN TERMINATED\n")) => "66\n")

(fact "evaluate when an integer is incremented with a negative value"
  (with-out-str  (roll-credits "IT'S SHOWTIME\n
                          HEY CHRISTMAS TREE VAR\n
                          YOU SET US UP 22\n
                          GET TO THE CHOPPER VAR\n
                          HERE IS MY INVITATION VAR\n
                          GET UP -44\n
                          ENOUGH TALK\n
                          TALK TO THE HAND VAR\n
                          YOU HAVE BEEN TERMINATED")) => "-22\n")

(fact "evaluate when multiplying variables"
  (with-out-str  (roll-credits "IT'S SHOWTIME\n
                          HEY CHRISTMAS TREE VAR\n
                          YOU SET US UP 22\n
                          GET TO THE CHOPPER VAR\n
                          HERE IS MY INVITATION VAR\n
                          YOU'RE FIRED 13\n
                          ENOUGH TALK\n
                          TALK TO THE HAND VAR\n
                          YOU HAVE BEEN TERMINATED")) => "286\n")

(fact "evaluate when multiplying variables with different signs"
  (with-out-str (roll-credits  "IT'S SHOWTIME 
                          HEY CHRISTMAS TREE VAR
                          YOU SET US UP 22
                          GET TO THE CHOPPER VAR
                          HERE IS MY INVITATION VAR
                          YOU'RE FIRED -13
                          ENOUGH TALK
                          TALK TO THE HAND VAR
                          YOU HAVE BEEN TERMINATED")) => "-286\n")

(fact "evaluate when multiplying variables with zero"
  (with-out-str (roll-credits  "IT'S SHOWTIME 
                           HEY CHRISTMAS TREE VAR 
                           YOU SET US UP 22 
                           GET TO THE CHOPPER VAR 
                           HERE IS MY INVITATION VAR 
                           YOU'RE FIRED 0 
                           ENOUGH TALK 
                           TALK TO THE HAND VAR 
                           YOU HAVE BEEN TERMINATED")) => "0\n")

(fact "evaluate when multiplying assigned variables"
  (with-out-str (roll-credits  "IT'S SHOWTIME 
                          HEY CHRISTMAS TREE VAR 
                          YOU SET US UP 7 
                          HEY CHRISTMAS TREE VAR2 
                          YOU SET US UP 4 
                          GET TO THE CHOPPER VAR 
                          HERE IS MY INVITATION VAR 
                          YOU'RE FIRED VAR2 
                          ENOUGH TALK 
                          TALK TO THE HAND VAR 
                          YOU HAVE BEEN TERMINATED")) => "28\n")

(fact "evaluate when dividing variables"
  (with-out-str (roll-credits "IT'S SHOWTIME 
                          HEY CHRISTMAS TREE VAR 
                          YOU SET US UP 100 
                          GET TO THE CHOPPER VAR 
                          HERE IS MY INVITATION VAR 
                          HE HAD TO SPLIT 4 
                          ENOUGH TALK 
                          TALK TO THE HAND VAR 
                          YOU HAVE BEEN TERMINATED")) => "25\n")

(fact "evaluate when dividing variables with different signs"
  (with-out-str (roll-credits "IT'S SHOWTIME 
                          HEY CHRISTMAS TREE VAR 
                          YOU SET US UP 99 
                          GET TO THE CHOPPER VAR 
                          HERE IS MY INVITATION VAR 
                          HE HAD TO SPLIT -33 
                          ENOUGH TALK 
                          TALK TO THE HAND VAR 
                          YOU HAVE BEEN TERMINATED")) => "-3\n")

(fact "evaluate when dividing variables with one"
  (with-out-str (roll-credits  "IT'S SHOWTIME 
                           HEY CHRISTMAS TREE VAR 
                           YOU SET US UP 22 
                           GET TO THE CHOPPER VAR 
                           HERE IS MY INVITATION VAR 
                           HE HAD TO SPLIT 1 
                           ENOUGH TALK 
                           TALK TO THE HAND VAR 
                           YOU HAVE BEEN TERMINATED")) => "22\n")

(fact "evaluate when dividing assigned variables"
  (with-out-str (roll-credits  "IT'S SHOWTIME 
                          HEY CHRISTMAS TREE VAR 
                          YOU SET US UP 9 
                          HEY CHRISTMAS TREE VAR2 
                          YOU SET US UP 4 
                          GET TO THE CHOPPER VAR 
                          HERE IS MY INVITATION VAR 
                          HE HAD TO SPLIT VAR2 
                          ENOUGH TALK 
                          TALK TO THE HAND VAR 
                          YOU HAVE BEEN TERMINATED")) => "2\n")

(fact "evaluate when calculating modulo variables vol1"
  (with-out-str (roll-credits "IT'S SHOWTIME 
                          HEY CHRISTMAS TREE var 
                          YOU SET US UP 1 
                          GET TO THE CHOPPER var 
                          HERE IS MY INVITATION var 
                          I LET HIM GO 2 
                          ENOUGH TALK 
                          TALK TO THE HAND var 
                          YOU HAVE BEEN TERMINATED")) => "1\n")

(fact "evaluate when calculating modulo variables vol2" 
  (with-out-str (roll-credits  "IT'S SHOWTIME 
                          HEY CHRISTMAS TREE var 
                          YOU SET US UP 2 
                          GET TO THE CHOPPER var 
                          HERE IS MY INVITATION var 
                          I LET HIM GO 2 
                          ENOUGH TALK 
                          TALK TO THE HAND var 
                          YOU HAVE BEEN TERMINATED")) => "0\n")

(fact "evaluate using different arithmetic operations"
  (with-out-str (roll-credits "IT'S SHOWTIME 
                          HEY CHRISTMAS TREE VAR 
                          YOU SET US UP 22 
                          GET TO THE CHOPPER VAR 
                          HERE IS MY INVITATION 11 
                          GET DOWN 43 
                          GET UP 54 
                          GET UP 44 
                          ENOUGH TALK 
                          TALK TO THE HAND VAR 
                          YOU HAVE BEEN TERMINATED")) => "66\n")

(fact "evaluate using different arithmetic operations vol2"
  (with-out-str (roll-credits  "IT'S SHOWTIME 
                          HEY CHRISTMAS TREE VAR 
                          YOU SET US UP 22 
                          GET TO THE CHOPPER VAR 
                          HERE IS MY INVITATION 11 
                          GET DOWN 55 
                          GET UP 11 
                          GET UP 22 
                          GET UP 23 
                          GET DOWN 0 
                          GET UP 0 
                          GET UP 1 
                          ENOUGH TALK 
                          TALK TO THE HAND VAR 
                          YOU HAVE BEEN TERMINATED")) => "13\n")

(fact "evaluate using different arithmetic operations vol3"
  (with-out-str (roll-credits  "IT'S SHOWTIME 
                          HEY CHRISTMAS TREE VAR 
                          YOU SET US UP 22 
                          GET TO THE CHOPPER VAR 
                          HERE IS MY INVITATION 11 
                          GET DOWN 22 
                          HE HAD TO SPLIT -11 
                          YOU'RE FIRED 23 
                          GET UP 23 
                          GET DOWN 22 
                          HE HAD TO SPLIT 2 
                          ENOUGH TALK 
                          TALK TO THE HAND VAR 
                          YOU HAVE BEEN TERMINATED")) => "12\n")

(fact "detect duplicate variable declarations"
  (with-out-str (roll-credits "IT'S SHOWTIME 
                          HEY CHRISTMAS TREE VAR 
                          YOU SET US UP 22 
                          HEY CHRISTMAS TREE VAR 
                          YOU SET US UP 22 
                          YOU HAVE BEEN TERMINATED")) => (throws Exception "WHAT THE FUCK DID I DO WRONG? You made duplicate variable declarations '[:variable \"VAR\"]'" ) )

(fact "detect faulty variable names" 
  (with-out-str (roll-credits  "IT'S SHOWTIME 
                          HEY CHRISTMAS TREE 1VAR 
                          YOU SET US UP 123 
                          YOU HAVE BEEN TERMINATED")) => (throws Exception "WHAT THE FUCK DID I DO WRONG? \nParse error at line 2, column 47:\n                          HEY CHRISTMAS TREE 1VAR \n                                              ^\nExpected:\n\"YOU SET US UP\"\n" ))


