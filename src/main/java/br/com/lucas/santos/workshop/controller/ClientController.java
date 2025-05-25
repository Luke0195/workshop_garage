package br.com.lucas.santos.workshop.controller;


import br.com.lucas.santos.workshop.domain.dto.request.ClientRequestDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClientController {


    @PostMapping(value = "/client")
    public ResponseEntity<?> handleAddUser(@Valid @RequestBody ClientRequestDto clientRequestDto){
        return null;
    }
}
