package org.z.act.service;

import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import org.z.act.dto.NotificationDTO;
import org.z.act.dto.NotificationEmail;

@ApplicationScoped
@Path("/notification/api/v1/")
public class NotificationProducer {
    private static final Logger LOG = LoggerFactory.getLogger(NotificationProducer.class);
    @Inject
    @Channel("my-notification")
    Emitter<String> emitter;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response sendNotification(NotificationDTO notificationData) {
        try {
            String message = MessageService.createMessage(notificationData);
            emitter.send(message);
            return Response.status(Response.Status.CREATED).entity("{\"message\": \"Notificação enviada\"}").build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"message\": \"Falha no envio\"}").build();
        }
    }

    @Inject
    @Channel("my-email")
    Emitter<NotificationEmail> emailEmitter;

    @POST
    @Path("sendEmail")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response sendEmail(NotificationEmail notificationEmail) {
        try {
            emailEmitter.send(notificationEmail);
            return Response.status(Response.Status.CREATED).entity("{\"message\": \"Email colocado na fila para envio\"}").build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"message\": \"Falha ao colocar o email na fila\"}").build();
        }
    }
}
