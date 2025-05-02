package br.com.lucas.santos.workshop.bunisses.service;

import br.com.lucas.santos.workshop.domain.dto.request.AuthenticationRequestDto;
import br.com.lucas.santos.workshop.infrastructure.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;


@ExtendWith(MockitoExtension.class)
@ActiveProfiles("dev")
class AuthenticationServiceTest {

    private AuthenticationRequestDto authenticationRequestDto;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthenticationService authenticationService;


    @DisplayName("authenticatie should calls findByEmail when valid e-mail is provided")
    @Test
    void authenticateShouldCallFindByEmailWhenValidEmailIsProvided(){

    }


    void setupValues(){
        this.authenticationRequestDto =
    }



}