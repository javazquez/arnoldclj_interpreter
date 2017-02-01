(ns arnoldclj_s.logical-test
(:use midje.sweet)
(:use arnoldclj_s.lexr))

(fact "False Or True Evaluate True" 
  (lights-camera-action "IT'S SHOWTIME\n" 
                        "HEY CHRISTMAS TREE var\n" 
                        "YOU SET US UP 0\n" 
                        "GET TO THE CHOPPER var\n" 
                        "HERE IS MY INVITATION 0\n" 
                        "CONSIDER THAT A DIVORCE 1\n" 
                        "ENOUGH TALK\n" 
                        "TALK TO THE HAND var\n" 
                        "YOU HAVE BEEN TERMINATED\n"))
   ; getOutput(code) should equal("1\n")
  


(fact "True Or False Evaluate True" 
  (lights-camera-action "IT'S SHOWTIME\n" 
                        "HEY CHRISTMAS TREE var\n" 
                        "YOU SET US UP @I LIED\n" 
                        "GET TO THE CHOPPER var\n" 
                        "HERE IS MY INVITATION @NO PROBLEMO\n" 
                        "CONSIDER THAT A DIVORCE @I LIED\n" 
                        "ENOUGH TALK\n" 
                        "TALK TO THE HAND var\n" 
                        "YOU HAVE BEEN TERMINATED\n"))
 ;   getOutput(code) should equal("1\n")
  

(fact "True Or True Evaluate True" 
  (lights-camera-action "IT'S SHOWTIME\n" 
                        "HEY CHRISTMAS TREE var\n" 
                        "YOU SET US UP @I LIED\n" 
                        "GET TO THE CHOPPER var\n" 
                        "HERE IS MY INVITATION @NO PROBLEMO\n" 
                        "CONSIDER THAT A DIVORCE @NO PROBLEMO\n" 
                        "ENOUGH TALK\n" 
                        "TALK TO THE HAND var\n" 
                        "YOU HAVE BEEN TERMINATED\n"))
   ; getOutput(code) should equal("1\n")
  

(fact "False Or False Evaluate False" 
  (lights-camera-action "IT'S SHOWTIME\n" 
                        "HEY CHRISTMAS TREE var\n" 
                        "YOU SET US UP @I LIED\n" 
                        "GET TO THE CHOPPER var\n" 
                        "HERE IS MY INVITATION @I LIED\n" 
                        "CONSIDER THAT A DIVORCE @I LIED\n" 
                        "ENOUGH TALK\n" 
                        "TALK TO THE HAND var\n" 
                        "YOU HAVE BEEN TERMINATED\n"))
   ; getOutput(code) should equal("0\n")
  

(fact "False And True Evaluate False" 
  (lights-camera-action "IT'S SHOWTIME\n" 
                        "HEY CHRISTMAS TREE var\n" 
                        "YOU SET US UP @I LIED\n" 
                        "GET TO THE CHOPPER var\n" 
                        "HERE IS MY INVITATION @I LIED\n" 
                        "KNOCK KNOCK @NO PROBLEMO\n" 
                        "ENOUGH TALK\n" 
                        "TALK TO THE HAND var\n" 
                        "YOU HAVE BEEN TERMINATED\n"))
   ; getOutput(code) should equal("0\n")
  

(fact "True And False Evaluate False" 
  (lights-camera-action "IT'S SHOWTIME\n" 
                        "HEY CHRISTMAS TREE var\n" 
                        "YOU SET US UP @I LIED\n" 
                        "GET TO THE CHOPPER var\n" 
                        "HERE IS MY INVITATION @NO PROBLEMO\n" 
                        "KNOCK KNOCK @I LIED\n" 
                        "ENOUGH TALK\n" 
                        "TALK TO THE HAND var\n" 
                        "YOU HAVE BEEN TERMINATED\n"))
   ; getOutput(code) should equal("0\n")
  

(fact "True And True Evaluate True" 
  (lights-camera-action "IT'S SHOWTIME\n" 
                        "HEY CHRISTMAS TREE var\n" 
                        "YOU SET US UP @I LIED\n" 
                        "GET TO THE CHOPPER var\n" 
                        "HERE IS MY INVITATION @NO PROBLEMO\n" 
                        "KNOCK KNOCK @NO PROBLEMO\n" 
                        "ENOUGH TALK\n" 
                        "TALK TO THE HAND var\n" 
                        "YOU HAVE BEEN TERMINATED\n"))
   ; getOutput(code) should equal("1\n")
  

