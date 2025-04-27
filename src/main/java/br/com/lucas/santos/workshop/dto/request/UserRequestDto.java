package br.com.lucas.santos.workshop.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

public record UserRequestDto(
        @NotEmpty(message = "The field name must be required")
        String name,
        @NotEmpty(message = "The field email must be required")
        @Email(message = "Please provided a valid email")
        String email,
        @NotEmpty(message =  "The field password must be required")
        @Min(value = 3, message = "The field password must be at least 3 characters")
        @Max(value = 20, message = "The field password cannot have more that 20 characters")
        String password) {
}
