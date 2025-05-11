package br.com.lucas.santos.workshop.bunisses.service;

import br.com.lucas.santos.workshop.bunisses.contractors.externalibs.notification.EmailNotification;
import br.com.lucas.santos.workshop.bunisses.contractors.repositories.passwordreset.ResetPassword;
import br.com.lucas.santos.workshop.domain.dto.request.AuthenticationRequestDto;
import br.com.lucas.santos.workshop.domain.dto.response.AuthenticationResponseDto;
import br.com.lucas.santos.workshop.domain.entities.User;
import br.com.lucas.santos.workshop.domain.features.authentication.Authentication;
import br.com.lucas.santos.workshop.domain.features.authentication.ForgotPassword;
import br.com.lucas.santos.workshop.infrastructure.adapters.cryphtography.BcryptAdapter;
import br.com.lucas.santos.workshop.infrastructure.adapters.cryphtography.JwtAdapter;
import br.com.lucas.santos.workshop.infrastructure.adapters.db.UserRepository;

import br.com.lucas.santos.workshop.infrastructure.exceptions.InvalidCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class AuthenticationService implements Authentication, ForgotPassword {


    private final UserRepository userJpaRepository;
    private final BcryptAdapter bcryptAdapter;
    private final JwtAdapter jwtAdapter;
    private final ResetPassword resetPassword;
    public AuthenticationService(
            UserRepository userJpaRepository,
            BcryptAdapter bcryptAdapter,
            JwtAdapter jwtAdapter,
            ResetPassword resetPassword,
            EmailNotification emailNotification
    ){
        this.userJpaRepository = userJpaRepository;
        this.bcryptAdapter = bcryptAdapter;
        this.jwtAdapter = jwtAdapter;
        this.resetPassword = resetPassword;

    }

    @Override
    @Transactional
    public AuthenticationResponseDto authenticate(AuthenticationRequestDto authenticationRequestDto) {
        User user =  userJpaRepository.loadUserByEmail(authenticationRequestDto.email())
            .orElseThrow(() ->  new InvalidCredentialsException("Invalid credentials are provided"));
        boolean isMatchedPassword = bcryptAdapter.compare(authenticationRequestDto.password(), user.getPassword());
        if(!isMatchedPassword) throw new InvalidCredentialsException("Invalid Credentials");
        return jwtAdapter.generateToken(user.getId().toString());
    }

    @Override
    public void forgotPassword(String email) {
        resetPassword.resetPassword(email);
    }
}
