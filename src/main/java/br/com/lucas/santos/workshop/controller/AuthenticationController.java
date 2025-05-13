package br.com.lucas.santos.workshop.controller;

import br.com.lucas.santos.workshop.bunisses.service.AuthenticationService;
import br.com.lucas.santos.workshop.bunisses.service.ForgotPasswordService;
import br.com.lucas.santos.workshop.domain.dto.request.AuthenticationRequestDto;
import br.com.lucas.santos.workshop.domain.dto.request.ForgotPasswordDto;
import br.com.lucas.santos.workshop.domain.dto.response.AuthenticationResponseDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final ForgotPasswordService forgotPasswordService;

    public AuthenticationController(AuthenticationService authenticationService, ForgotPasswordService forgotPasswordService){
        this.authenticationService = authenticationService;
        this.forgotPasswordService = forgotPasswordService;
    }

    @PostMapping(value = "/signin")
    public ResponseEntity<AuthenticationResponseDto> handleAuthentication(@Valid @RequestBody AuthenticationRequestDto authenticationRequestDto){
      AuthenticationResponseDto authenticationResponseDto = authenticationService.authenticate(authenticationRequestDto);
      return ResponseEntity.status(HttpStatus.OK).body(authenticationResponseDto);
    }

    @PostMapping(value = "/forgotpassword")
    public ResponseEntity<Void> handleForgotPassword(@Valid @RequestBody ForgotPasswordDto forgotPasswordDto){
        forgotPasswordService.forgotUserPassword(forgotPasswordDto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
