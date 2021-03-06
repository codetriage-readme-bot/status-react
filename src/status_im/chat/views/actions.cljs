(ns status-im.chat.views.actions
  (:require [re-frame.core :as re-frame]
            [status-im.i18n :as i18n]))

(defn view-profile [chat-id group?]
  {:label  (i18n/label :t/view-profile)
   :action #(re-frame/dispatch [(if group? :show-group-chat-profile :show-profile) chat-id])})

(defn- clear-history []
  {:label  (i18n/label :t/clear-history)
   :action #(re-frame/dispatch [:clear-history?])})

(defn- delete-chat [chat-id group?]
  {:label  (i18n/label :t/delete-chat)
   :action #(re-frame/dispatch [:delete-chat? chat-id group?])})

(defn- leave-group-chat [chat-id public?]
  {:label  (i18n/label (if public? :t/leave-public-chat :t/leave-group-chat))
   :action #(re-frame/dispatch [:leave-group-chat? chat-id])})

(defn- chat-actions [chat-id]
  [(view-profile chat-id false)
   (delete-chat chat-id false)])

(defn- group-chat-actions [chat-id]
  [(view-profile chat-id true)
   (clear-history)
   (delete-chat chat-id true)
   (leave-group-chat chat-id false)])

(defn- public-chat-actions [chat-id]
  [(clear-history)
   (delete-chat chat-id true)
   (leave-group-chat chat-id true)])

(defn actions [group-chat? chat-id public?]
  (cond
    public?     (public-chat-actions chat-id)
    group-chat? (group-chat-actions chat-id)
    :else       (chat-actions chat-id)))
