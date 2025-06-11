package br.com.lucas.santos.workshop.business.service;

import br.com.lucas.santos.workshop.business.contractors.repositories.client.*;
import br.com.lucas.santos.workshop.domain.dto.request.ClientRequestDto;
import br.com.lucas.santos.workshop.domain.dto.response.ClientResponseDto;
import br.com.lucas.santos.workshop.domain.entities.Client;

import br.com.lucas.santos.workshop.domain.features.client.*;

import br.com.lucas.santos.workshop.infrastructure.exceptions.ResourceAlreadyExistsException;
import br.com.lucas.santos.workshop.infrastructure.exceptions.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
public class ClientService{

    private final DbLoadClientByEmail dbLoadClientByEmail;
    private final DbLoadClientByCode dbLoadClientByCode;
    private final DbAddClient  dbAddClient;
    private final DbLoadClient dbLoadClient;
    private final DbLoadClientById dbLoadClientById;
    private final DbRemoveClientById dbRemoveClientById;
    private final DbUpdateClient dbUpdateClient;

    public ClientService(final DbLoadClientByEmail dbLoadClientByEmail,
                         final DbLoadClientByCode dbLoadClientByCode,
                         final DbAddClient dbAddClient,
                         final DbLoadClient dbLoadClient,
                         final DbLoadClientById dbLoadClientById,
                         final DbRemoveClientById dbRemoveClientById,
                         final DbUpdateClient dbUpdateClient
                         ){
        this.dbLoadClientByEmail = dbLoadClientByEmail;
        this.dbLoadClientByCode =  dbLoadClientByCode;
        this.dbAddClient = dbAddClient;
        this.dbLoadClient = dbLoadClient;
        this.dbLoadClientById = dbLoadClientById;
        this.dbRemoveClientById = dbRemoveClientById;
        this.dbUpdateClient = dbUpdateClient;
    }


    @Transactional(rollbackFor = Exception.class)
    public ClientResponseDto add(ClientRequestDto clientRequestDto) {
        Optional<Client> clientAlreadyExists = dbLoadClientByEmail.loadClientByEmail(clientRequestDto.email());
        if(clientAlreadyExists.isPresent()) throw new ResourceAlreadyExistsException("This email is already taken!");
        Optional<Client> findClientByCpf = dbLoadClientByCode.loadClientByCode(clientRequestDto.cpf());
        if(findClientByCpf.isPresent()) throw new ResourceAlreadyExistsException("This cpf already exists");
        Client client = Client.makeClient(clientRequestDto);
        client = this.dbAddClient.add(client);
        return ClientResponseDto.makeClientResponseDto(client);
    }

    @Transactional(readOnly = true)
    public Page<ClientResponseDto> loadClients(Pageable pageable) {
        Page<Client> clientPage = dbLoadClient.loadClient(pageable);
        return clientPage.map(ClientResponseDto::makeClientResponseDto);
    }

    @Transactional(readOnly = true)
    public ClientResponseDto load(Long id) {
        Client client = dbLoadClientById.loadById(id).orElseThrow(() -> new ResourceNotFoundException("This client id was not found"));
        return ClientResponseDto.makeClientResponseDto(client);
    }

    public void remove(Long id) {
        Client client = dbLoadClientById.loadById(id).orElseThrow(() -> new ResourceNotFoundException("Client id not found"));
        dbRemoveClientById.deleteById(client.getId());
    }

    @Transactional(rollbackFor = Exception.class)
    public ClientResponseDto update(Long id, ClientRequestDto clientRequestDto) {
        Client client = dbLoadClientById.loadById(id).orElseThrow(() -> new ResourceNotFoundException("Client id not found"));
        return ClientResponseDto.makeClientResponseDto(client);
    }
}
