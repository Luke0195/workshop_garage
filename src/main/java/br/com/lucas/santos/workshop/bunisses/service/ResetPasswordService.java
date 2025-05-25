package br.com.lucas.santos.workshop.bunisses.service;

import br.com.lucas.santos.workshop.domain.dto.request.ResetPasswordParamsDto;
import br.com.lucas.santos.workshop.domain.entities.PasswordResetToken;
import br.com.lucas.santos.workshop.domain.entities.User;
import br.com.lucas.santos.workshop.infrastructure.adapters.cryphtography.BcryptAdapter;
import br.com.lucas.santos.workshop.infrastructure.adapters.db.UserRepository;
import br.com.lucas.santos.workshop.infrastructure.exceptions.ResourceNotFoundException;
import br.com.lucas.santos.workshop.infrastructure.repository.PasswordResetTokenJpaRepository;
import org.springframework.stereotype.Service;

@Service
public class ResetPasswordService {

    private final PasswordResetTokenJpaRepository passwordResetTokenJpaRepository;
    private final BcryptAdapter bcryptAdapter;
    private final UserRepository userRepository;

    public ResetPasswordService(
        PasswordResetTokenJpaRepository passwordResetTokenJpaRepository,
        BcryptAdapter brBcryptAdapter,
        UserRepository userRepository){
        this.passwordResetTokenJpaRepository = passwordResetTokenJpaRepository;
        this.bcryptAdapter = brBcryptAdapter;
        this.userRepository = userRepository;
    }


    public void reset(ResetPasswordParamsDto resetPasswordDto){
        PasswordResetToken passwordResetToken = passwordResetTokenJpaRepository.findByToken(resetPasswordDto.token()).orElseThrow(()-> new ResourceNotFoundException("Token not found"));
        User user = passwordResetToken.getUser();
        String newUserPassword = bcryptAdapter.encrypt(resetPasswordDto.password());
        user.setPassword(newUserPassword);
        passwordResetToken.setUsed(true);
        this.passwordResetTokenJpaRepository.save(passwordResetToken);
        this.userRepository.add(user);
    }
}
