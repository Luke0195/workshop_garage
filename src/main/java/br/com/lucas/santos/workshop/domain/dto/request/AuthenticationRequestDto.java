package br.com.lucas.santos.workshop.domain.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record AuthenticationRequestDto(
        @NotEmpty(message = "The field email must be required")
        @Email(message = "Please provided valid email")
        String email,
        @NotEmpty(message = "The field password must be required")
        @Size(min = 3, max = 20, message = "The field password must have between 3 and 20 characters")
        String password
) {
}
