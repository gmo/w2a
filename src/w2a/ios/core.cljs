(ns w2a.ios.core
  (:require [reagent.core :as r]
            [w2a.ios.components :as c]
            [w2a.ios.model :as m]
            [w2a.ios.views.community :as community]
            [w2a.ios.views.drawer :as drawer]
            [w2a.ios.views.loading :as loading]
            [w2a.ios.views.login :as login]
            [w2a.ios.views.notes :as notes]
            [w2a.ios.views.preview :as preview]
            [w2a.ios.views.profile :as profile]
            [w2a.ios.views.seeker :as seeker]
            [w2a.ios.views.tour :as tour]
            [w2a.ios.views.win :as win]))

(defn app-root []
  (case @m/app-state
    :loading [loading/loading-screen]
    :tour [tour/tour]
    :login [login/login]
    :profile [profile/profile]
    :community [community/community]
    :seeker [seeker/seeker]
    :preview [preview/preview]
    :notes [notes/notes]
    :win [win/win]))

(defn init []
  (.registerComponent c/app-registry "w2a" #(r/reactify-component app-root)))
