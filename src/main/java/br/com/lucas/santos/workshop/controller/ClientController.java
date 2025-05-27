package br.com.lucas.santos.workshop.controller;


import br.com.lucas.santos.workshop.bunisses.service.ClientService;
import br.com.lucas.santos.workshop.domain.dto.request.ClientRequestDto;
import br.com.lucas.santos.workshop.domain.dto.response.ClientResponseDto;
import br.com.lucas.santos.workshop.helpers.http.HttpHelper;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService){
        this.clientService = clientService;
    }

    @PostMapping(value = "/client")
    public ResponseEntity<ClientResponseDto> handleAddUser(@Valid @RequestBody ClientRequestDto clientRequestDto){
        ClientResponseDto clientResponseDto = clientService.add(clientRequestDto);
        return HttpHelper.created(clientResponseDto, clientResponseDto.id());
    }
}
