(ns w2a.ios.views.loading
  (:require [reagent.core :as r]
            [w2a.ios.components :as c]
            [w2a.ios.model :as m]))

(defn loading-screen []
  (let [progress-atom (r/atom 0)]
    (r/create-class
      {:component-did-mount
       (fn [this]
         (let [id (atom 0)]
           (reset! id
                   (js/setInterval
                     (fn []
                       (let [x (swap! progress-atom #( + 0.01 %))]
                         (when (> x 1.0)
                           (reset! m/app-state :tour)
                           (js/clearInterval @id)))) 15))))
       :reagent-render
       (fn []
         [c/view {:style {:flex-direction "column"
                        :justify-content "space-between"
                        :height "100%"
                        :padding-top "20%"
                        :padding-bottom "15%"
                        :width "100%"
                        :background-color "#cd5334"
                        :align-items "center"}}
          [c/view {:style {:align-items "center"}}
           [c/logo]
           [c/text {:style {:font-size 16
                          :font-weight "900"
                          :color "rgba(255,255,255,0.7)"
                          :letter-spacing 3
                          :text-align "center"}}
            "WITNESS TO ALL"]]
          [c/progress {:style {:width "100%"}
                     :progress-tint-color "#17BEBB"
                     :progress-view-style "bar"
                     :track-tint-color "#fff"
                     :progress @progress-atom}]])})))

