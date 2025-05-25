package br.com.lucas.santos.workshop.domain.dto.request;

import br.com.lucas.santos.workshop.domain.entities.enums.ClientStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.br.CPF;

public record ClientRequestDto(
    @NotEmpty(message = "The field name must be required")
    String name,
    @NotNull(message = "The field phone cannot be null")
    String phone,
    @NotEmpty(message = "The field email must be required")
    @Email(message = "Please provided a valid email")
    String email,
    @NotEmpty(message = "The field cpf must  be required")
    @CPF(message = "Please provided a valid cpf")
    String cpf,
    @NotEmpty(message = "The field zipcode must be required")
    @Pattern(regexp = "\\\\d{5}-\\\\d{3}", message = "Please provided a valid zipcode")
    String zipcode,
    @NotEmpty(message = "The field address must be required")
    String address,
    @NotNull(message = "The field number cannot be null")
    Integer number,
    @NotEmpty(message = "The field complement must be required")
    String complement,
    @NotNull(message = "The field status cannot be null")
    ClientStatus status) {
}
