package br.com.lucas.santos.workshop.domain.dto.request;

public record EmailNotificationRequestDto(
    String email, String suject, String text) {
}
