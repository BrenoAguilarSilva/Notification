package org.z.act.dto;

import java.util.List;

public class NotificationDTO {
    private String subject;
    private String body;
    private EmailConfig email;
    private PushConfig push;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public EmailConfig getEmail() {
        return email;
    }

    public void setEmail(EmailConfig email) {
        this.email = email;
    }

    public PushConfig getPush() {
        return push;
    }

    public void setPush(PushConfig push) {
        this.push = push;
    }

    public static class EmailConfig {
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

    public static class PushConfig {
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
}
