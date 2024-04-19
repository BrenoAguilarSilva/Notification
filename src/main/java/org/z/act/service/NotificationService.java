package org.z.act.service;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.z.act.dto.NotificationUserDTO;
import org.z.act.entity.NotificationEmailEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class NotificationService {
    private final ConvertService convertService;
    @Inject
    public NotificationService(ConvertService convertService) {
        this.convertService = convertService;
    }
    public List<NotificationUserDTO> getNotificationsForUser(String userEmail) {
        Uni<List<NotificationEmailEntity>> uni = NotificationEmailEntity.list("recipient", userEmail);
        List<NotificationEmailEntity> notificationEntities = uni.await().indefinitely();

        return notificationEntities.stream()
                .map(convertService::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<NotificationUserDTO> getNotificationsForLast30Days(String userEmail) {
        LocalDateTime thirtyDaysAgo = LocalDateTime.now().minusDays(30);

        Uni<List<NotificationEmailEntity>> uni = NotificationEmailEntity.list("data >= ?1 and recipient = ?2", thirtyDaysAgo, userEmail);
        List<NotificationEmailEntity> notificationEntities = uni.await().indefinitely();


        return notificationEntities.stream()
                .map(convertService::convertToDTO)
                .collect(Collectors.toList());
    }
}
