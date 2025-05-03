package br.com.lucas.santos.workshop.bunisses.service;

import br.com.lucas.santos.workshop.domain.dto.request.AuthenticationRequestDto;
import br.com.lucas.santos.workshop.domain.dto.response.AuthenticationResponseDto;
import br.com.lucas.santos.workshop.domain.entities.User;
import br.com.lucas.santos.workshop.factories.AuthenticationFactory;
import br.com.lucas.santos.workshop.factories.UserFactory;
import br.com.lucas.santos.workshop.infrastructure.adapters.cryphtography.BcryptAdapter;
import br.com.lucas.santos.workshop.infrastructure.adapters.cryphtography.JwtAdapter;
import br.com.lucas.santos.workshop.infrastructure.adapters.db.UserRepository;
import br.com.lucas.santos.workshop.infrastructure.exceptions.InvalidCredentialsException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("dev")
class AuthenticationServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthenticationService sut;

    @Mock
    private BcryptAdapter bcryptAdapter;
    @Mock
    private JwtAdapter jwtAdapter;

    private User user;
    private AuthenticationRequestDto authenticationRequestDto;


    @BeforeEach
    void setup(){
        setupValues();
    }



    @DisplayName("authenticate should calls UserRepository findByEmail when valid e-mail is provided")
    @Test
    void authenticateShouldCallFindByEmailWhenValidEmailIsProvided(){
        String validId = "1cc1d929-1373-4c79-ab13-50d743c25146";
        Mockito.when(userRepository.loadUserByEmail(this.authenticationRequestDto.email())).thenReturn(user);
        Mockito.when(bcryptAdapter.compare(this.authenticationRequestDto.password(), "any_password")).thenReturn(true);
        Mockito.when(jwtAdapter.generateToken(validId)).thenReturn(new AuthenticationResponseDto("any_token"
            , 3600L));
        sut.authenticate(this.authenticationRequestDto);
        Mockito.verify(userRepository).loadUserByEmail(this.authenticationRequestDto.email());
    }

    @DisplayName("authenticate should throws InvalidCredentialsException when an invalid email is provided")
    @Test
    void authenticateShouldThrowsInvalidCredentialsExceptionWhenAnInvalidEmailIsProvided(){
        Mockito.when(userRepository.loadUserByEmail(this.authenticationRequestDto.email())).thenThrow(InvalidCredentialsException.class);
        Assertions.assertThrows(InvalidCredentialsException.class, () -> {
            sut.authenticate(authenticationRequestDto);
        });
    }

    @DisplayName("authenticate should throws InvalidCredentialsException when password does not match")
    @Test
    void authenticateShouldThrowsInvalidCredentialsExceptionWhenPasswordDoesNotWatch(){
        Mockito.when(userRepository.loadUserByEmail(this.authenticationRequestDto.email())).thenReturn(user);
        Assertions.assertThrows(InvalidCredentialsException.class, () -> {
            sut.authenticate(authenticationRequestDto);
        });
    }




    void setupValues(){
        this.authenticationRequestDto = AuthenticationFactory.makeAuthenticationRequestDto();
        this.user = UserFactory.makeUser(UserFactory.makeUserRequestDto());
    }



}