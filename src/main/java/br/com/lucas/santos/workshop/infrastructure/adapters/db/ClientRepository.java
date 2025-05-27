package br.com.lucas.santos.workshop.infrastructure.adapters.db;

import br.com.lucas.santos.workshop.bunisses.contractors.repositories.client.DbLoadClient;
import br.com.lucas.santos.workshop.bunisses.contractors.repositories.client.SaveClient;
import br.com.lucas.santos.workshop.bunisses.contractors.repositories.client.LoadClientByCode;
import br.com.lucas.santos.workshop.bunisses.contractors.repositories.client.LoadClientByEmail;
import br.com.lucas.santos.workshop.domain.entities.Client;
import br.com.lucas.santos.workshop.infrastructure.repository.ClientJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ClientRepository implements LoadClientByEmail, LoadClientByCode, SaveClient, DbLoadClient {

    private final ClientJpaRepository clientJpaRepository;

   public ClientRepository(ClientJpaRepository clientJpaRepository){
       this.clientJpaRepository = clientJpaRepository;
   }

    @Override
    public Optional<Client> loadClientByEmail(String email) {
        return clientJpaRepository.findByEmail(email);
    }

    @Override
    public Client add(Client client) {
        return clientJpaRepository.save(client);
    }

    @Override
    public Optional<Client> loadClientByCode(String code) {
        return clientJpaRepository.findByCpf(code);
    }

    @Override
    public Page<Client> loadClient(Pageable pageable) {
       return clientJpaRepository.findAll(pageable);
    }
}
