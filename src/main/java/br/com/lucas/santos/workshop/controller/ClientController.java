package br.com.lucas.santos.workshop.controller;


import br.com.lucas.santos.workshop.bunisses.service.ClientService;
import br.com.lucas.santos.workshop.domain.dto.request.ClientRequestDto;
import br.com.lucas.santos.workshop.domain.dto.response.ClientResponseDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService){
        this.clientService = clientService;
    }

    @PostMapping(value = "/client")
    public ResponseEntity<ClientResponseDto> handleAddUser(@Valid @RequestBody ClientRequestDto clientRequestDto){
        ClientResponseDto clientResponseDto = clientService.add(clientRequestDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
            .buildAndExpand("/{id}", clientResponseDto.id()).toUri();
        return ResponseEntity.created(uri).body(clientResponseDto);
    }
}
