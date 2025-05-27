package br.com.lucas.santos.workshop.bunisses.contractors.repositories.client;

import br.com.lucas.santos.workshop.domain.entities.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DbLoadClient {
    Page<Client> loadClient(Pageable pageable);
}
