package br.com.lucas.santos.workshop.infrastructure.adapters.db;

import br.com.lucas.santos.workshop.bunisses.protocols.db.passwordreset.ResetPassword;
import br.com.lucas.santos.workshop.domain.entities.PasswordResetToken;
import br.com.lucas.santos.workshop.domain.entities.User;
import br.com.lucas.santos.workshop.infrastructure.exceptions.ResourceNotFoundException;
import br.com.lucas.santos.workshop.infrastructure.repository.PasswordResetTokenJpaRepository;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import java.util.UUID;


@Component
public class PasswordResetTokenRepository implements ResetPassword {
    private final PasswordResetTokenJpaRepository passwordResetTokenJpaRepository;
    private final UserRepository userRepository;

    public PasswordResetTokenRepository(PasswordResetTokenJpaRepository passwordResetTokenJpaRepository,
                                           UserRepository userRepository){
        this.passwordResetTokenJpaRepository = passwordResetTokenJpaRepository;
        this.userRepository = userRepository;
    }
    @Override
    public void resetPassword(String email) {
        User user = userRepository.loadUserByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User email was not found"));
        UUID recoveryToken = UUID.randomUUID();
        PasswordResetToken passwordResetToken =  PasswordResetToken.builder()
            .token(recoveryToken)
            .used(Boolean.FALSE)
            .expiresAt(Instant.now().plus(1, ChronoUnit.HOURS))
            .user(user)
            .build();
        passwordResetTokenJpaRepository.save(passwordResetToken);
        System.out.println("REGISTRO SALVO");
    }
}
