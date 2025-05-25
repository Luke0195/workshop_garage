package br.com.lucas.santos.workshop.bunisses.contractors.repositories.client;

import br.com.lucas.santos.workshop.domain.entities.Client;

import java.util.Optional;

public interface LoadClientByCode {
    Optional<Client> loadClientByCode(String code);
}
