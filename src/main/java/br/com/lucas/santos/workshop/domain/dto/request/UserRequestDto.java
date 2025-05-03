package br.com.lucas.santos.workshop.domain.dto.request;


import jakarta.validation.constraints.*;

import java.util.Set;

public record UserRequestDto(
        @NotEmpty(message = "The field name must be required")
        String name,
        @NotEmpty(message = "The field email must be required")
        @Email(message = "Please provided a valid email")
        String email,
        @NotEmpty(message =  "The field password must be required")
        @Size(min = 3, max = 20, message = "The field password must have between 3 and 20 characters")
        String password,
        @NotNull(message = "The field roles must be required")
        Set<String> roles) {
}
