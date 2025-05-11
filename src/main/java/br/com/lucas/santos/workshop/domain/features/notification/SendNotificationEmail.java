package br.com.lucas.santos.workshop.domain.features.notification;

import br.com.lucas.santos.workshop.domain.dto.request.EmailNotificationRequestDto;

public interface SendNotificationEmail {
    void perform(EmailNotificationRequestDto emailNotificationRequestDto);
}
