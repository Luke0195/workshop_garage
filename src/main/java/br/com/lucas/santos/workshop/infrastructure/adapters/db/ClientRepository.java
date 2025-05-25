package br.com.lucas.santos.workshop.infrastructure.adapters.db;

import br.com.lucas.santos.workshop.bunisses.contractors.repositories.client.LoadClientByEmail;
import br.com.lucas.santos.workshop.domain.entities.Client;
import br.com.lucas.santos.workshop.infrastructure.repository.ClientJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ClientRepository implements LoadClientByEmail {

    private final ClientJpaRepository clientJpaRepository;

   public ClientRepository(ClientJpaRepository clientJpaRepository){
       this.clientJpaRepository = clientJpaRepository;
   }

    @Override
    public Optional<Client> loadClientByEmail(String email) {
        return clientJpaRepository.findByEmail(email);
    }
}
