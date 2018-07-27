(ns w2a.ios.views.win
  (:require [reagent.core :as r]
            [w2a.ios.components :as c]))

(defn win []
  [c/view {:style {:flex 1
                   :justify-content :center
                   :align-items :center
                   :background-color "#f8c43a"}}
   [c/text {:style {:font-size 20
                    :font-weight "900"
                    :letter-spacing 3
                    :margin-bottom 30}} "YOU EARNED A BADGE"]
   [c/image {:source (js/require "./images/win.png")
             :style {:height 150
                     :width 150
                     :margin-bottom 30}}]
   [c/text {:style {:font-size 30
                    :font-weight "900"
                    :color "rgba(255,255,255,0.8)"
                    :margin-bottom 30}}
    "THE DISCIPLEMAKER"]])
