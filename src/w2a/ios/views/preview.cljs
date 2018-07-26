(ns w2a.ios.views.preview
  (:require [reagent.core :as r]
            [w2a.ios.components :as c]
            [w2a.ios.model :as m]
            [w2a.ios.lib.swiper :as s]))

(defn preview-img [source width height]
  [c/view
   {:padding 15}
   [c/image {:source (js/require source)
           :style  {:width width
                    :height height
                    :margin-bottom 10}}]])

(defn preview []
  [c/view {:style {:flex-direction "column"
                 :justify-content "space-between"
                 :height "100%"
                 :padding-top "20%"
                 :padding-bottom "15%"
                 :width "100%"
                 :background-color "#fff"
                 :align-items "baseline"}}
    [s/swiper {:onSwipeRight #(do (reset! m/app-state :community) true)}
      [c/view {}
        [c/view {:style {:flex 1
                        :flex-direction "column"
                        :align-items "center"
                        :justify-content :center}}
          [c/text {:style {:font-size 18}} "Kenya"]
          [preview-img "./images/kenya.png" 170 170]
          [c/text {:style {:font-size 18}} "Olidare Onyango"]]
        [c/view {:style {:margin-top 100 :margin-left 40}}
          [c/text {:style {:font-size 14}} "Website: facebook.com/GodLifePage"]
          [c/text {:style {:font-size 14}} "Channel: Facebook"]
          [c/text {:style {:font-size 14}} "Language: English"]
          [c/text {:style {:font-size 14}} "Decision: I just prayed to receive Christ"]
          [c/text {:style {:font-size 14}} "Comment: How do I receive the Holy Spirit?"]]
        [c/view {:style {:flex-direction "row"}}
          [preview-img "./images/arrow-left.jpg" 32 32]
          [c/text {:style {:font-size 14 :margin-top 20}} "No"]
          [c/text {:style {:font-size 14 :margin-top 20 :margin-left 200}} "Yes"]
          [preview-img "./images/arrow-right.jpg" 32 32]]]
    ]]
)
