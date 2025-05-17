package br.com.lucas.santos.workshop.bunisses.service;

import br.com.lucas.santos.workshop.domain.dto.request.ResetPasswordParamsDto;
import br.com.lucas.santos.workshop.domain.entities.PasswordResetToken;
import br.com.lucas.santos.workshop.domain.entities.User;
import br.com.lucas.santos.workshop.infrastructure.adapters.cryphtography.BcryptAdapter;
import br.com.lucas.santos.workshop.infrastructure.adapters.db.PasswordForgotRepository;
import br.com.lucas.santos.workshop.infrastructure.adapters.db.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class ResetPasswordService {

    private final PasswordForgotRepository passwordForgotRepository;
    private final BcryptAdapter bcryptAdapter;
    private final UserRepository userRepository;
    // PasswordResetToken - para validar se o token existe.
    // Recuperar o usuário que possui aquele token
    //

    public ResetPasswordService(
        PasswordForgotRepository passwordForgotRepository,
        BcryptAdapter brBcryptAdapter,
        UserRepository userRepository){
        this.passwordForgotRepository = passwordForgotRepository;
        this.bcryptAdapter = brBcryptAdapter;
        this.userRepository = userRepository;
    }


    public void reset(ResetPasswordParamsDto resetPasswordDto){
        PasswordResetToken passwordResetToken = passwordForgotRepository.loadPasswordResetByToken(resetPasswordDto.token());
        User user = passwordResetToken.getUser();
        String newUserPassword = bcryptAdapter.encrypt(resetPasswordDto.password());
        user.setPassword(newUserPassword);
        this.userRepository.add(user);
    }
}
