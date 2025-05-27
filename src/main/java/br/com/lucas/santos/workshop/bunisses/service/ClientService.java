package br.com.lucas.santos.workshop.bunisses.service;

import br.com.lucas.santos.workshop.bunisses.contractors.repositories.client.DbLoadClient;
import br.com.lucas.santos.workshop.bunisses.contractors.repositories.client.LoadClientByCode;
import br.com.lucas.santos.workshop.bunisses.contractors.repositories.client.LoadClientByEmail;
import br.com.lucas.santos.workshop.bunisses.contractors.repositories.client.SaveClient;
import br.com.lucas.santos.workshop.domain.dto.request.ClientRequestDto;
import br.com.lucas.santos.workshop.domain.dto.response.ClientResponseDto;
import br.com.lucas.santos.workshop.domain.dto.response.UserResponseDto;
import br.com.lucas.santos.workshop.domain.entities.Client;

import br.com.lucas.santos.workshop.domain.features.client.AddClient;
import br.com.lucas.santos.workshop.domain.features.client.LoadClient;
import br.com.lucas.santos.workshop.infrastructure.exceptions.ResourceAlreadyExistsException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
public class ClientService implements AddClient, LoadClient {

    private final LoadClientByEmail loadClientByEmail;
    private final LoadClientByCode loadClientByCode;
    private final SaveClient saveClient;
    private final DbLoadClient dbLoadClient;

    public ClientService(LoadClientByEmail loadClientByEmail, LoadClientByCode loadClientByCode, SaveClient saveClient, DbLoadClient dbLoadClient){
        this.loadClientByEmail = loadClientByEmail;
        this.loadClientByCode = loadClientByCode;
        this.saveClient = saveClient;
        this.dbLoadClient = dbLoadClient;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ClientResponseDto add(ClientRequestDto clientRequestDto) {
        Optional<Client> clientAlreadyExists = loadClientByEmail.loadClientByEmail(clientRequestDto.email());
        if(clientAlreadyExists.isPresent()) throw new ResourceAlreadyExistsException("This email is already taken!");
        Optional<Client> findClientByCpf = loadClientByCode.loadClientByCode(clientRequestDto.cpf());
        if(findClientByCpf.isPresent()) throw new ResourceAlreadyExistsException("This cpf already exists");
        Client client = Client.makeClient(clientRequestDto);
        client = this.saveClient.add(client);
        return ClientResponseDto.makeClientResponseDto(client);
    }

    @Override
    public Page<ClientResponseDto> loadClients(Pageable pageable) {
        Page<Client> clientPage = dbLoadClient.loadClient(pageable);
        return clientPage.map(ClientResponseDto::makeClientResponseDto);
    }
}
