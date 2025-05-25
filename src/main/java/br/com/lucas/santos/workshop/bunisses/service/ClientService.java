package br.com.lucas.santos.workshop.bunisses.service;

import br.com.lucas.santos.workshop.bunisses.contractors.repositories.client.LoadClientByCode;
import br.com.lucas.santos.workshop.bunisses.contractors.repositories.client.LoadClientByEmail;
import br.com.lucas.santos.workshop.bunisses.contractors.repositories.client.SaveClient;
import br.com.lucas.santos.workshop.domain.dto.request.ClientRequestDto;
import br.com.lucas.santos.workshop.domain.dto.response.ClientResponseDto;
import br.com.lucas.santos.workshop.domain.dto.response.UserResponseDto;
import br.com.lucas.santos.workshop.domain.entities.Client;
import br.com.lucas.santos.workshop.domain.entities.enums.ClientStatus;

import br.com.lucas.santos.workshop.domain.features.client.AddClient;
import br.com.lucas.santos.workshop.infrastructure.exceptions.ResourceAlreadyExistsException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class ClientService implements AddClient {

    private final LoadClientByEmail loadClientByEmail;
    private final LoadClientByCode loadClientByCode;
    private final SaveClient saveClient;

    public ClientService(LoadClientByEmail loadClientByEmail, LoadClientByCode loadClientByCode, SaveClient saveClient){
        this.loadClientByEmail = loadClientByEmail;
        this.loadClientByCode = loadClientByCode;
        this.saveClient = saveClient;
    }

    @Override
    public ClientResponseDto add(ClientRequestDto clientRequestDto) {
        Optional<Client> clientAlreadyExists = loadClientByEmail.loadClientByEmail(clientRequestDto.email());
        if(clientAlreadyExists.isPresent()) throw new ResourceAlreadyExistsException("This email is already taken!");
        Optional<Client> findClientByCpf = loadClientByCode.loadClientByCode(clientRequestDto.cpf());
        if(findClientByCpf.isPresent()) throw new ResourceAlreadyExistsException("This cpf already exists");
        Client client = Client.builder().name(clientRequestDto.name())
            .phone(clientRequestDto.phone()).email(clientRequestDto.email())
            .cpf(clientRequestDto.cpf()).zipcode(clientRequestDto.zipcode())
            .address(clientRequestDto.address()).number(clientRequestDto.number())
            .complement(clientRequestDto.complement()).status(ClientStatus.ACTIVE).build();
        client = this.saveClient.add(client);
        return new ClientResponseDto(client.getId(), client.getName(), client.getPhone(), client.getEmail(),client.getCpf(),
            client.getZipcode(), client.getAddress(), client.getNumber(), client.getComplement(), client.getStatus(),
            client.getCreatedAt(), client.getUpdateAt());
    }
}
