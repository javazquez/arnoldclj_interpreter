(ns arnoldclj_s.branch-statement-test
(:use midje.sweet)
(:use arnoldclj_s.lexr))

(fact "function using a simple if statement"
  (lights-camera-action 
       "IT'S SHOWTIME
        HEY CHRISTMAS TREE vartrue 
        YOU SET US UP @NO PROBLEMO 
        BECAUSE I'M GOING TO SAY PLEASE vartrue
        TALK TO THE HAND \"this branch should be reached\"
        YOU HAVE NO RESPECT FOR LOGIC 
        YOU HAVE BEEN TERMINATED" ) =>
[:Program
 [:begin-main
  [:statement
   [:decvar [:variable "vartrue"] [:init-val [:bool [:true 1]]]]]
  [:statement
   [:if
    [:variable "vartrue"]
    [:statement
     [:printing [:quotedstring "this branch should be reached"]]]]]]])

(fact "function using simple if statement v2"
  (lights-camera-action "IT'S SHOWTIME\n" 
        "HEY CHRISTMAS TREE vartrue\n" 
        "YOU SET US UP @I LIED\n" 
        "BECAUSE I'M GOING TO SAY PLEASE vartrue\n" 
        "TALK TO THE HAND \"this branch should not be reached\"\n" 
        "YOU HAVE NO RESPECT FOR LOGIC\n" 
        "YOU HAVE BEEN TERMINATED\n") =>
[:Program
 [:begin-main
  [:statement
   [:decvar [:variable "vartrue"] [:init-val [:bool [:false 0]]]]]
  [:statement
   [:if
    [:variable "vartrue"]
    [:statement
     [:printing
      [:quotedstring "this branch should not be reached"]]]]]]]
)

(fact "function using simple if else statements"
  (lights-camera-action  "IT'S SHOWTIME\n" 
        "HEY CHRISTMAS TREE vartrue\n" 
        "YOU SET US UP @NO PROBLEMO\n" 
        "BECAUSE I'M GOING TO SAY PLEASE vartrue\n" 
        "TALK TO THE HAND \"this branch should be reached\"\n" 
        "BULLSHIT\n" 
        "TALK TO THE HAND \"this branch should not be reached\"\n" 
        "YOU HAVE NO RESPECT FOR LOGIC\n" 
        "YOU HAVE BEEN TERMINATED\n") =>
[:Program
 [:begin-main
  [:statement
   [:decvar [:variable "vartrue"] [:init-val [:bool [:true 1]]]]]
  [:statement
   [:if
    [:variable "vartrue"]
    [:statement
     [:printing [:quotedstring "this branch should be reached"]]]
    [:else-if
     [:statement
      [:printing
       [:quotedstring "this branch should not be reached"]]]]]]]] )

(fact "function using simple if else statements vol2"
  (lights-camera-action "IT'S SHOWTIME\n" 
                        "HEY CHRISTMAS TREE varfalse\n" 
                        "YOU SET US UP @I LIED\n" 
                        "BECAUSE I'M GOING TO SAY PLEASE varfalse\n" 
                        "TALK TO THE HAND \"this branch should not be reached\"\n" 
                        "BULLSHIT\n" 
                        "TALK TO THE HAND \"this branch should be reached\"\n" 
                        "YOU HAVE NO RESPECT FOR LOGIC\n" 
                        "YOU HAVE BEEN TERMINATED\n") =>
[:Program
 [:begin-main
  [:statement
   [:decvar [:variable "varfalse"] [:init-val [:bool [:false 0]]]]]
  [:statement
   [:if
    [:variable "varfalse"]
    [:statement
     [:printing [:quotedstring "this branch should not be reached"]]]
    [:else-if
     [:statement
      [:printing
       [:quotedstring "this branch should be reached"]]]]]]]])

(fact "function using assigning variables in if statements"
  (lights-camera-action "IT'S SHOWTIME\n" 
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
                        "YOU HAVE BEEN TERMINATED\n") =>
[:Program
 [:begin-main
  [:statement [:decvar [:variable "var"] [:init-val [:number "0"]]]]
  [:statement
   [:decvar [:variable "vartrue"] [:init-val [:bool [:true 1]]]]]
  [:statement
   [:if
    [:variable "vartrue"]
    [:statement
     [:assignment [:variable "var"] [:set-val [:number "3"]]]]]]
  [:statement [:printing [:variable "var"]]]]] )

(fact "function using stub while statement"
  (lights-camera-action "IT'S SHOWTIME\n" 
                        "HEY CHRISTMAS TREE varfalse\n" 
                        "YOU SET US UP @I LIED\n"  ;revisit this
                        "STICK AROUND varfalse\n" 
                        "CHILL\n" 
                        "YOU HAVE BEEN TERMINATED\n") =>
[:Program
 [:begin-main
  [:statement
   [:decvar [:variable "varfalse"] [:init-val [:bool [:false 0]]]]]
  [:statement [:while [:variable "varfalse"]]]]] )

(fact "function using stub while statement vol2"
  (lights-camera-action   "IT'S SHOWTIME\n" 
                          "STICK AROUND @I LIED\n" ;revisit this!
                          "CHILL\n" 
                          "YOU HAVE BEEN TERMINATED\n") =>
[:Program [:begin-main [:statement [:while [:false 0]]]]]
 ) 

(fact "function when while loop executed once"
  (lights-camera-action "IT'S SHOWTIME\n" 
                        "HEY CHRISTMAS TREE varfalse\n" 
                        "YOU SET US UP @NO PROBLEMO\n" 
                        "STICK AROUND varfalse\n" 
                        "GET TO THE CHOPPER varfalse\n" 
                        "HERE IS MY INVITATION @I LIED\n" 
                        "ENOUGH TALK\n" 
                        "TALK TO THE HAND \"while statement printed once\"\n" 
                        "CHILL\n" 
                        "YOU HAVE BEEN TERMINATED\n") => 
[:Program
 [:begin-main
  [:statement
   [:decvar [:variable "varfalse"] [:init-val [:bool [:true 1]]]]]
  [:statement
   [:while
    [:variable "varfalse"]
    [:statement
     [:assignment [:variable "varfalse"] [:set-val [:false 0]]]]
    [:statement
     [:printing [:quotedstring "while statement printed once"]]]]]]] )

(fact "function when while loop executed consequently"
  (lights-camera-action  "IT'S SHOWTIME\n" 
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
                         "YOU HAVE BEEN TERMINATED\n") =>
[:Program
 [:begin-main
  [:statement
   [:decvar
    [:variable "isLessThan10"]
    [:init-val [:bool [:true 1]]]]]
  [:statement [:decvar [:variable "n"] [:init-val [:number "0"]]]]
  [:statement
   [:while
    [:variable "isLessThan10"]
    [:statement
     [:assignment
      [:variable "n"]
      [:set-val
       [:variable "n"]
       [:arithmetic-op [:plus [:number "1"]]]]]]
    [:statement [:printing [:variable "n"]]]
    [:statement
     [:assignment
      [:variable "isLessThan10"]
      [:set-val
       [:number "10"]
       [:logical-op [:gt [:variable "n"]]]]]]]]]] )


