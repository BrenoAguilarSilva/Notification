package org.z.act.service;

import io.quarkus.mailer.Mail;
import io.quarkus.mailer.reactive.ReactiveMailer;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.z.act.dto.NotificationDTO;
import org.z.act.dto.NotificationEmail;
import org.z.act.entity.NotificationEmailEntity;

import java.time.LocalDateTime;

@ApplicationScoped
public class NotificationConsumer {
    private static final Logger LOG = LoggerFactory.getLogger(NotificationConsumer.class);
    @Incoming("my-notification")
    public Response receiveMessage(String message) {
        LOG.info("Mensagem recebida do Kafka: {}" + message);
        return Response.ok(message, MediaType.TEXT_PLAIN).build();
    }

    @ApplicationScoped
    public static class EmailConsumer {
        @Inject
        ReactiveMailer reactiveMailer;

        @Incoming("my-email")
        public void processEmail(NotificationEmail emailNotification) {
            LocalDateTime now = LocalDateTime.now();

            NotificationEmailEntity notificationEntity = PersistService.getNotificationEmailEntity(emailNotification, now);

            PersistService.persistNotification(notificationEntity);

            if (emailNotification.isReceiveEmail()) {
                sendEmail(emailNotification);
            }
        }
        private void sendEmail(NotificationEmail emailNotification) {
            try {
                Mail mail = Mail.withText(emailNotification.getRecipient(), emailNotification.getSubject(), emailNotification.getBody());

                if (emailNotification.getRecipientCC() != null) {
                    for (String ccRecipient : emailNotification.getRecipientCC()) {
                        mail = mail.addCc(ccRecipient);
                    }
                }
                reactiveMailer.send(mail)
                        .subscribe().with(
                                success -> LOG.info("E-mail enviado com sucesso para: " + emailNotification.getRecipient()),
                                failure -> LOG.error("Erro ao enviar e-mail: " + failure.getMessage()));

            } catch (Exception e) {
                LOG.error("Erro ao enviar e-mail: " + e.getMessage(), e);
            }
        }
    }
}


