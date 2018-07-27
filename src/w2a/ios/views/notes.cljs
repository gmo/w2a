(ns w2a.ios.views.notes
  (:require [reagent.core :as r]
            [w2a.ios.components :as c]
            [w2a.ios.model :as m]
            [w2a.ios.lib.swiper :as s]))

(def text (r/atom ""))
(def focus? (r/atom false))

(defn preview-img [source width height]
  [c/view
   {:padding 15}
   [c/image {:source (js/require source)
             :style  {:width width
                      :height height
                      :margin-bottom 10}}]])


(defn notes []
  [s/swiper {:style {:flex 1}
             :onSwipeRight (fn []
                             (reset! m/app-state :win))}
   [c/kb-view {:behavior :padding
               :style {:flex 1}}
    [c/view {:style {:flex 1
                     :margin-top 50
                     :align-items :center
                     :justify-content :center}}
     [c/text {:style {:font-size 35
                      :color "#17bebb"}} "Notes"]]
    [c/view {:style {:flex 2
                     :background-color :white
                     :border-radius 5
                     :border-color (if @focus?
                                     "#17bebb"
                                     "#ddd")
                     :border-width 3
                     :margin 20}}
     [c/text-input {:flex 1
                    :padding-top 10
                    :padding-bottom 10
                    :padding-left 10 
                    :padding-right 10 
                    :placeholder "Let GMO know how your interaction went so we can get smarter about reaching seekers!"
                    :on-change (fn [e]
                                 (let [s (.. e -target -value)]
                                   (reset! text s)))
                    :value @text
                    :multiline true
                    :numberOfLines 5
                    :on-focus #(reset! focus? true)
                    :on-blur #(reset! focus? false)
                    :style {:width "100%"}}]]
    [c/view {:style {:flex 1
                     :margin-bottom 30
                     :flex-direction :row
                     :align-items :flex-end
                     :justify-content :space-between}}
     [c/view {:style {:flex-direction :row
                      :flex 1}}
      [preview-img "./images/arrow-left.png" 32 32]
      [preview-img "./images/thumbs_down.png" 32 32]]
     [c/view {:style {:flex-direction :row
                      :justify-content :flex-end
                      :flex 1}}
      [preview-img "./images/thumbs_up.png" 32 32]
      [preview-img "./images/arrow-right.png" 32 32]]]]])
