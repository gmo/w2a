(ns w2a.ios.model
  (:require [reagent.core :as r]))

(def app-state (r/atom :community))

(def show-drawer (r/atom false))
