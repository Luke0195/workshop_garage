package br.com.lucas.santos.workshop.infrastructure.adapters.db;

import br.com.lucas.santos.workshop.business.contractors.repositories.client.*;
import br.com.lucas.santos.workshop.domain.entities.Client;
import br.com.lucas.santos.workshop.infrastructure.repository.ClientJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ClientRepository implements LoadClientByEmailRepository, LoadClientByCodeRepository, AddClientRepository, LoadClientRepository,
    DbLoadClientById, RemoveClientRepository, UpdateClientRepository {

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
    public Client loadClientByCode(String code) {
        return clientJpaRepository.findByCpf(code).orElse(null);
    }

    @Override
    public Page<Client> loadClient(Pageable pageable) {
       return clientJpaRepository.findAll(pageable);
    }

    @Override
    public Client loadById(Long id) {
        return clientJpaRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteById(Long id) {
       clientJpaRepository.deleteById(id);
    }

    @Override
    public Client update(Client client) {
       return clientJpaRepository.save(client);
    }
}
