package br.com.lucas.santos.workshop.infrastructure.adapters.db;


import br.com.lucas.santos.workshop.factories.UserFactory;
import br.com.lucas.santos.workshop.infrastructure.exceptions.ResourceNotFoundException;
import br.com.lucas.santos.workshop.infrastructure.repository.PasswordResetTokenJpaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class PasswordResetTokenRepositoryTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private PasswordForgotUserTokenRepository sut;

    @Mock
    private PasswordResetTokenJpaRepository passwordResetTokenJpaRepository;


    @DisplayName("resetPassword should throws ResourceNotFoundException if user email was not found")
    @Test
    void resetPasswordShouldThrowsResourceNotFoundExceptionIfUserEmailWasNotFound(){
        Mockito.when(userRepository.loadUserByEmail(Mockito.any())).thenThrow(ResourceNotFoundException.class);
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            sut.resetPassword("invalid_mail@mail.com");
        });
    }

    @DisplayName("resetPassword should save an passwordReset when valid data is provided")
    @Test
    void resetPasswordShouldSaveAnPasswordResetWhenValidDataIsProvided(){
        Mockito.when(userRepository.loadUserByEmail(Mockito.any())).thenReturn(Optional.of(UserFactory.makeUser(UserFactory.makeUserRequestDto())));
        sut.resetPassword("any_mail");
        Mockito.verify(passwordResetTokenJpaRepository).save(Mockito.any());
    }
}