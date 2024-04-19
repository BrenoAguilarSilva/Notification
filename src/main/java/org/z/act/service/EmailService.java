package org.z.act.service;

import io.quarkus.mailer.Mail;
import io.quarkus.mailer.reactive.ReactiveMailer;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.z.act.dto.NotificationEmail;
import org.z.act.resource.NotificationConsumer;

@ApplicationScoped
public class EmailService {
    private static final Logger LOG = LoggerFactory.getLogger(EmailService.class);

    @Inject
    ReactiveMailer reactiveMailer;

    public void sendEmail(NotificationEmail emailNotification) {
        try {
            Mail mail = Mail.withText(emailNotification.getRecipient(), emailNotification.getSubject(), emailNotification.getBody());

            if (emailNotification.getRecipientCC() != null) {
                for (String ccRecipient : emailNotification.getRecipientCC()) {
                    mail = mail.addCc(ccRecipient);
                }
            }
            reactiveMailer.send(mail)
                    .subscribe().with(
                            success -> LOG.info("E-mail enviado com sucesso para: " + emailNotification.getRecipient()),
                            failure -> LOG.error("Erro ao enviar e-mail: " + failure.getMessage()));

        } catch (Exception e) {
            LOG.error("Erro ao enviar e-mail: " + e.getMessage(), e);
        }
    }
}
