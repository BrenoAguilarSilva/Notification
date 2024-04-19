package org.z.act.service;

import jakarta.enterprise.context.ApplicationScoped;
import org.z.act.dto.NotificationUserDTO;
import org.z.act.entity.NotificationEmailEntity;

@ApplicationScoped
public class ConvertService {
    public NotificationUserDTO convertToDTO(NotificationEmailEntity entity) {
        NotificationUserDTO dto = new NotificationUserDTO();
        dto.setSubject(entity.getSubject());
        dto.setBody(entity.getBody());
        return dto;
    }
}
