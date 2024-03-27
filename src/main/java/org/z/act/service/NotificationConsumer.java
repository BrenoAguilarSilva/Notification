package org.z.act.service;

import io.quarkus.mailer.Mail;
import io.quarkus.mailer.reactive.ReactiveMailer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.z.act.dto.NotificationDTO;
import org.z.act.entity.NotificationEmailEntity;

@ApplicationScoped
public class NotificationConsumer {
    @Incoming("my-notification")
    public Response receiveMessage(String message) {
        System.out.println("Mensagem recebida do Kafka: " + message);
        return Response.ok(message, MediaType.TEXT_PLAIN).build();
    }

    @ApplicationScoped
    public static class EmailConsumer {
        @Inject
        ReactiveMailer reactiveMailer;

        @Incoming("my-email")
        public void processEmail(NotificationDTO notificationDTO) {
            System.out.println("Email recebido pelo Kafka:");
            System.out.println("Recipient: " + notificationDTO.getRecipient());
            System.out.println("Sender: " + notificationDTO.getSender());
            System.out.println("Subject: " + notificationDTO.getSubject());
            System.out.println("Body: " + notificationDTO.getBody());

            NotificationEmailEntity notificationEntity = new NotificationEmailEntity();
            notificationEntity.setRecipient(notificationDTO.getRecipient());
            notificationEntity.setSender(notificationDTO.getSender());
            notificationEntity.setSubject(notificationDTO.getSubject());
            notificationEntity.setBody(notificationDTO.getBody());
            notificationEntity.persist();

            sendEmail(notificationDTO);
        }
        private void sendEmail(NotificationDTO notificationDTO) {
            try {
                reactiveMailer.send(Mail.withText(notificationDTO.getRecipient(), notificationDTO.getSubject(), notificationDTO.getBody()))
                        .subscribe().with(
                                success -> System.out.println("E-mail enviado com sucesso para: " + notificationDTO.getRecipient()),
                                failure -> System.err.println("Erro ao enviar e-mail: " + failure.getMessage()));
            } catch (Exception e) {
                System.err.println("Erro ao enviar e-mail: " + e.getMessage());
            }
        }
    }
}


