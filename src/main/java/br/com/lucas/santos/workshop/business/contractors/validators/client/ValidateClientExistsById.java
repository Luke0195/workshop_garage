package br.com.lucas.santos.workshop.business.contractors.validators.client;

import br.com.lucas.santos.workshop.domain.entities.Client;

public interface ValidateClientExistsById {

    void validate(Client client);
}
