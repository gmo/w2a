(ns w2a.ios.views.community
  (:require [reagent.core :as r]
            [w2a.ios.components :as c]
            [w2a.ios.model :as m]
            [goog.date.DateTime]
            [goog.date.duration]))

(def focus? (r/atom false))
(def message-text (r/atom ""))

(def seeker-count (r/atom (+ 250 (rand-int 250))))

(def seekers (r/atom []))

(defn update-seeker-count []
  (js/setTimeout
    (fn []
      (swap! seeker-count
             (fn [x]
               ((if (< (.random js/Math) 0.45) dec inc) x)))
      (update-seeker-count))
    (rand-int 5000)))

(update-seeker-count)

(def system-messages 
  (r/atom [{:name "W2A Bot"
            :time (.now js/Date)
            :text "Welcome to W2A, Witness to All, a new application for real-time chatting with Seekers around the world. Global Media Outreach is looking forward to your time and effort leading others to know Christ."}]))


(js/setTimeout 
  (fn []
    (swap! system-messages
           conj
           {:name "W2A Bot"
            :time (.now js/Date)
            :text "Feel free to introduce yourself to the W2A community of Online Missionaries! Or, click the Menu in the top left to go get your first Seeker contact!"}))
  4000)

(js/setTimeout 
  (fn []
    (swap! system-messages
           conj
           {:name "W2A Bot"
            :time (.now js/Date)
            :text "If you have any questions, just ask them to the OMs here in this channel!"}))
  4000)

#_(def messages 
    (r/atom [{:name "Olidare Kenyatta"
              :time (dec (.now js/Date))
              :text "Oh, that this too solid flesh would thaw, melt, or resolve itself into a dew. Or, that the everlasting had not fixed his canon against self-slaughter. Oh how weary, stale, and unprofitable seem to me all the uses of this world!"}
             {:name "Olidare Kenyatta"
              :time (.now js/Date)
              :text "I like turtles."}
             {:name "Olidare Kenyatta"
              :time (inc (.now js/Date))
              :text "smeol."}]))

(def start (atom (.now js/Date)))

(defn menu-bar []
  (let [id (atom nil)
        end (r/atom (.now js/Date))]
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
           [c/touchable-opacity {:on-press #(reset! m/show-drawer true)}
            [c/image {:source (js/require "./images/menu.png")
                      :style {:margin-bottom 10
                              :margin-left 10
                              :width 25 
                              :height 25}}]]
           [c/text {:style {:font-size 17
                            :margin-bottom 10
                            :margin-left 10}} 
            "Community"]]
          [c/text {:style {:font-size 13
                           :margin-bottom 13
                           :margin-right 15}} 
           "Wait Time: " (bit-or 0 (/ @end 60000)) " min."]])})))

(defn message [name time text]
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
                                          "Olidare Kenyatta" "#c93d1b"
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
                       :flex-direction :row}}
       [c/text {:style {:font-size 12
                        :font-weight "700"}} 
        name]
       [c/text {:style {:margin-left 3
                        :font-size 12
                        :color "#999"}}
        (str hours
             ":"
             mins)]]
      [c/text {:style {:margin-top 5
                       :margin-left 10
                       :line-height 18}} 
       text]]]))

(defn message-body []
  (let [component (atom nil)]
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
     (for [{:keys [name time text]} @system-messages]
       ^{:key time} [message name time text])]))

(defn message-controls []
  [c/view {:style {:flex 2
                   :width "100%"
                   :border-top-width 1
                   :border-color "#17bebb"
                   :background-color :white
                   :flex-direction :column
                   :padding-top 8
                   :padding-left 15}}
   [c/text-input {:multiline true
                  :flex 1
                  :z-index 1
                  :on-change-text #(reset! message-text %)
                  :on-focus #(reset! focus? true)
                  :on-blur #(reset! focus? false)
                  :value @message-text
                  :margin-right 20
                  :placeholder "Type a message."}]
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
                                          (swap! system-messages
                                                 conj
                                                 {:name "Becker Polverini"
                                                  :time t
                                                  :text mt})
                                          (js/setTimeout
                                            #(swap! system-messages
                                                    conj
                                                    {:name "Robert Landon"
                                                     :time (.now js/Date)
                                                     :text "Hey, welcome! Glad you downloaded the app! Feel free to ask any questions here."})
                                            4000)
                                          (reset! start t)
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
        "SEND"]]])])

(defn drawer-section-item [title on-press]
  [c/touchable-opacity {:on-press on-press}
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
   (for [i items]
     ^{:key i} [drawer-section-item i (constantly nil)])])

(defn community []
  [c/view {:style {:flex 1}}
   (when @m/show-drawer
     [c/view
      {:style {:top 0
               :left 0
               :height "100%"
               :width "100%"
               :z-index 1
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
       [c/touchable-opacity 
        {:on-press #(reset! m/show-drawer false)}
        [c/text {:style {:margin-top 8 
                         :color :white
                         :letter-spacing 0.3
                         :font-size 17}} 
         [c/text {:style {:width 30
                          :font-weight "900"}} @seeker-count]
         [c/text {:style {:font-size 13}}
          (str " SEEKERS WAITING")]]]
       [c/touchable-opacity {:on-press #(reset! m/app-state :swipe-for-jesus)
                             :style {:margin-top 20
                                     :background-color "#17bebb"
                                     :padding 15 
                                     :border-radius 5
                                     :width "90%"
                                     :align-items :center}}
        [c/text {:style {:color :white}} "Add Contact"]]
       [c/view {:style {:margin-top 10}}
        [drawer-section "SEEKERS" @seekers]
        [drawer-section "DISCIPLES" []]
        [drawer-section "CHANNELS" ["#community"
                                    "#tech-support"]]]]
      [c/view
       {:style {:flex 1
                :justify-content :center
                :align-items :stretch}}
       [c/touchable-opacity
        {:style {:justify-content :center
                 :align-items :center
                 :flex 1}
         :on-press #(reset! m/show-drawer false)}
        [c/image {:source (js/require "./images/menu.png")
                  :style {:height 20
                          :width 20}}]]]])
   [menu-bar]
   [c/kb-view {:behavior :padding
               :style {:flex 10}}
    [c/view {:style {:flex 10}}
     [message-body]]
    [message-controls]]])
