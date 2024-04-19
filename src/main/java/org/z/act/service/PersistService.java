package org.z.act.service;

import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import org.z.act.dto.NotificationEmail;
import org.z.act.entity.NotificationEmailEntity;

import java.time.LocalDateTime;

public class PersistService {
    private static final Logger LOG = LoggerFactory.getLogger(PersistService.class);
    public static NotificationEmailEntity getNotificationEmailEntity(NotificationEmail emailNotification, LocalDateTime now) {
        NotificationEmailEntity notificationEntity = new NotificationEmailEntity();
        notificationEntity.setReceiveEmail(emailNotification.isReceiveEmail());
        notificationEntity.setRecipient(emailNotification.getRecipient());
        notificationEntity.setCcRecipients(emailNotification.getRecipientCC());
        notificationEntity.setSender(emailNotification.getSender());
        notificationEntity.setSubject(emailNotification.getSubject());
        notificationEntity.setBody(emailNotification.getBody());
        notificationEntity.setData(now);
        return notificationEntity;
    }

    public static void persistNotification(NotificationEmailEntity notificationEntity) {
        notificationEntity.persist().subscribe().with(
                result -> LOG.info("Persistencia realizada com sucesso"),
                failure -> LOG.error("Erro ao persistir e-mail: " + failure.getMessage())
        );
    }
}
