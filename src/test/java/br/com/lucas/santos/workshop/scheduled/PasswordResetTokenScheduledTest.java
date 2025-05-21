package br.com.lucas.santos.workshop.scheduled;

import br.com.lucas.santos.workshop.infrastructure.repository.PasswordResetTokenJpaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;



@ExtendWith(MockitoExtension.class)
class PasswordResetTokenScheduledTest {

    @InjectMocks
    private PasswordResetTokenScheduled passwordResetTokenScheduled;

    @Mock
    private PasswordResetTokenJpaRepository passwordResetTokenJpaRepository;

    @Test
    void shouldRemoveFieldsWithUsedStatusTrue(){
    passwordResetTokenScheduled.removePasswordTokenWithUsedStatus();
    Mockito.verify(passwordResetTokenJpaRepository, Mockito.times(1)).deletePasswordResetTokenByUsed(true);

    }
}