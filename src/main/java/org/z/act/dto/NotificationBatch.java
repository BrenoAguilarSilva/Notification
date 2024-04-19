package org.z.act.dto;

public class NotificationBatch extends NotificationDTO{
    private NotificationEmail email;
    private NotificationPush push;

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

    @Override
    public String toString() {
        return "NotificationBatch{" +
                "email=" + this.email +
                ", push=" + this.push +
                '}';
    }
}
