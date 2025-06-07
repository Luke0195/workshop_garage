package br.com.lucas.santos.workshop.business.service;

import br.com.lucas.santos.workshop.business.contractors.repositories.client.*;
import br.com.lucas.santos.workshop.domain.dto.request.ClientRequestDto;
import br.com.lucas.santos.workshop.domain.dto.response.ClientResponseDto;
import br.com.lucas.santos.workshop.domain.entities.Client;

import br.com.lucas.santos.workshop.domain.features.client.*;
import br.com.lucas.santos.workshop.infrastructure.adapters.db.ClientRepository;
import br.com.lucas.santos.workshop.infrastructure.exceptions.ResourceAlreadyExistsException;
import br.com.lucas.santos.workshop.infrastructure.exceptions.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
public class ClientService implements AddClient, LoadClient, LoadClientById, RemoveClient, UpdateClient {

    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository){
        this.clientRepository = clientRepository;

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ClientResponseDto add(ClientRequestDto clientRequestDto) {
        Optional<Client> clientAlreadyExists = clientRepository.loadClientByEmail(clientRequestDto.email());
        if(clientAlreadyExists.isPresent()) throw new ResourceAlreadyExistsException("This email is already taken!");
        Optional<Client> findClientByCpf = clientRepository.loadClientByCode(clientRequestDto.cpf());
        if(findClientByCpf.isPresent()) throw new ResourceAlreadyExistsException("This cpf already exists");
        Client client = Client.makeClient(clientRequestDto);
        client = this.clientRepository.add(client);
        return ClientResponseDto.makeClientResponseDto(client);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ClientResponseDto> loadClients(Pageable pageable) {
        Page<Client> clientPage = clientRepository.loadClient(pageable);
        return clientPage.map(ClientResponseDto::makeClientResponseDto);
    }

    @Override
    @Transactional(readOnly = true)
    public ClientResponseDto load(Long id) {
        Client client = clientRepository.loadById(id).orElseThrow(() -> new ResourceNotFoundException("This client id was not found"));
        return ClientResponseDto.makeClientResponseDto(client);
    }

    @Override
    public void remove(Long id) {
        Client client = clientRepository.loadById(id).orElseThrow(() -> new ResourceNotFoundException("Client id not found"));
        clientRepository.deleteById(client.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ClientResponseDto update(Long id, ClientRequestDto clientRequestDto) {
        Client client = clientRepository.loadById(id).orElseThrow(() -> new ResourceNotFoundException("Client id not found"));
        return ClientResponseDto.makeClientResponseDto(client);
    }
}