(fact "True and True and False evaluates False" 
  (lights-camera-action "IT'S SHOWTIME\n" 
                        "HEY CHRISTMAS TREE var\n" 
                        "YOU SET US UP @I LIED\n" 
                        "GET TO THE CHOPPER var\n" 
                        "HERE IS MY INVITATION 1\n" 
                        "KNOCK KNOCK 1\n" 
                        "KNOCK KNOCK 0\n" 
                        "ENOUGH TALK\n" 
                        "TALK TO THE HAND var\n" 
                        "YOU HAVE BEEN TERMINATED\n"))
;    getOutput(code) should equal("0\n")
  

(fact "True and True and True evaluates True" 
  (lights-camera-action "IT'S SHOWTIME\n" 
                        "HEY CHRISTMAS TREE var\n" 
                        "YOU SET US UP @I LIED\n" 
                        "GET TO THE CHOPPER var\n" 
                        "HERE IS MY INVITATION 1\n" 
                        "KNOCK KNOCK 1\n" 
                        "KNOCK KNOCK 1\n" 
                        "ENOUGH TALK\n" 
                        "TALK TO THE HAND var\n" 
                        "YOU HAVE BEEN TERMINATED\n"))
   ; getOutput(code) should equal("1\n")
  

(fact "True and True and True and True and False evaluates False" 
  (lights-camera-action "IT'S SHOWTIME\n" 
                        "HEY CHRISTMAS TREE var\n" 
                        "YOU SET US UP @I LIED\n" 
                        "GET TO THE CHOPPER var\n" 
                        "HERE IS MY INVITATION @NO PROBLEMO\n" 
                        "KNOCK KNOCK @NO PROBLEMO\n" 
                        "KNOCK KNOCK @NO PROBLEMO\n" 
                        "KNOCK KNOCK @NO PROBLEMO\n" 
                        "KNOCK KNOCK @I LIED\n" 
                        "ENOUGH TALK\n" 
                        "TALK TO THE HAND var\n" 
                        "YOU HAVE BEEN TERMINATED\n"))
   ; getOutput(code) should equal("0\n")
  

(fact "False or False or False evaluates False" 
  (lights-camera-action "IT'S SHOWTIME\n" 
                        "HEY CHRISTMAS TREE var\n" 
                        "YOU SET US UP @I LIED\n" 
                        "GET TO THE CHOPPER var\n" 
                        "HERE IS MY INVITATION @I LIED\n" 
                        "CONSIDER THAT A DIVORCE @I LIED\n" 
                        "CONSIDER THAT A DIVORCE @I LIED\n" 
                        "ENOUGH TALK\n" 
                        "TALK TO THE HAND var\n" 
                        "YOU HAVE BEEN TERMINATED\n"))
   ; getOutput(code) should equal("0\n")
  

(fact "False or True and False evaluates False" 
  (lights-camera-action "IT'S SHOWTIME\n" 
                        "HEY CHRISTMAS TREE var\n" 
                        "YOU SET US UP @I LIED\n" 
                        "GET TO THE CHOPPER var\n" 
                        "HERE IS MY INVITATION @I LIED\n" 
                        "CONSIDER THAT A DIVORCE @NO PROBLEMO\n" 
                        "KNOCK KNOCK @I LIED\n" 
                        "ENOUGH TALK\n" 
                        "TALK TO THE HAND var\n" 
                        "YOU HAVE BEEN TERMINATED\n"))
   ; getOutput(code) should equal("0\n")
  

(fact "False And False Evaluate False" 
  (lights-camera-action "IT'S SHOWTIME\n" 
                        "HEY CHRISTMAS TREE var\n" 
                        "YOU SET US UP @I LIED\n" 
                        "GET TO THE CHOPPER var\n" 
                        "HERE IS MY INVITATION @I LIED\n" 
                        "KNOCK KNOCK @I LIED\n" 
                        "ENOUGH TALK\n" 
                        "TALK TO THE HAND var\n" 
                        "YOU HAVE BEEN TERMINATED\n"))
   ; getOutput(code) should equal("0\n")
  

