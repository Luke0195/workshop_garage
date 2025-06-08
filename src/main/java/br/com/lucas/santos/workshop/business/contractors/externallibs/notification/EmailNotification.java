package br.com.lucas.santos.workshop.business.contractors.externallibs.notification;

import br.com.lucas.santos.workshop.domain.dto.request.EmailNotificationRequestDto;
import jakarta.mail.MessagingException;

public interface EmailNotification {

    void sendNotification(EmailNotificationRequestDto emailNotificationRequestDto) throws MessagingException;
}
