(ns w2a.ios.views.login
  (:require [reagent.core :as r]
            [w2a.ios.components :as c]))

(defn login []
  [c/view {:style {:flex 1
                 :background-color "#cd5334"}}
   [c/view {:style {:flex 1
                  :align-items :center
                  :justify-content :center
                  :width "100%"}}
    [c/logo]
    [c/text {:style {:color :white
                   :font-size 20
                   :font-weight "700"}} 
     "Login"]
    [c/view {:style {:padding 30
                   :width "100%"}}
     [c/view {:style {:border-width 2
                    :padding 10
                    :width "100%"}}
      [c/text-input {:placeholder "Placeholder"
                   :style {:width "100%"}}]]]]])