(fact "False Equals False evaluates True" 
  (lights-camera-action "IT'S SHOWTIME\n" 
                        "HEY CHRISTMAS TREE varfalse\n" 
                        "YOU SET US UP @I LIED\n" 
                        "HEY CHRISTMAS TREE varfalse2\n" 
                        "YOU SET US UP @I LIED\n" 
                        "GET TO THE CHOPPER varfalse\n" 
                        "HERE IS MY INVITATION @I LIED\n" 
                        "YOU ARE NOT YOU YOU ARE ME varfalse2\n" 
                        "ENOUGH TALK\n" 
                        "TALK TO THE HAND varfalse\n" 
                        "YOU HAVE BEEN TERMINATED\n"))
   ; getOutput(code) should equal("1\n")
  
(fact "True Equals False evaluates False" 
  (lights-camera-action "IT'S SHOWTIME\n" 
                        "HEY CHRISTMAS TREE varfalse\n" 
                        "YOU SET US UP @I LIED\n" 
                        "HEY CHRISTMAS TREE result\n" 
                        "YOU SET US UP @I LIED\n" 
                        "GET TO THE CHOPPER result\n" 
                        "HERE IS MY INVITATION @NO PROBLEMO\n" 
                        "YOU ARE NOT YOU YOU ARE ME varfalse\n" 
                        "ENOUGH TALK\n" 
                        "TALK TO THE HAND result\n" 
                        "YOU HAVE BEEN TERMINATED\n"))
   ; getOutput(code) should equal("0\n")
  

(fact "True Equals True Equals True evaluates True" 
  (lights-camera-action "IT'S SHOWTIME\n" 
                        "HEY CHRISTMAS TREE result\n" 
                        "YOU SET US UP @I LIED\n" 
                        "GET TO THE CHOPPER result\n" 
                        "HERE IS MY INVITATION @NO PROBLEMO\n" 
                        "YOU ARE NOT YOU YOU ARE ME @NO PROBLEMO\n" 
                        "YOU ARE NOT YOU YOU ARE ME @NO PROBLEMO\n" 
                        "ENOUGH TALK\n" 
                        "TALK TO THE HAND result\n" 
                        "YOU HAVE BEEN TERMINATED\n"))
    ;getOutput(code) should equal("1\n")
  

(fact "(13 Equals 13) equals True evaluates True" 
  (lights-camera-action "IT'S SHOWTIME\n" 
                        "HEY CHRISTMAS TREE result\n" 
                        "YOU SET US UP @I LIED\n" 
                        "GET TO THE CHOPPER result\n" 
                        "HERE IS MY INVITATION 13\n" 
                        "YOU ARE NOT YOU YOU ARE ME 13\n" 
                        "YOU ARE NOT YOU YOU ARE ME @NO PROBLEMO\n" 
                        "ENOUGH TALK\n" 
                        "TALK TO THE HAND result\n" 
                        "YOU HAVE BEEN TERMINATED\n"))
    ;getOutput(code) should equal("1\n")
  

(fact "(13 Equals 14) equals False evaluates True" 
  (lights-camera-action "IT'S SHOWTIME\n" 
                        "HEY CHRISTMAS TREE result\n" 
                        "YOU SET US UP @I LIED\n" 
                        "GET TO THE CHOPPER result\n" 
                        "HERE IS MY INVITATION 13\n" 
                        "YOU ARE NOT YOU YOU ARE ME 14\n" 
                        "YOU ARE NOT YOU YOU ARE ME @I LIED\n" 
                        "ENOUGH TALK\n" 
                        "TALK TO THE HAND result\n" 
                        "YOU HAVE BEEN TERMINATED\n"))
    ;getOutput(code) should equal("1\n")
  

(fact "(1 Equals 2) equals 3 evaluates False" 
  (lights-camera-action "IT'S SHOWTIME\n" 
                        "HEY CHRISTMAS TREE result\n" 
                        "YOU SET US UP @I LIED\n" 
                        "GET TO THE CHOPPER result\n" 
                        "HERE IS MY INVITATION 1\n" 
                        "YOU ARE NOT YOU YOU ARE ME 2\n" 
                        "YOU ARE NOT YOU YOU ARE ME 3\n" 
                        "ENOUGH TALK\n" 
                        "TALK TO THE HAND result\n" 
                        "YOU HAVE BEEN TERMINATED\n"))
    ;getOutput(code) should equal("0\n")
  

