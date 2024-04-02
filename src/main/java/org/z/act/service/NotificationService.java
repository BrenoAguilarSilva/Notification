package org.z.act.service;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import org.z.act.dto.NotificationUserDTO;
import org.z.act.entity.NotificationEmailEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class NotificationService {

    public List<NotificationUserDTO> getNotificationsForUser(String userEmail) {
        Uni<List<NotificationEmailEntity>> uni = NotificationEmailEntity.list("recipient", userEmail);
        List<NotificationEmailEntity> notificationEntities = uni.await().indefinitely();

        return notificationEntities.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<NotificationUserDTO> getNotificationsForLast30Days(String userEmail) {
        LocalDateTime thirtyDaysAgo = LocalDateTime.now().minusDays(30);

        Uni<List<NotificationEmailEntity>> uni = NotificationEmailEntity.list("data >= ?1 and recipient = ?2", thirtyDaysAgo, userEmail);
        List<NotificationEmailEntity> notificationEntities = uni.await().indefinitely();

        return notificationEntities.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private NotificationUserDTO convertToDTO(NotificationEmailEntity entity) {
        NotificationUserDTO dto = new NotificationUserDTO();
        dto.setSubject(entity.getSubject());
        dto.setBody(entity.getBody());
        return dto;
    }
}
