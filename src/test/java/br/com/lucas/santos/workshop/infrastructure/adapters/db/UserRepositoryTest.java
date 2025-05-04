package br.com.lucas.santos.workshop.infrastructure.adapters.db;

import br.com.lucas.santos.workshop.domain.entities.User;
import br.com.lucas.santos.workshop.factories.UserFactory;
import br.com.lucas.santos.workshop.infrastructure.exceptions.ResourceNotFoundException;
import br.com.lucas.santos.workshop.infrastructure.exceptions.RoleNotFoundException;
import br.com.lucas.santos.workshop.infrastructure.exceptions.ServerError;

import br.com.lucas.santos.workshop.infrastructure.repository.UserJpaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.Assert;

import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("dev")
class UserRepositoryTest {

    @Mock
    private UserJpaRepository userJpaRepository;

    @InjectMocks
    private UserRepository sut;
    private User user = UserFactory.makeUser(UserFactory.makeUserRequestDto());



    @DisplayName("loadUserByEmail should returns an user when valid e-mail is provided")
    @Test
    void loadUserByEmailShouldReturnsAnUserWhenValidEmailIsProvided(){
        Mockito.when(userJpaRepository.findByEmail(Mockito.any())).thenReturn(Optional.of(user));
        User user = sut.loadUserByEmail("any_mail@mail.com");
        Assertions.assertNotNull(user);
        Assertions.assertNotNull(user.getId());
    }

    @DisplayName("loadUserByEmail should throws RoleNotFoundException if email does not exists")
    @Test
    void loadUserByEmailShouldThrowsResourceNotFoundExceptionIfEmailDoesNotExists(){
        Mockito.when(userJpaRepository.findByEmail("invalid_mail@mail.com")).thenThrow(ResourceNotFoundException.class);
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            sut.loadUserByEmail("invalid_mail@mail.com");
        });
        Mockito.verify(userJpaRepository).findByEmail("invalid_mail@mail.com");
    }

    @DisplayName("loadUserByEmail should returns ServerError if UserJpaRepository throws")
    @Test
    void loadUserByEmailShouldThrowsServerErrorIfUserJpaRepositoryThrows(){
        Mockito.when(userJpaRepository.findByEmail("any_mail@mail.com")).thenThrow(new ServerError());
        Assertions.assertThrows(ServerError.class, () -> {
            sut.loadUserByEmail("any_mail@mail.com");
        });
        Mockito.verify(userJpaRepository).findByEmail("any_mail@mail.com");
    }
    @DisplayName("save should returns an user on success")
    @Test
    void saveShouldReturnsAnUserOnSuccess(){
        Mockito.when(userJpaRepository.save(user)).thenReturn(user);
        User user = userJpaRepository.save(this.user);
        Assertions.assertNotNull(user);
        Assertions.assertEquals(UUID.fromString("1cc1d929-1373-4c79-ab13-50d743c25146"), user.getId());
        Assertions.assertEquals("any_name", user.getName());
        Assertions.assertEquals("any_mail@mail.com", user.getEmail());
    }


}