package br.com.lucas.santos.workshop.controller;

import br.com.lucas.santos.workshop.domain.dto.request.AuthenticationRequestDto;
import br.com.lucas.santos.workshop.domain.dto.response.AuthenticationResponseDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {


    @PostMapping(value = "/signin")
    public ResponseEntity<AuthenticationResponseDto> handleAuthentication(@Valid @RequestBody AuthenticationRequestDto
                                                                                      authenticationRequestDto){
        return null;
    }
}
