package br.com.lucas.santos.workshop.business.service;


import br.com.lucas.santos.workshop.domain.entities.User;
import br.com.lucas.santos.workshop.domain.dto.request.UserRequestDto;
import br.com.lucas.santos.workshop.factories.UserFactory;
import br.com.lucas.santos.workshop.infrastructure.adapters.cryphtography.BcryptAdapter;
import br.com.lucas.santos.workshop.infrastructure.adapters.db.RoleRepository;
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
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;
import java.util.Optional;


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

    @DisplayName("add should throws ResourceAlreadyExistsException if user email already exists")
    @Test
    void addShouldThrowsResourceAlreadyExistsExceptionIfUserEmailAlreadyExists(){
        Mockito.when(userRepository.loadUserByEmail(Mockito.any())).thenReturn(Optional.of(user));
        Assertions.assertThrows(ResourceAlreadyExistsException.class, () -> {
            userService.add(userRequestDto);
        });
    }

    @DisplayName("add should throws ServerError if encrypter returns null")
    @Test
    void addShouldThrowsServerErrorIfEncrypterReturnsNull(){
        Mockito.lenient().when(userRepository.loadUserByEmail(Mockito.any())).thenReturn(Optional.empty());
        Mockito.lenient().when(encrypter.encrypt(Mockito.any())).thenReturn(null);
        Assertions.assertThrows(ServerError.class, () -> {
            userService.add(userRequestDto);
        });
    }





}