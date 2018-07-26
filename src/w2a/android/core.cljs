(ns w2a.android.core
  (:require [reagent.core :as r]))

(def ReactNative (js/require "react-native"))

(def app-registry (.-AppRegistry ReactNative))
(def text (r/adapt-react-class (.-Text ReactNative)))
(def view (r/adapt-react-class (.-View ReactNative)))
(def image (r/adapt-react-class (.-Image ReactNative)))
(def touchable-highlight (r/adapt-react-class (.-TouchableHighlight ReactNative)))

(def logo-img (js/require "./images/cljs.png"))

(defn alert [title]
  (.alert (.-Alert ReactNative) title))

(defn app-root []
  [view {:style {:flex-direction "column" :margin 40 :align-items "center"}}
   [text {:style {:font-size 30 :font-weight "100" :margin-bottom 20 :text-align "center"}} "android"]
   [image {:source logo-img
           :style  {:width 80 :height 80 :margin-bottom 30}}]
   [touchable-highlight {:style {:background-color "#999" :padding 10 :border-radius 5}
                         :on-press #(alert "HELLO!")}
    [text {:style {:color "white" :text-align "center" :font-weight "bold"}} "press me"]]])

(defn init []
  (.registerComponent app-registry "w2a" #(r/reactify-component app-root)))
