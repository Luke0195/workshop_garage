package br.com.lucas.santos.workshop.domain.dto.request;

import jakarta.validation.constraints.NotEmpty;

public record ResetPasswordDto(
    @NotEmpty(message = "The field password must be required")
    String password) {
}
