package br.com.lucas.santos.workshop.business.service;


import br.com.lucas.santos.workshop.domain.entities.User;
import br.com.lucas.santos.workshop.domain.dto.request.UserRequestDto;
import br.com.lucas.santos.workshop.factories.UserFactory;
import br.com.lucas.santos.workshop.infrastructure.adapters.cryphtography.BcryptAdapter;
import br.com.lucas.santos.workshop.infrastructure.adapters.db.RoleRepository;
import br.com.lucas.santos.workshop.infrastructure.adapters.db.UserRepository;
import br.com.lucas.santos.workshop.infrastructure.exceptions.ParseDataException;
import br.com.lucas.santos.workshop.infrastructure.exceptions.ServerError;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;


@ExtendWith(MockitoExtension.class)
@ActiveProfiles("dev")
class UserServiceTest {

    private UserRequestDto userRequestDto;

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;
    @Mock
    private BcryptAdapter encrypter;

    private User user;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
        this.userRequestDto = UserFactory.makeUserRequestDto();
        this.user = UserFactory.makeUser(UserFactory.makeUserRequestDto());
    }


    @DisplayName("add should throw ParseDataException if encrypter returns null")
    @Test
    void addShouldThrowParseDataExceptionIfEncrypterReturnsNull() {
        Mockito.lenient().when(userRepository.loadUserByEmail(Mockito.anyString())).thenReturn(null);
        Mockito.lenient().when(encrypter.encrypt(Mockito.anyString())).thenReturn(null);
        Assertions.assertThrows(ParseDataException.class, () -> {
            userService.add(userRequestDto);
        });
    }

}