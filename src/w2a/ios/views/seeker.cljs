(ns w2a.ios.views.seeker
  (:require [reagent.core :as r]
            [w2a.ios.components :as c]
            [w2a.ios.model :as m]
            [w2a.ios.views.channel :as chan]
            [w2a.ios.views.drawer :as drawer]))

(def simulated? (atom false))

(def confusing-message
  "But what about the zakat we give in Islam? Who does that go to and how does Allah hear my prayers if Allah does not receive my zakat?")

(def accepting-message
  "This is so good news! I believe in Jesus! I pray He will forgive me of my sins by the sacrifical blood of Jesus and I commit to Him forever. He is the Son of God and I feel such joy and freedom to know I have life.")

(def confused? (atom false))
(def gospeled? (atom false))

(def asked-bob? (atom false))

(defn simulate-bot []
  (add-watch m/seeker-messages
             ::seeker-messages
             (fn [_ _ o n]
               (let [{:keys [name]} (last n)]
                 (when (= name @m/fullname)
                   (cond
                     (compare-and-set! confused? false true)
                     (js/setTimeout
                       #(m/add-message m/seeker-messages
                                       :seeker
                                       confusing-message)
                       10000)
                     
                     (and @asked-bob? (compare-and-set! gospeled? false true))
                     (js/setTimeout
                       (fn []
                         (m/add-message m/seeker-messages
                                       :seeker
                                       accepting-message)
                         (js/setTimeout
                           #(m/add-message m/seeker-messages
                                           :seeker
                                           "Let's talk tomorrow more about Jesus.")
                           3000)
                         (js/setTimeout
                           #(m/add-message m/seeker-messages
                                           :system
                                           "Uhuru Kenyatta has left."
                                           :completed)
                           6000))
                       10000)))))))

(defn seeker []
  (r/create-class
    {:component-did-mount
     (fn [] 
       (when (compare-and-set! simulated? false true)
         (add-watch m/drawer?
                    ::drawer?
                    (fn [_ _ o n]
                      (when (and (not o) n)
                        (reset! asked-bob? true))))
         (simulate-bot)))

     :reagent-render
     (fn []
       (if @m/drawer?
         [drawer/drawer]
         [chan/channel "Uhuru Kenyatta" m/seeker-messages]))}))
