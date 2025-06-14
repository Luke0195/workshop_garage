package br.com.lucas.santos.workshop.business.validators;

import br.com.lucas.santos.workshop.business.contractors.validators.client.ValidateClientExistsById;
import br.com.lucas.santos.workshop.domain.entities.Client;
import br.com.lucas.santos.workshop.infrastructure.exceptions.ValidationException;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ClientValidator implements ValidateClientExistsById {

    @Override
    public void validate(Client client) {
        if(Objects.isNull(client.getId())) throw new ValidationException("This owner_id was not found");
    }
}
