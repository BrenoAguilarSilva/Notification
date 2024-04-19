package org.z.act.dto;

import java.util.List;

public class NotificationDTO {
    private String subject;
    private String body;
    private NotificationEmail email;
    private NotificationPush push;

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

    public NotificationEmail getEmail() {
        return email;
    }

    public void setEmail(NotificationEmail email) {
        this.email = email;
    }

    public NotificationPush getPush() {
        return push;
    }

    public void setPush(NotificationPush push) {
        this.push = push;
    }
}
