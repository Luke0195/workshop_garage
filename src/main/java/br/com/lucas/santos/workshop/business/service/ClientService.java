package br.com.lucas.santos.workshop.business.service;

import br.com.lucas.santos.workshop.business.contractors.repositories.client.*;
import br.com.lucas.santos.workshop.domain.dto.request.ClientRequestDto;
import br.com.lucas.santos.workshop.domain.dto.response.ClientResponseDto;
import br.com.lucas.santos.workshop.domain.entities.Client;
import br.com.lucas.santos.workshop.infrastructure.exceptions.ResourceAlreadyExistsException;
import br.com.lucas.santos.workshop.infrastructure.exceptions.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;


@Service
public class ClientService{

    private final LoadClientByEmailRepository loadClientByEmailRepository;
    private final LoadClientByCodeRepository dbLoadClientByCode;
    private final AddClientRepository addClientRepository;
    private final LoadClientRepository loadClientRepository;
    private final DbLoadClientById dbLoadClientById;
    private final RemoveClientRepository removeClientRepository;
    private final UpdateClientRepository dbUpdateClient;

    public ClientService(final LoadClientByEmailRepository loadClientByEmailRepository,
                         final LoadClientByCodeRepository dbLoadClientByCode,
                         final AddClientRepository addClientRepository,
                         final LoadClientRepository loadClientRepository,
                         final DbLoadClientById dbLoadClientById,
                         final RemoveClientRepository removeClientRepository,
                         final UpdateClientRepository dbUpdateClient
                         ){
        this.loadClientByEmailRepository = loadClientByEmailRepository;
        this.dbLoadClientByCode =  dbLoadClientByCode;
        this.addClientRepository = addClientRepository;
        this.loadClientRepository = loadClientRepository;
        this.dbLoadClientById = dbLoadClientById;
        this.removeClientRepository = removeClientRepository;
        this.dbUpdateClient = dbUpdateClient;
    }


    @Transactional(rollbackFor = Exception.class)
    public ClientResponseDto add(ClientRequestDto clientRequestDto) {
        Optional<Client> clientAlreadyExists = loadClientByEmailRepository.loadClientByEmail(clientRequestDto.email());
        if(clientAlreadyExists.isPresent()) throw new ResourceAlreadyExistsException("This email is already taken!");
        Client findClientByCpf = dbLoadClientByCode.loadClientByCode(clientRequestDto.cpf());
        if(Objects.isNull(findClientByCpf)) throw new ResourceAlreadyExistsException("This cpf already exists");
        Client client = Client.makeClient(clientRequestDto);
        client = this.addClientRepository.add(client);
        return ClientResponseDto.makeClientResponseDto(client);
    }

    @Transactional(readOnly = true)
    public Page<ClientResponseDto> loadClients(Pageable pageable) {
        Page<Client> clientPage = loadClientRepository.loadClient(pageable);
        return clientPage.map(ClientResponseDto::makeClientResponseDto);
    }

    @Transactional(readOnly = true)
    public ClientResponseDto load(Long id) {
        Client client = dbLoadClientById.loadById(id);
        return ClientResponseDto.makeClientResponseDto(client);
    }

    public void remove(Long id) {
        Client client = dbLoadClientById.loadById(id);
        if(Objects.isNull(client)) throw new ResourceNotFoundException("Client id not found");
        removeClientRepository.deleteById(client.getId());
    }

    @Transactional(rollbackFor = Exception.class)
    public ClientResponseDto update(Long id, ClientRequestDto clientRequestDto) {
        Client client = dbLoadClientById.loadById(id);
        if(Objects.isNull(client)) throw new ResourceNotFoundException("Client id not found");
        client = dbUpdateClient.update(client);
        return ClientResponseDto.makeClientResponseDto(client);
    }
}
