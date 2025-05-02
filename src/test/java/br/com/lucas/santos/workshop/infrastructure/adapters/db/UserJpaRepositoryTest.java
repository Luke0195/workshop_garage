package br.com.lucas.santos.workshop.infrastructure.adapters.db;

import br.com.lucas.santos.workshop.domain.entities.User;
import br.com.lucas.santos.workshop.factories.UserFactory;
import br.com.lucas.santos.workshop.infrastructure.exceptions.ResourceNotFoundException;
import br.com.lucas.santos.workshop.infrastructure.exceptions.ServerError;
import br.com.lucas.santos.workshop.infrastructure.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("dev")
class UserJpaRepositoryTest {


    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserJpaRepository sut;

    private User user = UserFactory.makeUser(UserFactory.makeUserRequestDto());



    @DisplayName("loadUserByEmail should returns an user when valid e-mail is provided")
    @Test
    void loadUserByEmailShouldReturnsAnUserWhenValidEmailIsProvided(){
        Mockito.when(userRepository.findByEmail("any_mail@mail.com")).thenReturn(Optional.of(user));
        User user = sut.loadUserByEmail("any_mail@mail.com");
        Assertions.assertNotNull(user);
        Assertions.assertNotNull(user.getId());
    }

    @DisplayName("loadUserByEmail should throws ResourceNotFoundException if email does not exists")
    @Test
    void loadUserByEmailShouldThrowsResourceNotFoundExceptionIfEmailDoesNotExists(){
        Mockito.when(userRepository.findByEmail("invalid_mail@mail.com")).thenThrow(ResourceNotFoundException.class);
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            sut.loadUserByEmail("invalid_mail@mail.com");
        });
        Mockito.verify(userRepository).findByEmail("invalid_mail@mail.com");
    }

    @DisplayName("loadUserByEmail should returns ServerError if UserJpaRepository throws")
    @Test
    void loadUserByEmailShouldThrowsServerErrorIfUserJpaRepositoryThrows(){
        Mockito.when(userRepository.findByEmail("any_mail@mail.com")).thenThrow(new ServerError());
        Assertions.assertThrows(ServerError.class, () -> {
            sut.loadUserByEmail("any_mail@mail.com");
        });
        Mockito.verify(userRepository).findByEmail("any_mail@mail.com");
    }
}