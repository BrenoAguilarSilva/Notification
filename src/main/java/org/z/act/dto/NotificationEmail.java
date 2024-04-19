package org.z.act.dto;

import java.util.List;

public class NotificationEmail extends NotificationDTO{
    private boolean receiveEmail;
    private String recipient;
    private List<String> recipientCC;
    private String sender;

    public boolean isReceiveEmail() {
        return receiveEmail;
    }

    public void setReceiveEmail(boolean receiveEmail) {
        this.receiveEmail = receiveEmail;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public List<String> getRecipientCC() {
        return recipientCC;
    }

    public void setRecipientCC(List<String> recipientCC) {
        this.recipientCC = recipientCC;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
}