(fact "13 Equals 13 Equals 14 evaluates False" 
  (lights-camera-action "IT'S SHOWTIME\n" 
                        "HEY CHRISTMAS TREE result\n" 
                        "YOU SET US UP @I LIED\n" 
                        "GET TO THE CHOPPER result\n" 
                        "HERE IS MY INVITATION 13\n" 
                        "YOU ARE NOT YOU YOU ARE ME 13\n" 
                        "YOU ARE NOT YOU YOU ARE ME 14\n" 
                        "ENOUGH TALK\n" 
                        "TALK TO THE HAND result\n" 
                        "YOU HAVE BEEN TERMINATED\n"))
    ;getOutput(code) should equal("0\n")
  

(fact "1 Equals 2 evaluates False" 
  (lights-camera-action "IT'S SHOWTIME\n" 
                        "HEY CHRISTMAS TREE one\n" 
                        "YOU SET US UP 1\n" 
                        "HEY CHRISTMAS TREE two\n" 
                        "YOU SET US UP 2\n" 
                        "HEY CHRISTMAS TREE result\n" 
                        "YOU SET US UP @NO PROBLEMO\n" 
                        "GET TO THE CHOPPER result\n" 
                        "HERE IS MY INVITATION one\n" 
                        "YOU ARE NOT YOU YOU ARE ME two\n" 
                        "ENOUGH TALK\n" 
                        "TALK TO THE HAND result\n" 
                        "YOU HAVE BEEN TERMINATED\n"))
    ;getOutput(code) should equal("0\n")
  

(fact "2 is greater than 1 evaluates True" 
  (lights-camera-action "IT'S SHOWTIME\n" 
                        "HEY CHRISTMAS TREE one\n" 
                        "YOU SET US UP 1\n" 
                        "HEY CHRISTMAS TREE two\n" 
                        "YOU SET US UP 2\n" 
                        "HEY CHRISTMAS TREE result\n" 
                        "YOU SET US UP @NO PROBLEMO\n" 
                        "GET TO THE CHOPPER result\n" 
                        "HERE IS MY INVITATION two\n" 
                        "LET OFF SOME STEAM BENNET one\n" 
                        "ENOUGH TALK\n" 
                        "TALK TO THE HAND result\n" 
                        "YOU HAVE BEEN TERMINATED\n"))
    ;getOutput(code) should equal("1\n")
  

(fact "1 is greater than 2 evaluates False" 
  (lights-camera-action"IT'S SHOWTIME\n" 
                       "HEY CHRISTMAS TREE one\n" 
                       "YOU SET US UP 1\n" 
                       "HEY CHRISTMAS TREE two\n" 
                       "YOU SET US UP 2\n" 
                       "HEY CHRISTMAS TREE result\n" 
                       "YOU SET US UP @NO PROBLEMO\n" 
                       "GET TO THE CHOPPER result\n" 
                       "HERE IS MY INVITATION one\n" 
                       "LET OFF SOME STEAM BENNET two\n" 
                       "ENOUGH TALK\n" 
                       "TALK TO THE HAND result\n" 
                       "YOU HAVE BEEN TERMINATED\n"))
    ;getOutput(code) should equal("0\n")
  

(fact "3 is greater than 3 evaluates False" 
  (lights-camera-action "IT'S SHOWTIME\n" 
                        "HEY CHRISTMAS TREE three\n" 
                        "YOU SET US UP 3\n" 
                        "HEY CHRISTMAS TREE three2\n" 
                        "YOU SET US UP 3\n" 
                        "HEY CHRISTMAS TREE result\n" 
                        "YOU SET US UP @NO PROBLEMO\n" 
                        "GET TO THE CHOPPER result\n" 
                        "HERE IS MY INVITATION three\n" 
                        "LET OFF SOME STEAM BENNET three2\n" 
                        "ENOUGH TALK\n" 
                        "TALK TO THE HAND result\n" 
                        "YOU HAVE BEEN TERMINATED\n"))
    ;getOutput(code) should equal("0\n")
  

#_(fact "detect faulty logical operations" 
    (try (lights-camera-action "IT'S SHOWTIME\n" 
                               "RIGHT? WRONG! VAR\n" 
                               "YOU SET US UP @I LIED\n" 
                               "GET TO THE CHOPPER VAR\n" 
                               "@I LIED\n" 
                               "@I LIED\n" 
                               "CONSIDER THAT A DIVORCE\n" 
                               "@NO PROBLEMO\n" 
                               "ENOUGH TALK\n" 
                               "YOU HAVE BEEN TERMINATED\n")
    (catch Exception e (.getMessage e)))
)
 ;   intercept[ParsingException]
      ;getOutput(code)
    
  
