package org.z.act.service;

import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import org.z.act.dto.NotificationEmail;
import org.z.act.entity.NotificationEmailEntity;

public class PersistService {
    private static final Logger LOG = LoggerFactory.getLogger(PersistService.class);
    public static NotificationEmailEntity getNotificationEmailEntity(NotificationEmail emailNotification) {
        return new NotificationEmailEntity(emailNotification.isReceiveEmail(),emailNotification.getRecipient(),emailNotification.getRecipientCC(),emailNotification.getSender(),emailNotification.getSubject(),emailNotification.getBody());
    }

    public static void persistNotification(NotificationEmailEntity notificationEntity) {
        notificationEntity.persist().subscribe().with(
                result -> LOG.info("Persistencia realizada com sucesso"),
                failure -> LOG.error("Erro ao persistir e-mail: " + failure.getMessage())
        );
    }
}
