(ns w2a.ios.views.tour
  (:require [reagent.core :as r]
            [w2a.ios.components :as c]
            [w2a.ios.model :as m]))

(defn tour []
  [c/view {:style {:flex 1
                   :background-color :white}}
   [c/view {:style {:flex 2
                    :margin-top 20
                    :justify-content :flex-end
                    :align-items :center}}
    [c/logo]]
   [c/view {:style {:flex 10}}
    [c/scroll-view {:horizontal true
                    :shows-horizontal-scroll-indicator true
                    :content-container-style {:margin-top 10
                                              :margin-left 10}}
     [c/view {:style {:padding-right 10}}
      [c/image {:source (js/require "./images/2.png")
                :style  {:margin-top 15
                         :width 315
                         :height 535}}]]
     [c/view {:style {:padding-right 10}}
      [c/image {:source (js/require "./images/1.png")
                :style  {:margin-top 15
                         :width 315
                         :height 535}}]]
     [c/view {:style {:padding-right 10}}
      [c/image {:source (js/require "./images/3.png")
                :style  {:margin-top 15
                         :width 315
                         :height 535}}]]]]
   [c/view {:style {:flex 2
                    :width "100%"
                    :justify-content :center
                    :align-items :center}}
    [c/touchable-highlight {:style {:background-color "#17bebb"
                                    :padding 10
                                    :border-radius 5
                                    :width "80%"}
                            :on-press #(reset! m/app-state :profile)}
     [c/text {:style {:color "white"
                      :text-align "center"
                      :font-size 24
                      :font-weight "700"}} "Let's get started!"]]]])
