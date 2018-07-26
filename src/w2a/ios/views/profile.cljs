(ns w2a.ios.views.profile
  (:require [reagent.core :as r]
            [w2a.ios.components :as c]
            [w2a.ios.model :as m]))

(def country (r/atom ""))

(defn profile []
  [c/kb-view {:behavior :padding
              :style {:flex 1
                      :flex-direction "column"
                      :justify-content "space-between"
                      :padding 20
                      :background-color "#cd5334"}}
   [c/view {:style {:flex 1
                    :padding 30
                    :justify-content :center
                    :align-items :center}}
    [c/view {:style {
                     :justify-content :center
                     :align-items :center}}

     [c/logo]
     [c/text {:style {:color "white"
                      :font-size 24
                      :font-weight "700"}}
      "Tell us about you."]
     [c/text {:style {:color "white"
                      :font-size 16
                      :margin-top 10
                      :font-weight "400"}}
      "Let us customize your experience so you can save more seekers!"]]]
   [c/view {:style {:flex 1}}
    [c/input :placeholder "Full Name"]
    [c/input :placeholder "Email"]
    [c/input :placeholder "Phone"]
    [c/view {:style {:margin-top 4
                     :padding 3}}
     [c/button "Save"
      (fn [] (reset! m/app-state :community))]]]])
