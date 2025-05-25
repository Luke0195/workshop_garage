package br.com.lucas.santos.workshop.bunisses.service;

import br.com.lucas.santos.workshop.bunisses.contractors.repositories.client.LoadClientByEmail;
import br.com.lucas.santos.workshop.domain.dto.request.ClientRequestDto;
import br.com.lucas.santos.workshop.domain.dto.response.ClientResponseDto;
import br.com.lucas.santos.workshop.domain.entities.Client;
import br.com.lucas.santos.workshop.domain.features.client.AddClient;
import br.com.lucas.santos.workshop.infrastructure.exceptions.ResourceAlreadyExistsException;
import br.com.lucas.santos.workshop.infrastructure.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientService implements AddClient {

    private final LoadClientByEmail loadClientByEmail;

    public ClientService(LoadClientByEmail loadClientByEmail){
        this.loadClientByEmail = loadClientByEmail;
    }

    @Override
    public ClientResponseDto add(ClientRequestDto clientRequestDto) {
        Client findClientByEmail = loadClientByEmail.loadClientByEmail(clientRequestDto.email()).orElseThrow(() -> new ResourceAlreadyExistsException("This email is already taken"));
        return null;
    }
}
