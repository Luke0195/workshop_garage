package br.com.lucas.santos.workshop.bunisses.service;

import br.com.lucas.santos.workshop.bunisses.contractors.externalibs.notification.EmailNotification;
import br.com.lucas.santos.workshop.domain.dto.request.EmailNotificationRequestDto;
import br.com.lucas.santos.workshop.domain.features.notification.SendNotificationEmail;
import org.springframework.stereotype.Service;

@Service
public class NotificationService implements SendNotificationEmail {

    private final EmailNotification emailNotification;

    public NotificationService(EmailNotification emailNotification){
        this.emailNotification = emailNotification;
    }

    @Override
    public void perform(EmailNotificationRequestDto emailNotificationRequestDto) {

    }
}
