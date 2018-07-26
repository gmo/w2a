(ns w2a.ios.views.login
  (:require [reagent.core :as r]
            [w2a.ios.components :as c]
            [w2a.ios.model :as m]))


(defn login []
  [c/kb-view {:behavior :padding
              :style {:flex 1
                      :align-items :center
                      :justify-content :center
                      :background-color "#cd5334"}}
   [c/view {:style {:justify-content :center
                    :align-items :center}}
    [c/logo]
    [c/text {:style {:color :white
                     :font-size 20
                     :font-weight "700"}} 
     "Welcome back, missionary!"]]
   [c/view {:style {:padding 30
                    :width "100%"}}
    [c/view {:style {:width "100%"
                     :margin-bottom 5}}
     [c/input :placeholder "Username"]]
    [c/view {:style {:width "100%"}}
     [c/input :placeholder "Password"]]
    [c/view {:style {:width "100%"
                     :margin-top 5
                     :padding 2}}
     [c/button "LOGIN" #(reset! m/app-state :profile)]]]])

