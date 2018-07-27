(ns w2a.ios.views.preview
  (:require [reagent.core :as r]
            [w2a.ios.components :as c]
            [w2a.ios.model :as m]
            [w2a.ios.lib.swiper :as s]))

(defn preview-img 
  ([source width height] [preview-img source width height false])
  ([source width height rounded?]
    [c/view
     {:padding 15}
     [c/image {:source (js/require source)
               :style  {:width width
                        :height height
                        :border-radius (if rounded? (/ height 2) 0)
                        :margin-bottom 10}}]]))

(defn preview-title [text]
  [c/text {:style {:margin-bottom 3
                   :letter-spacing 0.25
                   :color "rgba(0,0,0,0.7)"
                   :font-weight "900"
                   :font-size 15}}
   (.toUpperCase text)])


(defn preview-item [text]
  [c/text {:style {:margin-bottom 3
                   :color :black
                   :font-size 15}}
   text])

(defn preview []
  [s/swiper {:style {:flex 1}
             :onSwipeRight (fn []
                             (reset! m/visited-seeker? true)
                             (reset! m/app-state :seeker) 
                             true)}
   [c/view {:style {:flex 1}}
    [c/view {:style {:flex 2
                     :flex-direction :column
                     :align-items :center
                     :justify-content :center}}
     [c/text {:style {:color "rgba(0,0,0,0.8)"
                      :letter-spacing 4
                      :font-weight "900"
                      :font-size 18}} 
      "KENYA"]
     [preview-img "./images/kenya.png" 170 170 true]
     [c/text {:style {:color :black
                      :font-size 30
                      :letter-spacing 1
                      :font-weight "900"}} 
      "Uhuru Kenyatta"]]
    [c/view {:style {:flex 1}}
      [c/view {:style {:flex-direction :row
                       :margin 15}}
       [c/view {:style {:flex 1}}
        [preview-title "Website"]
        [preview-title "Channel"]
        [preview-title "Language"]
        [preview-title "Decision"]]
       [c/view {:style {:flex 2}}
        [preview-item "Facebook.com/GodLifePage"]
        [preview-item "Facebook"]
        [preview-item "English"]
        [preview-item "Prayed to receive Christ"]]]
      [c/view {:style {:margin 15}}
        [preview-title "Comment"]
        [c/text {:style {:font-size 18
                         :color :black}} 
         (:text (first @m/seeker-messages))]]
      [c/view 
       [c/text {:style {:margin-top 30
                        :text-align :center
                        :color "#777"
                        :font-size 20}} "Accept the conversation?"]]]
    [c/view {:style {:flex 1
                     :margin-bottom 30
                     :flex-direction :row
                     :align-items :flex-end
                     :justify-content :space-between}}
     [c/view {:style {:flex-direction :row
                      :flex 1}}
      [preview-img "./images/arrow-left.png" 32 32]
      [preview-img "./images/no.png" 32 32]]
     [c/view {:style {:flex-direction :row
                      :justify-content :flex-end
                      :flex 1}}
      [preview-img "./images/yes.png" 32 32]
      [preview-img "./images/arrow-right.png" 32 32]]]]])
