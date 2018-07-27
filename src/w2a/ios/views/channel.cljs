(ns w2a.ios.views.channel
  (:require [reagent.core :as r]
            [w2a.ios.components :as c]
            [w2a.ios.model :as m]
            [goog.date.DateTime]
            [goog.date.duration]))

(defn menu-bar [title start end]
  (let [id (atom nil)
        end (r/atom 0)]
    (r/create-class
      {:component-will-mount
       (fn []
         (reset! id
                 (js/setInterval
                   #(reset! end (- (.now js/Date) @start))
                   1000)))

       :component-will-unmount
       (fn [] (js/clearInterval @id))

       :reagent-render
       (fn []
         [c/view {:style {:flex-direction :row
                          :top 0
                          :height 75 
                          :align-items :flex-end
                          :justify-content :space-between
                          :border-bottom-width 1
                          :border-color "rgba(53, 208, 186, 0.3)"
                          :background-color :white}}
          [c/view {:flex 1
                   :flex-direction :row
                   :align-items :center
                   :margin-left 5
                   :justify-content :flex-start}
           [c/touchable-opacity {:on-press #(reset! m/drawer? true)}
            [c/image {:source (js/require "./images/menu.png")
                      :style {:margin-bottom 10
                              :margin-left 10
                              :width 25 
                              :height 25}}]]
           [c/text {:style {:font-size 17
                            :margin-bottom 10
                            :margin-left 10}} 
            title]]
          [c/text {:style {:font-size 13
                           :margin-bottom 13
                           :margin-right 15}} 
           "Wait Time: " (bit-or 0 (/ @end 60000)) " min."]])})))

(defn message [name time text suggested? message-text]
  (let [dt (.fromTimestamp goog.date.DateTime time)
        hours (.getHours dt)
        mins (.getMinutes dt)]
    [c/view {:style {:width "100%"
                     :flex-direction :row
                     :margin-bottom 20}}
     [c/view {:style {:margin-left 15
                      :height 35
                      :width 35
                      :justify-content :center
                      :align-items :center
                      :background-color (case name
                                          "Uhuru Kenyatta" "#c93d1b"
                                          "W2A Bot" "#f8c43a"
                                          "#17bebb")
                      :border-radius 4}}
      [c/text {:style {:font-size 14
                       :font-weight "700"
                       :color "#fff"}} 
       (let [[a b] (clojure.string/split (.toUpperCase name) #" ")]
         (str (.substring a 0 1) (.substring b 0 1)))]]
     [c/view {:style {:width "90%"}}
      [c/view {:style {:margin-left 10
                       :flex-direction :row
                       :justify-content :space-between}}
       [c/view {:style {:flex-direction :row}}
        [c/text {:style {:font-size 12
                         :font-weight "700"}} 
         name]
        [c/text {:style {:margin-left 3
                         :font-size 12
                         :color "#999"}}
         (str hours
              ":"
              (if (> 10 mins)
                (str 0 mins)
                mins))]]
       (case suggested?
         true 
         [c/text {:style {:color "#17bebb"
                          :margin-left 3
                          :font-weight "900"
                          :letter-spacing 0.25
                          :font-size 12}}
          "[ RECOMMENDED ]"]

         :completed 
         [c/text {:style {:color "#17bebb"
                          :margin-left 3
                          :font-weight "900"
                          :letter-spacing 0.25
                          :font-size 12}}
          "[ NOTES ]"]
         nil)]
      [c/text {:selectable true
               :style {:color (if suggested? "#aaa" :black)
                       :margin-top 5
                       :margin-left 10
                       :line-height 18}} 
       text]
      (case suggested?
        true 
        [c/view {:style {:margin-left 10
                         :margin-right 5
                         :margin-top 10
                         :flex-direction :row-reverse}}
          [c/touchable-opacity {:style {:justify-content :center
                                        :align-items :center
                                        :padding-left 10
                                        :padding-right 10
                                        :border-width 1
                                        :border-radius 4
                                        :border-color "#17bebb"}
                                :on-press (fn []
                                            (swap! m/seeker-messages (comp vec drop-last))
                                            (m/add-message m/seeker-messages
                                                           :user
                                                           text))}
           [c/text {:style {:font-size 14
                            :color "#17bebb"}}
            "Send"]]
          [c/touchable-opacity {:style {:padding 10
                                        :margin-right 30}
                                :on-press (fn []
                                            (swap! m/seeker-messages (comp vec drop-last))
                                            (reset! message-text text))}
           [c/text {:style {:font-size 14
                            :color "#555"}}
            "Edit"]]]


        :completed
        [c/view {:style {:margin-left 10
                         :margin-right 5
                         :margin-top 10
                         :flex-direction :row-reverse}}
         [c/touchable-opacity {:style {:justify-content :center
                                       :align-items :center
                                       :padding-left 10
                                       :padding-right 10
                                       :border-width 1
                                       :border-radius 4
                                       :border-color "#17bebb"}
                               :on-press (fn []
                                           (reset! m/app-state :notes))}
          [c/text {:style {:font-size 14
                           :color "#17bebb"}}
           "Salvation"]]
         [c/touchable-opacity {:style {:padding 10
                                       :margin-right 15}
                               :on-press (fn []
                                           (reset! m/app-state :notes))}
          [c/text {:style {:font-size 14
                           :color "#555"}}
           "Connected to Church"]]
         [c/touchable-opacity {:style {:padding 10
                                       :margin-right 15}
                               :on-press (fn []
                                           (reset! m/app-state :notes))}
          [c/text {:style {:font-size 14
                           :color "#555"}}
           "Rejected"]]]
        nil)]]))

(defn message-body [component messages message-text]
  [c/scroll-view {:ref (fn [com] (reset! component com))
                  :onContentSizeChange (fn []
                                         (when-let [c @component]
                                           (.scrollToEnd c)))
                  :content-container-style {:padding-top 15
                                            :padding-bottom 15
                                            :padding-right 25 
                                            :justify-content :flex-end
                                            :align-items :flex-start
                                            :flex-direction :column
                                            :background-color :white}}
   (for [{:keys [name time text suggested?]} @messages]
     ^{:key (str time text)} [message name time text suggested? message-text])])

(defn messages-input [focus? message-text scroll-body]
  [c/text-input {:multiline true
                 :flex 2
                 :z-index 1
                 :on-change-text #(reset! message-text %)
                 :on-focus (fn []
                             (reset! focus? true)
                             (.scrollToEnd @scroll-body))
                 :on-blur #(reset! focus? false)
                 :value @message-text
                 :margin-right 20
                 :placeholder "Type a message."}])

(defn message-controls [messages message-text start scroll-body]
  (let [focus? (r/atom false)]
    (fn [messages message-text start scroll-body]
      [c/view {:style {:flex 2
                       :width "100%"
                       :border-top-width 1
                       :border-color "#17bebb"
                       :background-color :white
                       :flex-direction :column
                       :padding-top 8
                       :padding-left 15}}
       [messages-input focus? message-text scroll-body]
       (when @focus?
         [c/view {:style {:flex 1
                          :flex-direction :row
                          :justify-content :space-between
                          :margin-bottom 10
                          :margin-right 5}}
          [c/view {:flex-direction :row
                   :style {:padding-top 3}}
           [c/image {:source (js/require "./images/at.png")
                     :style {:padding-left 5
                             :height 20
                             :width 20}}]
           [c/image {:source (js/require "./images/picture.png")
                     :style {:margin-left 5
                             :height 19
                             :width 24}}]]
          [c/touchable-opacity {:on-press (fn []
                                            (let [mt @message-text
                                                  t (.now js/Date)]
                                              (m/add-message
                                                messages
                                                :user
                                                mt)
                                              (reset! message-text "")))
                                :style {:border-width 3
                                        :border-color "#17bebb"
                                        :background-color (if (= @message-text "")
                                                            "#fff"
                                                            "#17bebb")
                                        :height 25
                                        :width 50
                                        :align-items :center
                                        :justify-content :center
                                        :border-radius 4}}
           [c/text {:style {:font-size 13
                            :font-weight "700"
                            :color (if (= @message-text "")
                                     "#17bebb"
                                     "white")}} 
            "SEND"]]])])))


(defn channel [title messages]
  (let [start (r/atom 0)
        end (r/atom 0)
        scroll-body (atom nil)
        message-text (r/atom "")]
    (r/create-class
      {:component-did-mount
       (fn [] 
         (reset! start (.now js/Date))
         (reset! end @start))

       :reagent-render
       (fn [title messages]
         [c/view {:style {:flex 1}}
          [menu-bar title start end]
          [c/kb-view {:behavior :padding
                      :style {:flex 10}}
           [c/view {:style {:flex 10}}
            [message-body scroll-body messages message-text]]
           [message-controls messages message-text start scroll-body]]])})))
