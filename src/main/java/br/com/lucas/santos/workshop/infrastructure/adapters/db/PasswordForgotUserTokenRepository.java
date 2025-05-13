package br.com.lucas.santos.workshop.infrastructure.adapters.db;

import br.com.lucas.santos.workshop.bunisses.contractors.externalibs.notification.EmailNotification;
import br.com.lucas.santos.workshop.bunisses.contractors.repositories.passwordreset.ForgotUserPassword;
import br.com.lucas.santos.workshop.domain.dto.request.EmailNotificationRequestDto;
import br.com.lucas.santos.workshop.domain.entities.PasswordResetToken;
import br.com.lucas.santos.workshop.domain.entities.User;
import br.com.lucas.santos.workshop.infrastructure.exceptions.ResourceNotFoundException;
import br.com.lucas.santos.workshop.infrastructure.repository.PasswordResetTokenJpaRepository;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import java.util.UUID;


@Component
public class PasswordForgotUserTokenRepository implements ForgotUserPassword {
    private final PasswordResetTokenJpaRepository passwordResetTokenJpaRepository;
    private final UserRepository userRepository;
    private final EmailNotification emailNotification;

    public PasswordForgotUserTokenRepository(PasswordResetTokenJpaRepository passwordResetTokenJpaRepository,
                                             UserRepository userRepository, EmailNotification emailNotification) {
        this.passwordResetTokenJpaRepository = passwordResetTokenJpaRepository;
        this.userRepository = userRepository;
        this.emailNotification = emailNotification;
    }

    @SneakyThrows
    @Override
    public void forgotUserPassword(String email) {
        UUID recoveryToken = UUID.randomUUID();
        String forgotPasswordUrl = String.format("http://localhost:8080/api/resetpassword?token=%s", recoveryToken).toString();
        User user = userRepository.loadUserByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User email was not found"));
        PasswordResetToken passwordResetToken = PasswordResetToken.builder()
            .token(recoveryToken)
            .used(Boolean.FALSE)
            .expiresAt(Instant.now().plus(1, ChronoUnit.HOURS))
            .user(user)
            .build();
        passwordResetTokenJpaRepository.save(passwordResetToken);
        String message = buildPasswordRecoveryEmail(user.getName(), forgotPasswordUrl);
        EmailNotificationRequestDto emailNotificationRequestDto = new EmailNotificationRequestDto(user.getEmail(), "Recuperação de senha", message);
        emailNotification.sendNotification(emailNotificationRequestDto);
    }

    public String buildPasswordRecoveryEmail(String userName, String recoveryLink) {
        return """
        <!DOCTYPE html>
        <html lang="pt-BR">
        <head>
            <meta charset="UTF-8">
            <title>Recuperação de Senha</title>
        </head>
        <body style="font-family: Arial, sans-serif; background-color: #f4f4f4; margin: 0; padding: 0;">
            <table align="center" width="100%%" style="max-width: 600px; margin: 0 auto; background-color: #ffffff; padding: 20px; border-radius: 8px; box-shadow: 0 2px 4px rgba(0,0,0,0.1);">
                <tr>
                    <td style="text-align: center; padding-bottom: 20px;">
                        <h2 style="color: #333333;">Recuperação de Senha</h2>
                    </td>
                </tr>
                <tr>
                    <td>
                        <p style="font-size: 16px; color: #333;">Olá, <strong>%s</strong>,</p>
                        <p style="font-size: 16px; color: #333;">Recebemos uma solicitação para redefinir sua senha.</p>
                        <p style="font-size: 16px; color: #333;">Para continuar com o processo, clique no botão abaixo:</p>
                        <p style="text-align: center; margin: 30px 0;">
                            <a href="%s" style="background-color: #1a73e8; color: white; padding: 12px 24px; text-decoration: none; border-radius: 4px; font-weight: bold;">Redefinir Senha</a>
                        </p>
                        <p style="font-size: 14px; color: #666;">Se você não solicitou essa alteração, pode ignorar este e-mail.</p>
                        <br>
                        <p style="font-size: 14px; color: #999;">Atenciosamente,<br>Sua equipe de suporte</p>
                    </td>
                </tr>
            </table>
        </body>
        </html>
        """.formatted(userName, recoveryLink);
    }
}
