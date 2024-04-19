package org.z.act.dto;

public class NotificationPush {
    private boolean receivePush;
    private String recipientPush;

    public boolean isReceivePush() {
        return receivePush;
    }

    public void setReceivePush(boolean receivePush) {
        this.receivePush = receivePush;
    }

    public String getRecipientPush() {
        return recipientPush;
    }

    public void setRecipientPush(String recipientPush) {
        this.recipientPush = recipientPush;
    }
}

