package br.com.lucas.santos.workshop.domain.dto.request;

public record EmailNotificationRequestDto(
    String email, String subject, String text) {
}
