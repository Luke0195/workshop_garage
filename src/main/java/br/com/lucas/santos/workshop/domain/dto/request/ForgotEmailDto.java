package br.com.lucas.santos.workshop.domain.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record ForgotEmailDto(
    @NotEmpty(message = "The field e-mail must be required")
    @Email(message = "Please provided a valid e-mail")
    String email) {
}
