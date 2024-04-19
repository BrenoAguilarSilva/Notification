package org.z.act.service;

import org.z.act.dto.NotificationDTO;
import org.z.act.dto.NotificationEmail;
import org.z.act.dto.NotificationPush;

public class MessageService {
    public static String createMessage(NotificationDTO notificationData) {
        return switch (notificationData.getClass().getSimpleName()) {
            case "NotificationEmail" -> {
                NotificationEmail emailNotification = (NotificationEmail) notificationData;
                yield String.format("Este email foi enviado por \"%s\" para \"%s\", com a intenção de notifica-lo sobre \"%s\". Este é o conteúdo do email: \"%s\"",
                        emailNotification.getSender(),
                        emailNotification.getRecipient(),
                        emailNotification.getSubject(),
                        emailNotification.getBody());
            }
            case "NotificationPush" -> {
                NotificationPush pushNotification = (NotificationPush) notificationData;
                yield String.format("Notificação push enviada para \"%s\" com a seguinte mensagem: \"%s\"",
                        pushNotification.getRecipientPush(),
                        pushNotification.getBody());
            }
            default ->
                    "Tipo de notificação não suportado";
        };
    }
}
