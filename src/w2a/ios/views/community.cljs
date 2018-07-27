(ns w2a.ios.views.community
  (:require [reagent.core :as r]
            [w2a.ios.components :as c]
            [w2a.ios.model :as m]
            [w2a.ios.views.channel :as chan]
            [w2a.ios.views.drawer :as drawer]))

(def simulated? (atom false))

(def welcome-message 
  "Howdy! I'm Bob Landon and I love to help other OM's. I'll be online for the next few minutes. I happen to know a lot about sharing Jesus with Muslims. Happy fishing!")

(def zakat-message 
  "I'm so glad you asked about that. Zakat is a charitable donation in Islam. Your seeker wants to know if good works will get him to heaven. You can tell him The Bible says salvation is God's free gift to us: “For the wages of sin is death but the free gift of God is eternal life in Christ Jesus our Lord.” — Romans 6:23")

(def welcomed? (atom false))
(def zakated? (atom false))

(defn simulate-bot []
  (js/setTimeout 
    (fn []
      (swap! m/community-messages
             conj
             {:name "W2A Bot"
              :time (.now js/Date)
              :text "Feel free to introduce yourself to the W2A community of Online Missionaries! Or, click the Menu in the top left to go get your first Seeker contact!"}))
    4000)

  (js/setTimeout 
    (fn []
      (swap! m/community-messages
             conj
             {:name "W2A Bot"
              :time (.now js/Date)
              :text "If you have any questions, just ask them to the OMs here in this channel!"}))
    (+ 4000 (rand-int 8000)))

  (add-watch m/community-messages
             ::community
             (fn [_ _ o n]
               (let [{:keys [name]} (last n)]
                 (when (= name @m/fullname)
                   (cond
                     (and (not @m/visited-seeker?) (compare-and-set! welcomed? false true))
                     (js/setTimeout
                         #(m/add-message m/community-messages :om welcome-message)
                         4000)

                     (and @m/visited-seeker? (compare-and-set! zakated? false true))
                     (js/setTimeout
                         #(m/add-message m/community-messages :om zakat-message)
                         4000)))))))


(defn community []
  (r/create-class
    {:component-did-mount
     (fn [] 
       (when (compare-and-set! simulated? false true)
         (simulate-bot)))

     :reagent-render
     (fn []
       (if @m/drawer?
         [drawer/drawer]
         [chan/channel "#community" m/community-messages]))}))
