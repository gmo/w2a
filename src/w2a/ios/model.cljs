(ns w2a.ios.model
  (:require [reagent.core :as r]))

(def app-state (r/atom :tour))

(def drawer? (r/atom false))

(def visited-seeker? (atom false))

(def fullname (r/atom "Becker Polverini"))

(def community-messages 
  (r/atom [{:name "W2A Bot"
            :time (.now js/Date)
            :text "Welcome to W2A, Witness to All, a new application for real-time chatting with Seekers around the world. Global Media Outreach is looking forward to your time and effort leading others to know Christ."}]))

(def seeker-messages 
  (r/atom [{:name "Uhuru Kenyatta"
            :time (.now js/Date)
            :text "Can God forgive me of anything even if I am not rich?"}
           {:name "W2A Bot"
            :time (.now js/Date)
            :text "Hello! The Bible says God will forgive anyone who asks, “If you confess your sins He is faithful and just to forgive us our sins and cleanse us from all unrighteousness. — 1 John 1:9”"
            :suggested? true}]))

(defn add-message 
  ([queue type text] (add-message queue type text nil))
  ([queue type text suggested?]
    (swap! queue
           conj
           {:name (case type
                    :system "W2A Bot"
                    :seeker "Uhuru Kenyatta"
                    :om "Robert Landon"
                    :user @fullname)
            :time (.now js/Date)
            :text text
            :suggested? suggested?})))
