package br.com.lucas.santos.workshop.business.contractors.repositories.client;

import br.com.lucas.santos.workshop.domain.entities.Client;

public interface LoadClientByCodeRepository {
    Client loadClientByCode(String code);
}
