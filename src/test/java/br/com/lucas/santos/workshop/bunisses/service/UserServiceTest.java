package br.com.lucas.santos.workshop.bunisses.service;


import br.com.lucas.santos.workshop.bunisses.protocols.Encrypter;
import br.com.lucas.santos.workshop.dto.request.UserRequestDto;
import br.com.lucas.santos.workshop.factories.UserFactory;
import br.com.lucas.santos.workshop.infrastructure.exceptions.ResourceAlreadyExistsException;
import br.com.lucas.santos.workshop.infrastructure.exceptions.ResourceNotFoundException;
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
}