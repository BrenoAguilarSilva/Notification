package org.z.act.entity;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;
import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoEntity;
import jakarta.persistence.Id;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;
import java.util.List;

@MongoEntity(collection="email")
public class NotificationEmailEntity extends ReactivePanacheMongoEntity {
    public String recipient;

    public List<String> ccRecipients;
    public String sender;
    public String subject;
    public String body;
    public LocalDateTime data;


    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public List<String> getCcRecipients() {
        return ccRecipients;
    }

    public void setCcRecipients(List<String> ccRecipients) {
        this.ccRecipients = ccRecipients;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

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

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }
}