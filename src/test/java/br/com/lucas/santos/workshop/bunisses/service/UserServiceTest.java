package br.com.lucas.santos.workshop.bunisses.service;


import br.com.lucas.santos.workshop.bunisses.protocols.cryphography.Encrypter;
import br.com.lucas.santos.workshop.domain.entities.User;
import br.com.lucas.santos.workshop.domain.dto.request.UserRequestDto;
import br.com.lucas.santos.workshop.domain.dto.response.UserResponseDto;
import br.com.lucas.santos.workshop.factories.UserFactory;
import br.com.lucas.santos.workshop.infrastructure.exceptions.ResourceAlreadyExistsException;
import br.com.lucas.santos.workshop.infrastructure.exceptions.ServerError;
import br.com.lucas.santos.workshop.infrastructure.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
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

    @DisplayName("add should throws ResourceAlreadyExistsException if user email does not exists")
    @Test
    void addShouldThrowsResourceAlreadyExistsExceptionIfUserEmailDoesNotExists(){
        Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenThrow(ResourceAlreadyExistsException.class);
        Assertions.assertThrows(ResourceAlreadyExistsException.class, () -> {
            userService.add(this.userRequestDto);
        });
    }

    @DisplayName("add should call findByEmail with correct value")
    @Test
    void addShouldCallFindByEmailWithCorrectEmail(){
         Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());
         Mockito.when(encrypter.encrypt(Mockito.anyString())).thenReturn("hashed_password");
         Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);
         userService.add(userRequestDto);
         Mockito.verify(userRepository).findByEmail(userRequestDto.email());
    }

    @DisplayName("add should call encrypter with correct value")
    @Test
    void addShouldEncrypterWithCorrectValue(){
        Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());
        Mockito.when(encrypter.encrypt(Mockito.anyString())).thenReturn("hashed_password");
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);
        userService.add(userRequestDto);
        Mockito.verify(encrypter).encrypt(userRequestDto.password());
    }

    @DisplayName("add should throws ServerError if encrypter throws")
    @Test
    void addShouldThrowsServerErrorIfEncrypterThrows(){
        Mockito.when(encrypter.encrypt(Mockito.anyString())).thenReturn(null);
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
        Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());
        Mockito.when(encrypter.encrypt(Mockito.anyString())).thenReturn("hashed_password");
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);
        UserRequestDto userRequestDto = UserFactory.makeUserRequestDto();
        UserResponseDto userResponseDto =  userService.add(userRequestDto);
        Assertions.assertEquals(validId, userResponseDto.id());
        Assertions.assertEquals("any_name", userResponseDto.name());
        Assertions.assertEquals("any_mail@mail.com", userResponseDto.email());
        Assertions.assertEquals("hashed_password", userResponseDto.password());
        Assertions.assertNotNull(userResponseDto.createdAt());

    }

}