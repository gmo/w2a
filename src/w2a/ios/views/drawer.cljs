(ns w2a.ios.views.drawer
  (:require 
    [reagent.core :as r]
    [w2a.ios.components :as c]
    [w2a.ios.lib.swiper :as s]
    [w2a.ios.model :as m]))

(def seeker-count (r/atom (+ 250 (rand-int 250))))
(def seekers (r/atom []))
(def started-update (atom false))

(defn update-seeker-count []
  (js/setTimeout
    (fn []
      (swap! seeker-count
             (fn [x]
               ((if (< (.random js/Math) 0.45) dec inc) x)))
      (update-seeker-count))
    (rand-int 5000)))

(defn drawer-section-item [title on-press]
  [c/touchable-opacity {:on-press (fn []
                                    (on-press)
                                    (reset! m/drawer? false))}
   [c/text {:style {:margin-bottom 15
                    :color :white
                    :font-weight "700"
                    :letter-spacing 0.15
                    :font-size 20}}
    title]])

(defn drawer-section [title items]
  [c/view {:style {:margin-top 20}}
   [c/text {:style {:color "rgba(255,255,255,0.5)"
                    :font-weight "900"
                    :letter-spacing 0.35
                    :font-size 15
                    :margin-bottom 10}}
    title]
   (for [{:keys [title on-press]} items]
     ^{:key title} [drawer-section-item title on-press])])

(defn drawer []
  (r/create-class
    {:component-did-mount
     (fn []
       (when (compare-and-set! started-update false true)
         (update-seeker-count)))

     :reagent-render
     (fn []
       [s/swiper {:style {:flex 1}
                  :onSwipeRight (fn []
                                  (reset! m/drawer? false)
                                  true)}
        [c/view
         {:style {:flex 1
                  :background-color :transparent
                  :flex-direction :row}}
         [c/view
          {:style {:z-index 2
                   :background-color "#cd5334"
                   :flex 4
                   :padding-top 50
                   :padding-left 20}}
          [c/image {:source c/logo-img
                    :style {:width 80
                            :height 35}}]
          [c/text {:style {:margin-top 8 
                           :color :white
                           :letter-spacing 0.3
                           :font-size 17}} 
           [c/text {:style {:width 30
                            :font-weight "900"}} @seeker-count]
           [c/text {:style {:font-size 13}}
            (str " SEEKERS WAITING")]]
          [c/touchable-opacity {:on-press (fn []
                                            (reset! m/app-state :preview)
                                            (reset! m/drawer? false))
                                :style {:margin-top 20
                                        :background-color "#17bebb"
                                        :padding 15 
                                        :border-radius 5
                                        :width "90%"
                                        :align-items :center}}
           [c/text {:style {:color :white
                            :font-weight "900"}} "Add Contact"]]
          [c/view {:style {:margin-top 10}}
           [drawer-section "SEEKERS" (when @m/visited-seeker?
                                       [{:title "Uhuru Kenyatta"
                                        :on-press #(reset! m/app-state :seeker)}])]
           [drawer-section "DISCIPLES" []]
           [drawer-section "CHANNELS" [{:title "#community"
                                        :on-press #(reset! m/app-state :community)}
                                       {:title "#tech-support"
                                        :on-press #(reset! m/app-state :community)}]]]]
         [c/view
          {:style {:flex 1
                   :justify-content :center
                   :align-items :stretch}}
          [c/touchable-opacity
           {:style {:justify-content :center
                    :align-items :center
                    :flex 1}
            :on-press #(reset! m/drawer? false)}
           [c/image {:source (js/require "./images/arrow-right.png")
                     :style {:height 20
                             :width 20}}]]]]])}))
