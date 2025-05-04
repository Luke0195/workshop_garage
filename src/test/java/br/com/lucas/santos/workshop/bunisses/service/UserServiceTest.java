package br.com.lucas.santos.workshop.bunisses.service;


import br.com.lucas.santos.workshop.bunisses.protocols.cryptography.Encrypter;
import br.com.lucas.santos.workshop.domain.entities.Role;
import br.com.lucas.santos.workshop.domain.entities.User;
import br.com.lucas.santos.workshop.domain.dto.request.UserRequestDto;
import br.com.lucas.santos.workshop.domain.dto.response.UserResponseDto;
import br.com.lucas.santos.workshop.factories.UserFactory;
import br.com.lucas.santos.workshop.infrastructure.adapters.db.RoleRepository;
import br.com.lucas.santos.workshop.infrastructure.adapters.db.UserRepository;
import br.com.lucas.santos.workshop.infrastructure.exceptions.ResourceAlreadyExistsException;
import br.com.lucas.santos.workshop.infrastructure.exceptions.RoleNotFoundException;
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

    @DisplayName("add should throws RoleNotFoundException if no role was found")
    @Test
    void addShouldThrowsResourceNotFoundExceptionIfNoRoleWasFound(){
        Mockito.when(userRepository.loadUserByEmail(userRequestDto.email())).thenReturn(null);
        Mockito.when(encrypter.encrypt(userRequestDto.password())).thenReturn("hashed_password");
        Mockito.when(roleRepository.loadUserByRole(Mockito.anyString())).thenThrow(RoleNotFoundException.class);
        Assertions.assertThrows(RoleNotFoundException.class, () -> {
            userService.add(userRequestDto);
        });
    }

    @DisplayName("add should returns an UserResponseDto if save succeds")
    @Test
    void addShouldReturnsAnUserResponseDtoIfSaveSucceds(){
        Mockito.when(userRepository.loadUserByEmail(userRequestDto.email())).thenReturn(null);
        Mockito.when(encrypter.encrypt(userRequestDto.password())).thenReturn("hashed_password");
        Mockito.when(roleRepository.loadUserByRole(Mockito.anyString())).thenReturn(Role.builder().id(1L).name("ADMIN").build());
        Mockito.when(userRepository.add(Mockito.any())).thenReturn(user);
        UserResponseDto userResponseDto = userService.add(userRequestDto);
        Assertions.assertNotNull(userResponseDto);
        Assertions.assertNotNull(userResponseDto.id());
        Assertions.assertNotNull(userResponseDto.name());
    }





}