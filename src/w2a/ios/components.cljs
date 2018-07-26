(ns w2a.ios.components
  (:require [reagent.core :as r]))

(def ReactNative (js/require "react-native"))

(def easing (.-Easing ReactNative))
(def animated-value (.-Value (.-Animated ReactNative)))
(def app-registry (.-AppRegistry ReactNative))
(def text (r/adapt-react-class (.-Text ReactNative)))
(def view (r/adapt-react-class (.-View ReactNative)))
(def kb-view (r/adapt-react-class (.-KeyboardAvoidingView ReactNative)))
(def image (r/adapt-react-class (.-Image ReactNative)))
(def activity-indicator (r/adapt-react-class (.-ActivityIndicator ReactNative)))
(def progress (r/adapt-react-class (.-ProgressViewIOS ReactNative)))
(def touchable-highlight (r/adapt-react-class (.-TouchableHighlight ReactNative)))
(def touchable-opacity (r/adapt-react-class (.-TouchableOpacity ReactNative)))
(def scroll-view (r/adapt-react-class (.-ScrollView ReactNative)))
(def text-input (r/adapt-react-class (.-TextInput ReactNative)))
(def picker (r/adapt-react-class (.-Picker ReactNative)))
(def picker-item (r/adapt-react-class (.. ReactNative -Picker -Item)))

(def logo-img (js/require "./images/logo.png"))

(defn logo []
  [image {:source logo-img
          :style  {:width 175
                   :height 80
                   :margin-bottom 10}}])

(defn input [& {:keys [placeholder]}]
  (let [text (r/atom "")
        focus? (r/atom false)]
    (fn []
      [view {:style {:background-color :white
                     :padding 10
                     :border-radius 5
                     :border-color (if @focus?
                                     "#17bebb"
                                     "#cd5334")
                     :border-width 3
                     :width "100%"}}
       [text-input {:placeholder placeholder
                    :on-change #(reset! text (.. % -target -value))
                    :value @text
                    :on-focus #(reset! focus? true)
                    :on-blur #(reset! focus? false)
                    :style {:width "100%"}}]])))

(defn button [button-text on-click]
  [touchable-opacity {:style {:background-color "#17bebb"
                              :padding 7
                              :border-radius 5
                              :width "100%"}
                      :on-press on-click}
   [text {:style {:color "white"
                  :text-align "center"
                  :font-size 20
                  :letter-spacing 1
                  :font-weight "300"}} 
    button-text]])
