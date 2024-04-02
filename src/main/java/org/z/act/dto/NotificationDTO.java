package org.z.act.dto;

import java.util.List;

public class NotificationDTO {
    private String recipient;
    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }
    private List<String> recipientCC;

    public List<String> getRecipientCC() {
        return recipientCC;
    }

    public void setRecipientCC(List<String> recipientCC) {
        this.recipientCC = recipientCC;
    }

    private String sender;

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    private String subject;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    private String body;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }



}
