(ns w2a.ios.core
  (:require [reagent.core :as r]
            [w2a.ios.components :as c]
            [w2a.ios.model :as m]
            [w2a.ios.views.community :as community]
            [w2a.ios.views.loading :as loading]
            [w2a.ios.views.login :as login]
            [w2a.ios.views.profile :as profile]
            [w2a.ios.views.tour :as tour]))

(defn app-root []
  (case @m/app-state
    :loading [loading/loading-screen]
    :tour [tour/tour]
    :login [login/login]
    :profile [profile/profile]
    :community [community/community]))

(defn init []
  (.registerComponent c/app-registry "w2a" #(r/reactify-component app-root)))
