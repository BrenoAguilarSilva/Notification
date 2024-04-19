package org.z.act.resource;

import io.quarkus.mailer.Mail;
import io.quarkus.mailer.reactive.ReactiveMailer;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.z.act.dto.NotificationEmail;
import org.z.act.entity.NotificationEmailEntity;
import org.z.act.service.EmailService;
import org.z.act.service.PersistService;

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
        EmailService emailService;
        @Incoming("my-email")
        public void processEmail(NotificationEmail emailNotification) {
            LocalDateTime now = LocalDateTime.now();

            NotificationEmailEntity notificationEntity = PersistService.getNotificationEmailEntity(emailNotification, now);

            PersistService.persistNotification(notificationEntity);

            if (emailNotification.isReceiveEmail()) {
                emailService.sendEmail(emailNotification);
            }
        }
    }
}


