(ns w2a.ios.core
  (:require [reagent.core :as r]))

(def ReactNative (js/require "react-native"))

(def easing (.-Easing ReactNative))
(def animated-value (.-Value (.-Animated ReactNative)))
(def app-registry (.-AppRegistry ReactNative))
(def text (r/adapt-react-class (.-Text ReactNative)))
(def view (r/adapt-react-class (.-View ReactNative)))
(def image (r/adapt-react-class (.-Image ReactNative)))
(def activity-indicator (r/adapt-react-class (.-ActivityIndicator ReactNative)))
(def progress (r/adapt-react-class (.-ProgressViewIOS ReactNative)))
(def touchable-highlight (r/adapt-react-class (.-TouchableHighlight ReactNative)))
(def scroll-view (r/adapt-react-class (.-ScrollView ReactNative)))
(def text-input (r/adapt-react-class (.-TextInput ReactNative)))

(def logo-img (js/require "./images/logo.png"))

(defn alert [title]
  (.alert (.-Alert ReactNative) title))

(def app-state (r/atom :login))

(defn logo []
  [image {:source logo-img
          :style  {:width 175 
                   :height 80 
                   :margin-bottom 10}}])

(defn tour-img [source]
  [view
   {:padding 15}
   [image {:source (js/require source)
           :style  {:width 275 
                    :height 300 
                    :margin-bottom 10}}]])

(defn loading-screen []
  (let [progress-atom (r/atom 0)]
    (r/create-class
      {:component-did-mount
       (fn [this]
         (let [id (atom 0)]
           (reset! id
                   (js/setInterval 
                     (fn [] 
                       (let [x (swap! progress-atom #( + 0.005 %))]
                         (when (> x 1.0)
                           (reset! app-state :tour)
                           (js/clearInterval @id)))) 15))))
       :reagent-render
       (fn []
         [view {:style {:flex-direction "column" 
                        :justify-content "space-between"
                        :height "100%"
                        :padding-top "20%"
                        :padding-bottom "15%"
                        :width "100%"
                        :background-color "#cd5334"
                        :align-items "center"}}
          [view {:style {:align-items "center"}}
           [logo]
           [text {:style {:font-size 16 
                          :font-weight "700" 
                          :color "rgba(255,255,255,0.7)"
                          :letter-spacing 3
                          :text-align "center"}} 
            "WITNESS TO ALL"]]
          [progress {:style {:width "100%"}
                     :progress-tint-color "#17BEBB"
                     :progress-view-style "bar"
                     :track-tint-color "#fff"
                     :progress @progress-atom}]])})))

(defn tour []
  [view {:style {:flex 1
                 :background-color "#cd5334"}}
   [view {:style {:flex 1
                  :align-items :center
                  :justify-content :center
                  :width "100%"}}
    [logo]]
   [view {:style {:flex 2
                  :width "100%"
                  :align-items :center}}
    [text {:style {:font-size 18
                   :font-weight "700"
                   :width "80%"
                   :color "#fff"
                   :margin-bottom 10}} 
     "Take a look at these images to learn about the app!"]
    [scroll-view {:horizontal true
                  :shows-horizontal-scroll-indicator true}
     [tour-img "./images/1.jpg"]
     [tour-img "./images/2.jpg"]
     [tour-img "./images/3.jpg"]]]
   [view {:style {:flex 1
                  :width "100%"
                  :justify-content :center
                  :align-items :center}}
    [touchable-highlight {:style {:background-color "#17bebb" 
                                  :padding 10 
                                  :border-radius 5
                                  :width "80%"}}
     [text {:style {:color "white" 
                    :text-align "center" 
                    :font-size 24
                    :font-weight "700"}} "Ready!"]]]])

(defn login []
  [view {:style {:flex 1
                 :background-color "#cd5334"}}
   [view {:style {:flex 1
                  :align-items :center
                  :justify-content :center
                  :width "100%"}}
    [logo]
    [text {:style {:color :white
                   :font-size 20
                   :font-weight "700"}} 
     "Login"]
    [view {:style {:padding 30
                   :width "100%"}}
    [view {:style {:border :black
                   :border-width 2
                   :padding 10
                   :width "100%"}}
     [text-input {:placeholder "Placeholder"
                  :style {:width "100%"}}]]]]])

(defn app-root []
  (case @app-state 
    :loading [loading-screen]
    :tour [tour]
    :login [login]))

(defn init []
  (.registerComponent app-registry "w2a" #(r/reactify-component app-root)))
