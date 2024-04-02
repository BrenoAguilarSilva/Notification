package org.z.act.service;

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

@ApplicationScoped
@Path("/notification/api/v1/")
public class NotificationProducer {
    @Inject
    @Channel("my-notification")
    Emitter<String> emitter;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN) /////// Remover ou mudar para JSON
    public Response sendNotification(NotificationDTO notificationData) {
        try {
            String message = createMessage(notificationData);
            emitter.send(message);
            return Response.status(Response.Status.CREATED).entity("Notificação enviada").build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Falha no envio").build();
        }
    }

    private String createMessage(NotificationDTO notificationData) {
        return String.format("Este email foi enviado por \"%s\" para \"%s\", com a intenção de notifica-lo sobre \"%s\". Este é o conteúdo do email: \"%s\"",
                notificationData.getSender(),
                notificationData.getRecipient(),
                notificationData.getSubject(),
                notificationData.getBody());
    }

    @Inject
    @Channel("my-email")
    Emitter<NotificationDTO> emailEmitter;

    @POST
    @Path("sendEmail")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN) /////// Remover ou mudar para JSON
    public Response sendEmail(NotificationDTO notificationData) {
        try {
            emailEmitter.send(notificationData);
            return Response.status(Response.Status.CREATED).entity("Email colocado na fila para envio").build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Falha ao colocar o email na fila").build();
        }
    }
}
