package br.com.lucas.santos.workshop.infrastructure.adapters.db;

import br.com.lucas.santos.workshop.business.contractors.externallibs.notification.EmailNotification;
import br.com.lucas.santos.workshop.business.contractors.repositories.passwordreset.DbForgotUserPassword;
import br.com.lucas.santos.workshop.business.contractors.repositories.passwordreset.DbLoadPasswordResetByToken;
import br.com.lucas.santos.workshop.domain.dto.request.EmailNotificationRequestDto;
import br.com.lucas.santos.workshop.domain.entities.PasswordResetToken;
import br.com.lucas.santos.workshop.domain.entities.User;

import br.com.lucas.santos.workshop.infrastructure.exceptions.InvalidCredentialsException;
import br.com.lucas.santos.workshop.infrastructure.exceptions.ResourceNotFoundException;
import br.com.lucas.santos.workshop.infrastructure.repository.PasswordResetTokenJpaRepository;
import br.com.lucas.santos.workshop.utils.EmailHelper;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import java.util.Objects;
import java.util.UUID;


@Component
public class PasswordForgotRepository implements DbForgotUserPassword, DbLoadPasswordResetByToken {
    private final PasswordResetTokenJpaRepository passwordResetTokenJpaRepository;
    private final UserRepository userRepository;
    private final EmailNotification emailNotification;

    public PasswordForgotRepository(PasswordResetTokenJpaRepository passwordResetTokenJpaRepository,
                                    UserRepository userRepository, EmailNotification emailNotification) {
        this.passwordResetTokenJpaRepository = passwordResetTokenJpaRepository;
        this.userRepository = userRepository;
        this.emailNotification = emailNotification;
    }

    @SneakyThrows
    @Override
    public void forgotUserPassword(String email) {
        UUID recoveryToken = UUID.randomUUID();
        String forgotPasswordUrl = String.format("http://localhost:4200/resetpassword?token=%s", recoveryToken);
        User user = userRepository.loadUserByEmail(email);
        if(Objects.isNull(user)) throw new InvalidCredentialsException("User not found");
        PasswordResetToken passwordResetToken = PasswordResetToken.builder()
            .token(recoveryToken.toString())
            .used(Boolean.FALSE)
            .expiresAt(Instant.now().plus(1, ChronoUnit.HOURS))
            .user(user)
            .build();
        passwordResetTokenJpaRepository.save(passwordResetToken);
        String message = EmailHelper.buildPasswordRecoveryEmail(user.getName(), forgotPasswordUrl);
        EmailNotificationRequestDto emailNotificationRequestDto = new EmailNotificationRequestDto(user.getEmail(), "Recuperação de senha", message);
        emailNotification.sendNotification(emailNotificationRequestDto);
    }



    @Override
    public PasswordResetToken loadPasswordResetByToken(String token) {
        return passwordResetTokenJpaRepository.findByToken(token).orElseThrow(() -> new ResourceNotFoundException("Invalid token is provided"));
    }
}
