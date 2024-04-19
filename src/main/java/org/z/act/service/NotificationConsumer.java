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
        public void processEmail(NotificationDTO notificationDTO) {
            LocalDateTime now = LocalDateTime.now();

            NotificationEmailEntity notificationEntity = new NotificationEmailEntity();
            notificationEntity.setReceiveEmail((notificationDTO.getEmail().isReceiveEmail()));
            notificationEntity.setRecipient(notificationDTO.getEmail().getRecipient());
            notificationEntity.setCcRecipients(notificationDTO.getEmail().getRecipientCC());
            notificationEntity.setSender(notificationDTO.getEmail().getSender());
            notificationEntity.setSubject(notificationDTO.getSubject());
            notificationEntity.setBody(notificationDTO.getBody());
            notificationEntity.setData(now);

            persistNotification(notificationEntity);

            if (notificationDTO.getEmail().isReceiveEmail()) {
                sendEmail(notificationDTO);
            }
        }
        private void sendEmail(NotificationDTO notificationDTO) {
            try {
                Mail mail = Mail.withText(notificationDTO.getEmail().getRecipient(), notificationDTO.getSubject(), notificationDTO.getBody());

                if (notificationDTO.getEmail().getRecipientCC() != null) {
                    for (String ccRecipient : notificationDTO.getEmail().getRecipientCC()) {
                        mail = mail.addCc(ccRecipient);
                    }
                }
                    reactiveMailer.send(mail)
                            .subscribe().with(
                                    success -> LOG.info("E-mail enviado com sucesso para: " + notificationDTO.getEmail().getRecipient()),
                                    failure -> LOG.error("Erro ao enviar e-mail: " + failure.getMessage()));

            } catch (Exception e) {
                LOG.error("Erro ao enviar e-mail: " + e.getMessage(), e);
            }
        }
        private void persistNotification(NotificationEmailEntity notificationEntity) {
            notificationEntity.persist().subscribe().with(
                    result -> LOG.info("Persistencia realizada com sucesso"),
                    failure -> LOG.error("Erro ao persistir e-mail: " + failure.getMessage())
            );
        }
    }
}


