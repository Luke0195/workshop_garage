package br.com.lucas.santos.workshop.infrastructure.adapters.db;


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

@ExtendWith(MockitoExtension.class)
class PasswordResetTokenRepositoryTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private PasswordResetTokenRepository sut;

    @Mock
    private PasswordResetTokenJpaRepository passwordResetTokenJpaRepository;


    @DisplayName("resetPassword should  throws ResourceNotFoundException if user email was not found")
    @Test
    void resetPasswordShouldThrowsResourceNotFoundExceptionIfUserEmailWasNotFound(){
        Mockito.when(userRepository.loadUserByEmail(Mockito.any())).thenThrow(ResourceNotFoundException.class);
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            sut.resetPassword("invalid_mail@mail.com");
        });
    }
}