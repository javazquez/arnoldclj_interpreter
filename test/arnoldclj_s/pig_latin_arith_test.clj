(ns arnoldclj_s.pig-latin-arith-test
  (:use midje.sweet)
  (:use arnoldclj_s.pig-latin-lexr)) 



;original string
#_"IT'S SHOWTIME 
 HEY CHRISTMAS TREE VAR 
                          YOU SET US UP 22 
                          GET TO THE CHOPPER VAR 
                          HERE IS MY INVITATION 11 
                          GET DOWN 43 
                          GET UP 54 
                          GET UP 44 
                          ENOUGH TALK 
                          TALK TO THE HAND VAR 
                          YOU HAVE BEEN TERMINATED"
(fact "evaluate using different arithmetic operations"
  ( ights-camera-actionlay
"IT'SWAY OWTIMESHAY 
 EYHAY ISTMASCHRAY EETRAY ARVAY 
 OUYAY ETSAY USWAY UPWAY 22 
 ETGAY OTAY ETHAY OPPERCHAY ARVAY 
 EREHAY ISWAY MYAY INVITATIONWAY 11
 ETGAY OWNDAY 43
 ETGAY UPWAY 54
 ETGAY UPWAY 44
 ENOUGHWAY ALKTAY 
 ALKTAY OTAY ETHAY ANDHAY ARVAY 
 OUYAY AVEHAY EENBAY ERMINATEDTAY" 
) => 
[:Program 
 [:begin-main 
  [:statement 
   [:decvar 
    [:variable "ARVAY"] 
    [:init-val 
     [:number "22"]]]] 
  [:statement 
   [:assignment 
    [:variable "ARVAY"] 
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
    [:variable "ARVAY"]]]]]
) 
