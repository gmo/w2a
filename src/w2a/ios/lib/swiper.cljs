(ns w2a.ios.lib.swiper
  (:require [reagent.core :as r]
            [w2a.ios.components :as c]
            [w2a.ios.model :as m]))

(def swipe-directions {:right "SWIPE_RIGHT" :left "SWIPE_LEFT" :up "SWIPE_UP" :down "SWIPE_DOWN"})
(def swipe-config {:velocity-threshold 0.1 :directional-offset-threshold 50})

(defn abs [n] (max n (- n)))

(defn is-valid-swipe [velocity 
                      velocity-threshold 
                      directional-offset 
                      directional-offset-threshold]
  (and (> (abs velocity) velocity-threshold) 
       (> (abs directional-offset) directional-offset-threshold)))

(defn gesture-is-click [gesture-state]
  (and
    (< (abs (.-dx gesture-state)) 5)
    (< (abs (.-dy gesture-state)) 5)))

(defn should-set-pan-responder [evt gesture-state]
  (and
    (not (gesture-is-click gesture-state))
    (= (.-length (.-touches (.-nativeEvent evt))) 1)))

(defn is-valid-horizontal-swipe [gesture-state]
  (is-valid-swipe
    (.-vx gesture-state)
    (:velocity-threshold swipe-config)
    (.-dx gesture-state)
    (:directional-offset-threshold swipe-config)))

(defn is-valid-vertical-swipe [gesture-state]
  (is-valid-swipe
    (.-vy gesture-state)
    (:velocity-threshold swipe-config)
    (.-dy gesture-state)
    (:directional-offset-threshold swipe-config)))


(defn get-swipe-direction [evt gesture-state]
  (if (is-valid-horizontal-swipe gesture-state)
    (if (> (.-dx gesture-state) 0) 
      (:right swipe-directions) 
      (:left swipe-directions))
    (if (is-valid-vertical-swipe gesture-state)
      (if (> (.-dy gesture-state) 0) 
        (:down swipe-directions) 
        (:up swipe-directions))
      nil)))

(def nop (constantly nil))

(defn swiper [{:keys [onSwipeRight onSwipeLeft onSwipeUp onSwipeDown] 
               :or {onSwipeRight nop
                    onSwipeLeft nop
                    onSwipeUp nop
                    onSwipeDown nop}
               :as attrs} body]
  (let [atom-pan-responder (atom nil)]
    (r/create-class
      {:component-will-mount 
       (fn [this]
         (let [trigger-swipe-handlers (fn [swipe-direction gesture-state]
                                        (case swipe-direction
                                          "SWIPE_LEFT" (onSwipeLeft)
                                          "SWIPE_RIGHT" (onSwipeRight)
                                          "SWIPE_UP" (onSwipeUp)
                                          "SWIPE_DOWN" (onSwipeDown)
                                          nil))]
           (let [responder-end (fn [evt gesture-state]
                                 (trigger-swipe-handlers (get-swipe-direction evt gesture-state) gesture-state))]
             (reset!
               atom-pan-responder
               (.create c/pan-responder (clj->js {:onStartShouldSetPanResponder should-set-pan-responder
                                                  :onMoveShouldSetPanResponder should-set-pan-responder
                                                  :onPanResponderRelease responder-end
                                                  :onPanResponderTerminate responder-end}))))))
       :reagent-render 
       (fn [attrs body]
         [c/view
          (merge (js->clj (.-panHandlers @atom-pan-responder)) {:style (:style attrs)})
          body])})))
