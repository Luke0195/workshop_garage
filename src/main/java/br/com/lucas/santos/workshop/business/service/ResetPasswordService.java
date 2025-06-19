package br.com.lucas.santos.workshop.business.service;

import br.com.lucas.santos.workshop.business.contractors.externallibs.cryptography.Encrypter;
import br.com.lucas.santos.workshop.business.contractors.repositories.user.DbAddUserRepository;
import br.com.lucas.santos.workshop.domain.dto.request.ResetPasswordParamsDto;
import br.com.lucas.santos.workshop.domain.entities.PasswordResetToken;
import br.com.lucas.santos.workshop.domain.entities.User;
import br.com.lucas.santos.workshop.infrastructure.exceptions.ResourceNotFoundException;
import br.com.lucas.santos.workshop.infrastructure.repository.PasswordResetTokenJpaRepository;
import org.springframework.stereotype.Service;

@Service
public class ResetPasswordService {

    private final PasswordResetTokenJpaRepository passwordResetTokenJpaRepository;
    private final Encrypter encrypter;
    private final DbAddUserRepository dbAddUserRepository;
    public ResetPasswordService(
        PasswordResetTokenJpaRepository passwordResetTokenJpaRepository,
        Encrypter encrypter,
        DbAddUserRepository dbAddUserRepository){
        this.passwordResetTokenJpaRepository = passwordResetTokenJpaRepository;
        this.encrypter = encrypter;
        this.dbAddUserRepository = dbAddUserRepository;
    }


    public void reset(ResetPasswordParamsDto resetPasswordDto){
        PasswordResetToken passwordResetToken = passwordResetTokenJpaRepository.findByToken(resetPasswordDto.token()).orElseThrow(()-> new ResourceNotFoundException("Token not found"));
        User user = passwordResetToken.getUser();
        String newUserPassword = this.encrypter.encrypt(resetPasswordDto.password());
        user.setPassword(newUserPassword);
        passwordResetToken.setUsed(true);
        this.passwordResetTokenJpaRepository.save(passwordResetToken);
        this.dbAddUserRepository.add(user);
    }
}
