package br.com.lucas.santos.workshop.bunisses.service;


import br.com.lucas.santos.workshop.bunisses.protocols.cryptography.Encrypter;
import br.com.lucas.santos.workshop.domain.entities.User;
import br.com.lucas.santos.workshop.domain.dto.request.UserRequestDto;
import br.com.lucas.santos.workshop.domain.dto.response.UserResponseDto;
import br.com.lucas.santos.workshop.factories.UserFactory;
import br.com.lucas.santos.workshop.infrastructure.adapters.db.UserRepository;
import br.com.lucas.santos.workshop.infrastructure.exceptions.ResourceAlreadyExistsException;
import br.com.lucas.santos.workshop.infrastructure.exceptions.ServerError;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;


@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    private UserRequestDto userRequestDto;

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private Encrypter encrypter;

    private User user;

    @BeforeEach
    void setup(){
        this.userRequestDto = UserFactory.makeUserRequestDto();
        this.user = UserFactory.makeUser(UserFactory.makeUserRequestDto());
    }
    @DisplayName("add should throws ResourceAlreadyExistsException if user email already exists")
    @Test
    void addShouldThrowResourceAlreadyExistsExceptionIfUserEmailAlreadyExists() {
        Mockito.when(userRepository.loadUserByEmail(userRequestDto.email())).thenReturn(user); // Simula que já existe
        Assertions.assertThrows(ResourceAlreadyExistsException.class, () -> {
            userService.add(userRequestDto);
        });
    }
    @DisplayName("add should throws ServerError if bcrypt returns null ")
    @Test
    void addShouldThrowsServerErrorIfBcryptReturnsNull(){
        Mockito.when(userRepository.loadUserByEmail(userRequestDto.email())).thenReturn(null);
        Mockito.when(encrypter.encrypt(userRequestDto.password())).thenReturn(null);
        Assertions.assertThrows(ServerError.class, () -> {
            userService.add(userRequestDto);
        });
    }



    @DisplayName("add should returns an user when valid data is provided")
    @Test
    void addShouldReturnsAnUserWhenValidDataIsProvided(){
        UUID validId = UUID.fromString("1cc1d929-1373-4c79-ab13-50d743c25146");
        User user = UserFactory.makeUser(UserFactory.makeUserRequestDto());
        user.setId(validId);
        user.setPassword("hashed_password");
        Mockito.when(userRepository.loadUserByEmail(Mockito.anyString())).thenReturn(null);
        Mockito.when(encrypter.encrypt(Mockito.anyString())).thenReturn("hashed_password");
        Mockito.when(userRepository.add(Mockito.any())).thenReturn(user);
        UserRequestDto userRequestDto = UserFactory.makeUserRequestDto();
        UserResponseDto userResponseDto =  userService.add(userRequestDto);
        Assertions.assertEquals(validId, userResponseDto.id());
        Assertions.assertEquals("any_name", userResponseDto.name());
        Assertions.assertEquals("any_mail@mail.com", userResponseDto.email());
        Assertions.assertEquals("hashed_password", userResponseDto.password());
        Assertions.assertNotNull(userResponseDto.createdAt());

    }


}