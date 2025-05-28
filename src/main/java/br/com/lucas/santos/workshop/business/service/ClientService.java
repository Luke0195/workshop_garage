package br.com.lucas.santos.workshop.business.service;

import br.com.lucas.santos.workshop.business.contractors.repositories.client.*;
import br.com.lucas.santos.workshop.domain.dto.request.ClientRequestDto;
import br.com.lucas.santos.workshop.domain.dto.response.ClientResponseDto;
import br.com.lucas.santos.workshop.domain.entities.Client;

import br.com.lucas.santos.workshop.domain.features.client.AddClient;
import br.com.lucas.santos.workshop.domain.features.client.LoadClient;
import br.com.lucas.santos.workshop.domain.features.client.LoadClientById;
import br.com.lucas.santos.workshop.infrastructure.exceptions.ResourceAlreadyExistsException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
public class ClientService implements AddClient, LoadClient, LoadClientById {

    private final DbLoadClientById dbLoadClientById;
    private final DbLoadClientByEmail dbLoadClientByEmail;
    private final DbLoadClientByCode dbLoadClientByCode;
    private final DbAddClient dbAddClient;
    private final DbLoadClient dbLoadClient;

    public ClientService(
        DbLoadClientByEmail dbLoadClientByEmail,
        DbLoadClientByCode dbLoadClientByCode,
        DbAddClient dbAddClient,
        DbLoadClient dbLoadClient,
        DbLoadClientById dbLoadClientById
        ){
        this.dbLoadClientByEmail = dbLoadClientByEmail;
        this.dbLoadClientByCode = dbLoadClientByCode;
        this.dbAddClient = dbAddClient;
        this.dbLoadClient = dbLoadClient;
        this.dbLoadClientById = dbLoadClientById;
    }

    @Override
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

    @Override
    @Transactional(readOnly = true)
    public Page<ClientResponseDto> loadClients(Pageable pageable) {
        Page<Client> clientPage = dbLoadClient.loadClient(pageable);
        return clientPage.map(ClientResponseDto::makeClientResponseDto);
    }

    @Override
    @Transactional(readOnly = true)
    public ClientResponseDto load(Long id) {
        return null;
    }
}
