(ns w2a.ios.components
  (:require [reagent.core :as r]))

(def ReactNative (js/require "react-native"))

(def easing (.-Easing ReactNative))
(def animated-value (.-Value (.-Animated ReactNative)))
(def app-registry (.-AppRegistry ReactNative))
(def button (r/adapt-react-class (.-Button ReactNative)))
(def text (r/adapt-react-class (.-Text ReactNative)))
(def view (r/adapt-react-class (.-View ReactNative)))
(def image (r/adapt-react-class (.-Image ReactNative)))
(def activity-indicator (r/adapt-react-class (.-ActivityIndicator ReactNative)))
(def progress (r/adapt-react-class (.-ProgressViewIOS ReactNative)))
(def touchable-highlight (r/adapt-react-class (.-TouchableHighlight ReactNative)))
(def scroll-view (r/adapt-react-class (.-ScrollView ReactNative)))
(def text-input (r/adapt-react-class (.-TextInput ReactNative)))

(def logo-img (js/require "./images/logo.png"))

(defn logo []
  [image {:source logo-img
          :style  {:width 175
                   :height 80
                   :margin-bottom 10}}])
