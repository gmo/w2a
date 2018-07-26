(ns w2a.ios.views.profile
  (:require [reagent.core :as r]
            [w2a.ios.components :as c]
            [w2a.ios.model :as m]))

(defn profile []
  [c/view {:style {:flex-direction "row"
                 :justify-content "space-between"
                 :height "100%"
                 :padding-top "20%"
                 :padding-bottom "15%"
                 :width "100%"
                 :background-color "#fff"
                 :align-items "baseline"}}
   [c/view {:style {:flex 1}}
     [c/text {:style {:font-size 18
                    :font-weight "700"}}
      "- Thanks -"]
     [c/text {:style {:font-size 14
                    :font-weight "700"}}
      "for joining"]
     [c/view
      [c/text {:style {:font-size 10}}
       "Name: "]
      [c/text-input {:style {:height 20
                           :border-width 1}
                   :value "John Doe"}]]
     [c/view
      [c/text {:style {:font-size 10}}
       "Email: "]
      [c/text-input {:style {:height 20
                           :border-width 1}
                   :value "john.doe@gmail.com"}]]
     [c/view
      [c/text {:style {:font-size 10}}
       "Phone: "]
      [c/text-input {:style {:height 20
                           :border-width 1}
                   :value "123-456-7890"}]]
     [c/button {:title "Save"
              :style {:align-items "center"}
              :onPress (fn [] (reset! m/app-state :community))}]]])
