(ns w2a.ios.views.tour
  (:require [reagent.core :as r]
            [w2a.ios.components :as c]
            [w2a.ios.model :as m]))

(defn tour-img [source]
  [c/view
   {:padding 15}
   [c/image {:source (js/require source)
           :style  {:width 275
                    :height 300
                    :margin-bottom 10}}]])

(defn tour []
  [c/view {:style {:flex 1
                 :background-color "#cd5334"}}
   [c/view {:style {:flex 1
                  :align-items :center
                  :justify-content :center
                  :width "100%"}}
    [c/logo]]
   [c/view {:style {:flex 2
                  :width "100%"
                  :align-items :center}}
    [c/text {:style {:font-size 18
                   :font-weight "700"
                   :width "80%"
                   :color "#fff"
                   :margin-bottom 10}}
     "Take a look at these images to learn about the app!"]
    [c/scroll-view {:horizontal true
                  :shows-horizontal-scroll-indicator true}
     [tour-img "./images/1.jpg"]
     [tour-img "./images/2.jpg"]
     [tour-img "./images/3.jpg"]]]
   [c/view {:style {:flex 1
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
                    :font-weight "700"}} "Ready!"]]]])
