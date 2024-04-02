package org.z.act.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.z.act.dto.NotificationUserDTO;
import org.z.act.entity.NotificationEmailEntity;
import org.z.act.service.NotificationService;

import java.util.List;

@Path("/notification/api/v1/")
@Produces(MediaType.APPLICATION_JSON)
public class NotificationResource {
    @Inject
    NotificationService notificationService;
    @GET
    @Path("/userNotification/{email}")
    public List<NotificationUserDTO> getUserNotifications(@PathParam("email") String userEmail){
        return notificationService.getNotificationsForUser(userEmail);
    }

    @GET
    @Path("/userNotification/date/{email}")
    public List<NotificationUserDTO> getNotificationDate(@PathParam("email") String userEmail){
        return notificationService.getNotificationsForLast30Days(userEmail);
    }
}