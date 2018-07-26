(ns w2a.ios.views.community
  (:require [reagent.core :as r]
            [w2a.ios.components :as c]))

(defn community []
  [c/view
   [c/text {:style {:font-size 18
                  :font-weight "700"}}
    "#Community"]])
