package br.com.lucas.santos.workshop.bunisses.service;


import br.com.lucas.santos.workshop.bunisses.protocols.Encrypter;
import br.com.lucas.santos.workshop.dto.request.UserRequestDto;
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

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    private UserRequestDto userRequestDto;

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private Encrypter encrypter;

    @BeforeEach
    void setup(){
        this.userRequestDto = UserFactory.makeUserRequestDto();
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
         userService.add(userRequestDto);
         Mockito.verify(userRepository).findByEmail(userRequestDto.email());
    }

    @DisplayName("add should call encrypter with correct value")
    @Test
    void addShouldEncrypterWithCorrectValue(){
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

}