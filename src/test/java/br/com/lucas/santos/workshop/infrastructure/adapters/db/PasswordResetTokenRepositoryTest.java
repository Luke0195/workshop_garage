package br.com.lucas.santos.workshop.infrastructure.adapters.db;


import br.com.lucas.santos.workshop.business.contractors.externallibs.notification.EmailNotification;

import br.com.lucas.santos.workshop.domain.entities.PasswordResetToken;
import br.com.lucas.santos.workshop.factories.PasswordResetFactory;
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
    private PasswordForgotRepository sut;


    @Mock
    private EmailNotification emailNotification;

    @Mock
    private PasswordResetTokenJpaRepository passwordResetTokenJpaRepository;


    @DisplayName("resetPassword should throws ResourceNotFoundException if user email was not found")
    @Test
    void resetPasswordShouldThrowsResourceNotFoundExceptionIfUserEmailWasNotFound(){
        Mockito.when(userRepository.loadUserByEmail(Mockito.any())).thenThrow(ResourceNotFoundException.class);
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            sut.forgotUserPassword("invalid_mail@mail.com");
        });
    }

    @DisplayName("resetPassword should save a passwordReset when valid data is provided")
    @Test
    void resetPasswordShouldSaveAPasswordResetWhenValidDataIsProvided() throws Exception {
        String email = "any_mail@mail.com";
        var user = UserFactory.makeUser(UserFactory.makeUserRequestDto());
        Mockito.when(userRepository.loadUserByEmail(email)).thenReturn(Optional.of(user));
        Mockito.doNothing().when(emailNotification).sendNotification(Mockito.any());
        sut.forgotUserPassword(email);
        Mockito.verify(passwordResetTokenJpaRepository).save(Mockito.any());
        Mockito.verify(emailNotification).sendNotification(Mockito.any()); // opcional
    }


    @DisplayName("loadPasswordResetByToken should returns ResourceNotFoundException when token was not found")
    @Test
    void loadPasswordResetTokenShouldReturnsResourceNotFoundExceptionWhenTokenWasNotFound(){
        Mockito.when(passwordResetTokenJpaRepository.findByToken(Mockito.any())).thenReturn(Optional.empty());
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            sut.loadPasswordResetByToken("any_token");
        });
    }

    @DisplayName("loadPasswordResetToken should returns PasswordResetToken when valid token is provided")
    @Test
    void loadPasswordResetTokenShouldReturnsPasswordResetTokenWhenValidTokenIsProvided(){
        Mockito.when(passwordResetTokenJpaRepository.findByToken(Mockito.any())).thenReturn(Optional.of(PasswordResetFactory.makePasswordResetToken()));
        PasswordResetToken passwordResetToken = sut.loadPasswordResetByToken("any_token");
        Assertions.assertNotNull(passwordResetToken);
        Assertions.assertEquals(1, passwordResetToken.getId());
    }


}