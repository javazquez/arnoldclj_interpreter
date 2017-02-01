(ns arnoldclj_s.arithmetic-test 
 (:use midje.sweet)
 (:use arnoldclj_s.lexr))


(fact "function when a variable is declared"
   (lights-camera-action "IT'S SHOWTIME
         HEY CHRISTMAS TREE var
         YOU SET US UP 123
         YOU HAVE BEEN TERMINATED") =>

[:Program 
 [:begin-main 
  [:statement 
   [:decvar 
    [:variable "var"] 
    [:init-val 
     [:number "123"]]]]]]
)


(fact "function when an integer is printed"
 (lights-camera-action "IT'S SHOWTIME
          TALK TO THE HAND 123
          YOU HAVE BEEN TERMINATED") => 
[:Program                                        
 [:begin-main                                
  [:statement 
   [:printing 
    [:number "123"]]]]])

(fact "evaluate when a negative integer is printed"
  (lights-camera-action
   "IT'S SHOWTIME
            TALK TO THE HAND -111
            YOU HAVE BEEN TERMINATED") => 
[:Program 
 [:begin-main
  [:statement 
   [:printing 
    [:number "-111"]]]]])

;have to call run on rest.. recursive is missing
(fact "evaluate when a 'boolean' is printed"
   (lights-camera-action
    "IT'S SHOWTIME
            HEY CHRISTMAS TREE varfalse
            YOU SET US UP @I LIED
            TALK TO THE HAND varfalse
            YOU HAVE BEEN TERMINATED") => 

[:Program 
 [:begin-main 
  [:statement 
   [:decvar 
    [:variable "varfalse"] 
    [:init-val 
     [:bool 
      [:false 0]]]]] 
  [:statement 
   [:printing 
    [:variable "varfalse"]]]]]
)

(fact "evaluate when a string is printed"
   (lights-camera-action
    "IT'S SHOWTIME\n
            TALK TO THE HAND \"this should be printed\"\n
            YOU HAVE BEEN TERMINATED") => 
[:Program 
 [:begin-main
  [:statement 
   [:printing 
    [:quotedstring "this should be printed"]]]]])


(fact "evaluate when a more exotic string is printed"
   (lights-camera-action "IT'S SHOWTIME\n
            TALK TO THE HAND \"!!! ??? äöäöäöä@#0123=-,.\"
            YOU HAVE BEEN TERMINATED") => 
[:Program 
 [:begin-main 
  [:statement 
   [:printing 
    [:quotedstring "!!! ??? äöäöäöä@#0123=-,."]]]]])
      
      
(fact "evaluate when an integer is declared and printed"
   (lights-camera-action "IT'S SHOWTIME\n
                        HEY CHRISTMAS TREE A\n  
                        YOU SET US UP 999\n 
                        HEY CHRISTMAS TREE B\n 
                        YOU SET US UP 555\n
                        TALK TO THE HAND A\n
                        TALK TO THE HAND B\n
                        YOU HAVE BEEN TERMINATED\n") =>

[:Program 
 [:begin-main 
  [:statement 
   [:decvar 
    [:variable "A"] 
    [:init-val 
     [:number "999"]]]] 
  [:statement 
   [:decvar 
    [:variable "B"] 
    [:init-val 
     [:number "555"]]]] 
  [:statement 
   [:printing 
    [:variable "A"]]] 
  [:statement 
   [:printing 
    [:variable "B"]]]]]
) 


 ;fixme spaces after variables are not working     
(fact "evaluate when a negative integer is declared and printed"
  (lights-camera-action "IT'S SHOWTIME
                         HEY CHRISTMAS TREE a\n
                         YOU SET US UP -999\n
                         HEY CHRISTMAS TREE b\n 
                         YOU SET US UP -555\n 
                         TALK TO THE HAND a\n 
                         TALK TO THE HAND b\n 
                         YOU HAVE BEEN TERMINATED") => 

[:Program 
 [:begin-main 
  [:statement 
   [:decvar 
    [:variable "a"] 
    [:init-val 
     [:number "-999"]]]] 
  [:statement 
   [:decvar 
    [:variable "b"] 
    [:init-val 
     [:number "-555"]]]] 
  [:statement 
   [:printing 
    [:variable "a"]]] 
  [:statement 
   [:printing 
    [:variable "b"]]]]]
)
      
(fact "evaluate when assigning a variable"
  (lights-camera-action "IT'S SHOWTIME\n 
                          HEY CHRISTMAS TREE var\n 
                          YOU SET US UP 22\n 
                          GET TO THE CHOPPER var\n 
                          HERE IS MY INVITATION 123\n
                          ENOUGH TALK\n
                          TALK TO THE HAND var\n
                          YOU HAVE BEEN TERMINATED") =>

[:Program 
 [:begin-main 
  [:statement 
   [:decvar 
    [:variable "var"] 
    [:init-val 
     [:number "22"]]]] 
  [:statement 
   [:assignment 
    [:variable "var"] 
    [:set-val 
     [:number "123"]]]] 
  [:statement 
   [:printing 
    [:variable "var"]]]]]
) 

(fact "evaluate when assigning multiple variables"
   (lights-camera-action "IT'S SHOWTIME\n 
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
                          YOU HAVE BEEN TERMINATED\n") =>
[:Program 
 [:begin-main 
  [:statement 
   [:decvar 
    [:variable "var"] 
    [:init-val 
     [:number "22"]]]] 
  [:statement 
   [:decvar 
    [:variable "var2"] 
    [:init-val 
     [:number "27"]]]] 
  [:statement 
   [:assignment 
    [:variable "var"] 
    [:set-val 
     [:number "123"]]]] 
  [:statement 
   [:assignment 
    [:variable "var2"] 
    [:set-val 
     [:number "707"]]]] 
  [:statement 
   [:assignment 
    [:variable "var"] 
    [:set-val 
     [:variable "var2"] 
     [:arithmetic-op 
      [:plus 
       [:variable "var"]]]]]] 
  [:statement 
   [:printing 
    [:variable "var"]]]]]
) 
        
      
(fact "evaluate when an integer is incremented and printed"
   (lights-camera-action "IT'S SHOWTIME\n 
                          HEY CHRISTMAS TREE VAR\n
                          YOU SET US UP 22\n
                          GET TO THE CHOPPER VAR\n
                          HERE IS MY INVITATION VAR\n
                          GET UP 44\n
                          ENOUGH TALK\n
                          TALK TO THE HAND VAR\n
                          YOU HAVE BEEN TERMINATED") => 
[:Program 
 [:begin-main 
  [:statement 
   [:decvar 
    [:variable "VAR"] 
    [:init-val 
     [:number "22"]]]] 
  [:statement 
   [:assignment 
    [:variable "VAR"] 
    [:set-val 
     [:variable "VAR"] 
     [:arithmetic-op 
      [:plus 
       [:number "44"]]]]]] 
  [:statement 
   [:printing 
    [:variable "VAR"]]]]]
)

(fact "evaluate when an integer is decremented and printed" 
   (lights-camera-action  "IT'S SHOWTIME 
                          HEY CHRISTMAS TREE VAR 
                          YOU SET US UP 22
                          GET TO THE CHOPPER VAR
                          HERE IS MY INVITATION VAR
                          GET DOWN 44
                          ENOUGH TALK
                          TALK TO THE HAND VAR
                          YOU HAVE BEEN TERMINATED") => 
[:Program 
 [:begin-main 
  [:statement 
   [:decvar 
    [:variable "VAR"] 
    [:init-val 
     [:number "22"]]]] 
  [:statement 
   [:assignment 
    [:variable "VAR"] 
    [:set-val 
     [:variable "VAR"] 
     [:arithmetic-op 
      [:minus 
       [:number "44"]]]]]] 
  [:statement 
   [:printing 
    [:variable "VAR"]]]]]
)

(fact "evaluate when an integer is decremented with a negative value"
   (lights-camera-action "IT'S SHOWTIME\n 
                          HEY CHRISTMAS TREE VAR\n
                          YOU SET US UP 22\n
                          GET TO THE CHOPPER VAR\n 
                          HERE IS MY INVITATION VAR\n 
                          GET DOWN -44\n 
                          ENOUGH TALK\n 
                          TALK TO THE HAND VAR\n
                          YOU HAVE BEEN TERMINATED\n") =>
[:Program 
 [:begin-main 
  [:statement 
   [:decvar 
    [:variable "VAR"] 
    [:init-val 
     [:number "22"]]]] 
  [:statement 
   [:assignment 
    [:variable "VAR"] 
    [:set-val 
     [:variable "VAR"] 
     [:arithmetic-op 
      [:minus 
       [:number "-44"]]]]]] 
  [:statement 
   [:printing 
    [:variable "VAR"]]]]])   


(fact "evaluate when an integer is incremented with a negative value"
   (lights-camera-action "IT'S SHOWTIME\n
                          HEY CHRISTMAS TREE VAR\n
                          YOU SET US UP 22\n
                          GET TO THE CHOPPER VAR\n
                          HERE IS MY INVITATION VAR\n
                          GET UP -44\n
                          ENOUGH TALK\n
                          TALK TO THE HAND VAR\n
                          YOU HAVE BEEN TERMINATED") => 
[:Program 
 [:begin-main 
  [:statement 
   [:decvar 
    [:variable "VAR"] 
    [:init-val 
     [:number "22"]]]] 
  [:statement 
   [:assignment 
    [:variable "VAR"] 
    [:set-val 
     [:variable "VAR"] 
     [:arithmetic-op 
      [:plus 
       [:number "-44"]]]]]] 
  [:statement 
   [:printing 
    [:variable "VAR"]]]]])  
                        
      
(fact "evaluate when multiplying variables"
   (lights-camera-action "IT'S SHOWTIME\n
                          HEY CHRISTMAS TREE VAR\n
                          YOU SET US UP 22\n
                          GET TO THE CHOPPER VAR\n
                          HERE IS MY INVITATION VAR\n
                          YOU'RE FIRED 13\n
                          ENOUGH TALK\n
                          TALK TO THE HAND VAR\n
                          YOU HAVE BEEN TERMINATED") =>
[:Program 
 [:begin-main 
  [:statement 
   [:decvar 
    [:variable "VAR"] 
    [:init-val 
     [:number "22"]]]] 
  [:statement 
   [:assignment 
    [:variable "VAR"] 
    [:set-val 
     [:variable "VAR"] 
     [:arithmetic-op 
      [:mult 
       [:number "13"]]]]]] 
  [:statement 
   [:printing 
    [:variable "VAR"]]]]]
) 
                        
(fact "evaluate when multiplying variables with different signs"
   (lights-camera-action  "IT'S SHOWTIME 
                          HEY CHRISTMAS TREE VAR
                          YOU SET US UP 22
                          GET TO THE CHOPPER VAR
                          HERE IS MY INVITATION VAR
                          YOU'RE FIRED -13
                          ENOUGH TALK
                          TALK TO THE HAND VAR
                          YOU HAVE BEEN TERMINATED") =>
[:Program 
 [:begin-main 
  [:statement 
   [:decvar 
    [:variable "VAR"] 
    [:init-val 
     [:number "22"]]]] 
  [:statement 
   [:assignment 
    [:variable "VAR"] 
    [:set-val 
     [:variable "VAR"] 
     [:arithmetic-op 
      [:mult 
       [:number "-13"]]]]]] 
  [:statement 
   [:printing 
    [:variable "VAR"]]]]]

)

      
(fact "evaluate when multiplying variables with zero"
   (lights-camera-action  "IT'S SHOWTIME 
                           HEY CHRISTMAS TREE VAR 
                           YOU SET US UP 22 
                           GET TO THE CHOPPER VAR 
                           HERE IS MY INVITATION VAR 
                           YOU'RE FIRED 0 
                           ENOUGH TALK 
                           TALK TO THE HAND VAR 
                           YOU HAVE BEEN TERMINATED") =>
 [:Program 
  [:begin-main 
   [:statement 
    [:decvar 
     [:variable "VAR"] 
     [:init-val 
      [:number "22"]]]] 
   [:statement 
    [:assignment 
     [:variable "VAR"] 
     [:set-val 
      [:variable "VAR"] 
      [:arithmetic-op 
       [:mult 
        [:number "0"]]]]]] 
   [:statement 
    [:printing 
     [:variable "VAR"]]]]])

      
(fact "evaluate when multiplying assigned variables"
   (lights-camera-action  "IT'S SHOWTIME 
                          HEY CHRISTMAS TREE VAR 
                          YOU SET US UP 7 
                          HEY CHRISTMAS TREE VAR2 
                          YOU SET US UP 4 
                          GET TO THE CHOPPER VAR 
                          HERE IS MY INVITATION VAR 
                          YOU'RE FIRED VAR2 
                          ENOUGH TALK 
                          TALK TO THE HAND VAR 
                          YOU HAVE BEEN TERMINATED") =>
[:Program 
 [:begin-main 
  [:statement 
   [:decvar 
    [:variable "VAR"] 
    [:init-val 
     [:number "7"]]]] 
  [:statement 
   [:decvar 
    [:variable "VAR2"] 
    [:init-val 
     [:number "4"]]]] 
  [:statement 
   [:assignment 
    [:variable "VAR"] 
    [:set-val 
     [:variable "VAR"] 
     [:arithmetic-op 
      [:mult 
       [:variable "VAR2"]]]]]] 
  [:statement 
   [:printing 
    [:variable "VAR"]]]]])
      
(fact "evaluate when dividing variables"
   (lights-camera-action "IT'S SHOWTIME 
                          HEY CHRISTMAS TREE VAR 
                          YOU SET US UP 100 
                          GET TO THE CHOPPER VAR 
                          HERE IS MY INVITATION VAR 
                          HE HAD TO SPLIT 4 
                          ENOUGH TALK 
                          TALK TO THE HAND VAR 
                          YOU HAVE BEEN TERMINATED") =>

[:Program 
 [:begin-main 
  [:statement 
   [:decvar 
    [:variable "VAR"] 
    [:init-val 
     [:number "100"]]]] 
  [:statement 
   [:assignment 
    [:variable "VAR"] 
    [:set-val 
     [:variable "VAR"] 
     [:arithmetic-op 
      [:div 
       [:number "4"]]]]]] 
  [:statement 
   [:printing 
    [:variable "VAR"]]]]]
)  
      
(fact "evaluate when dividing variables with different signs"
   (lights-camera-action "IT'S SHOWTIME 
                          HEY CHRISTMAS TREE VAR 
                          YOU SET US UP 99 
                          GET TO THE CHOPPER VAR 
                          HERE IS MY INVITATION VAR 
                          HE HAD TO SPLIT -33 
                          ENOUGH TALK 
                          TALK TO THE HAND VAR 
                          YOU HAVE BEEN TERMINATED") =>
[:Program 
 [:begin-main 
  [:statement 
   [:decvar 
    [:variable "VAR"] 
    [:init-val 
     [:number "99"]]]] 
  [:statement 
   [:assignment 
    [:variable "VAR"] 
    [:set-val 
     [:variable "VAR"] 
     [:arithmetic-op 
      [:div 
       [:number "-33"]]]]]] 
  [:statement 
   [:printing 
    [:variable "VAR"]]]]]
)   
      
(fact "evaluate when dividing variables with one"
   (lights-camera-action  "IT'S SHOWTIME 
                           HEY CHRISTMAS TREE VAR 
                           YOU SET US UP 22 
                           GET TO THE CHOPPER VAR 
                           HERE IS MY INVITATION VAR 
                           HE HAD TO SPLIT 1 
                           ENOUGH TALK 
                           TALK TO THE HAND VAR 
                           YOU HAVE BEEN TERMINATED") => 

[:Program 
 [:begin-main 
  [:statement 
   [:decvar 
    [:variable "VAR"] 
    [:init-val 
     [:number "22"]]]] 
  [:statement 
   [:assignment 
    [:variable "VAR"] 
    [:set-val 
     [:variable "VAR"] 
     [:arithmetic-op 
      [:div 
       [:number "1"]]]]]] 
  [:statement 
   [:printing 
    [:variable "VAR"]]]]])   
      
(fact "evaluate when dividing assigned variables"
   (lights-camera-action  "IT'S SHOWTIME 
                          HEY CHRISTMAS TREE VAR 
                          YOU SET US UP 9 
                          HEY CHRISTMAS TREE VAR2 
                          YOU SET US UP 4 
                          GET TO THE CHOPPER VAR 
                          HERE IS MY INVITATION VAR 
                          HE HAD TO SPLIT VAR2 
                          ENOUGH TALK 
                          TALK TO THE HAND VAR 
                          YOU HAVE BEEN TERMINATED") =>
[:Program 
 [:begin-main 
  [:statement 
   [:decvar 
    [:variable "VAR"] 
    [:init-val 
     [:number "9"]]]] 
  [:statement 
   [:decvar 
    [:variable "VAR2"] 
    [:init-val 
     [:number "4"]]]] 
  [:statement 
   [:assignment 
    [:variable "VAR"] 
    [:set-val 
     [:variable "VAR"] 
     [:arithmetic-op 
      [:div 
       [:variable "VAR2"]]]]]] 
  [:statement 
   [:printing 
    [:variable "VAR"]]]]])   

(fact "evaluate when calculating modulo variables vol1"
   (lights-camera-action "IT'S SHOWTIME 
                          HEY CHRISTMAS TREE var 
                          YOU SET US UP 1 
                          GET TO THE CHOPPER var 
                          HERE IS MY INVITATION var 
                          I LET HIM GO 2 
                          ENOUGH TALK 
                          TALK TO THE HAND var 
                          YOU HAVE BEEN TERMINATED") =>
[:Program 
 [:begin-main 
  [:statement 
   [:decvar 
    [:variable "var"] 
    [:init-val 
     [:number "1"]]]] 
  [:statement 
   [:assignment 
    [:variable "var"] 
    [:set-val 
     [:variable "var"] 
     [:arithmetic-op 
      [:modulo 
       [:number "2"]]]]]] 
  [:statement 
   [:printing 
    [:variable "var"]]]]]
)

(fact "evaluate when calculating modulo variables vol2" 
   (lights-camera-action  "IT'S SHOWTIME 
                          HEY CHRISTMAS TREE var 
                          YOU SET US UP 2 
                          GET TO THE CHOPPER var 
                          HERE IS MY INVITATION var 
                          I LET HIM GO 2 
                          ENOUGH TALK 
                          TALK TO THE HAND var 
                          YOU HAVE BEEN TERMINATED") =>
[:Program 
 [:begin-main 
  [:statement 
   [:decvar 
    [:variable "var"] 
    [:init-val 
     [:number "2"]]]] 
  [:statement 
   [:assignment 
    [:variable "var"] 
    [:set-val 
     [:variable "var"] 
     [:arithmetic-op 
      [:modulo 
       [:number "2"]]]]]] 
  [:statement 
   [:printing 
    [:variable "var"]]]]]
)

(fact "evaluate using different arithmetic operations"
   (lights-camera-action "IT'S SHOWTIME 
                          HEY CHRISTMAS TREE VAR 
                          YOU SET US UP 22 
                          GET TO THE CHOPPER VAR 
                          HERE IS MY INVITATION 11 
                          GET DOWN 43 
                          GET UP 54 
                          GET UP 44 
                          ENOUGH TALK 
                          TALK TO THE HAND VAR 
                          YOU HAVE BEEN TERMINATED") => 
[:Program 
 [:begin-main 
  [:statement 
   [:decvar 
    [:variable "VAR"] 
    [:init-val 
     [:number "22"]]]] 
  [:statement 
   [:assignment 
    [:variable "VAR"] 
    [:set-val 
     [:number "11"] 
     [:arithmetic-op 
      [:minus 
       [:number "43"]]] 
     [:arithmetic-op 
      [:plus 
       [:number "54"]]] 
     [:arithmetic-op 
      [:plus 
       [:number "44"]]]]]] 
  [:statement 
   [:printing 
    [:variable "VAR"]]]]]
) 

(fact "evaluate using different arithmetic operations vol2"
   (lights-camera-action  "IT'S SHOWTIME 
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
                          YOU HAVE BEEN TERMINATED") => 
[:Program 
 [:begin-main 
  [:statement 
   [:decvar 
    [:variable "VAR"] 
    [:init-val 
     [:number "22"]]]] 
  [:statement 
   [:assignment 
    [:variable "VAR"] 
    [:set-val 
     [:number "11"] 
     [:arithmetic-op 
      [:minus 
       [:number "55"]]] 
     [:arithmetic-op 
      [:plus 
       [:number "11"]]] 
     [:arithmetic-op 
      [:plus 
       [:number "22"]]] 
     [:arithmetic-op 
      [:plus 
       [:number "23"]]] 
     [:arithmetic-op 
      [:minus 
       [:number "0"]]] 
     [:arithmetic-op 
      [:plus 
       [:number "0"]]] 
     [:arithmetic-op 
      [:plus 
       [:number "1"]]]]]] 
  [:statement 
   [:printing 
    [:variable "VAR"]]]]]

)
      
(fact "evaluate using different arithmetic operations vol3"
   (lights-camera-action  "IT'S SHOWTIME 
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
                          YOU HAVE BEEN TERMINATED") =>
[:Program 
 [:begin-main 
  [:statement 
   [:decvar 
    [:variable "VAR"] 
    [:init-val 
     [:number "22"]]]] 
  [:statement 
   [:assignment 
    [:variable "VAR"] 
    [:set-val 
     [:number "11"] 
     [:arithmetic-op 
      [:minus 
       [:number "22"]]] 
     [:arithmetic-op 
      [:div 
       [:number "-11"]]] 
     [:arithmetic-op 
      [:mult 
       [:number "23"]]] 
     [:arithmetic-op 
      [:plus 
       [:number "23"]]] 
     [:arithmetic-op 
      [:minus 
       [:number "22"]]] 
     [:arithmetic-op 
      [:div 
       [:number "2"]]]]]] 
  [:statement 
   [:printing 
    [:variable "VAR"]]]]]
)
      
;FIXME this should produce an error
(fact "detect duplicate variable declarations"
   (lights-camera-action "IT'S SHOWTIME 
                          HEY CHRISTMAS TREE VAR 
                          YOU SET US UP 22 
                          HEY CHRISTMAS TREE VAR 
                          YOU SET US UP 22 
                          YOU HAVE BEEN TERMINATED") =>
[:Program 
 [:begin-main 
  [:statement 
   [:decvar 
    [:variable "VAR"] 
    [:init-val 
     [:number "22"]]]] 
  [:statement 
   [:decvar 
    [:variable "VAR"] 
    [:init-val 
     [:number "22"]]]]]]
 )

 ; FIXME throws a parse error and is expected     
(fact "detect faulty variable names"  
  (with-out-str (lights-camera-action  "IT'S SHOWTIME 
                          HEY CHRISTMAS TREE 1VAR 
                          YOU SET US UP 123 
                          YOU HAVE BEEN TERMINATED")) => (throws Exception 
                                                            "WHAT THE FUCK DID I DO WRONG? \nParse error at line 2, column 47:\n                          HEY CHRISTMAS TREE 1VAR \n                                              ^\nExpected:\n\"YOU SET US UP\"\n")) 



