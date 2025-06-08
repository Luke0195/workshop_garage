package br.com.lucas.santos.workshop.business.contractors.repositories.client;

import br.com.lucas.santos.workshop.domain.entities.Client;

import java.util.Optional;

public interface DbLoadClientByCode {
    Optional<Client> loadClientByCode(String code);
}
