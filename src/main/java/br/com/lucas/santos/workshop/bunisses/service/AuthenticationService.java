package br.com.lucas.santos.workshop.bunisses.service;

import br.com.lucas.santos.workshop.domain.dto.request.AuthenticationRequestDto;
import br.com.lucas.santos.workshop.domain.dto.response.AuthenticationResponseDto;
import br.com.lucas.santos.workshop.domain.entities.User;
import br.com.lucas.santos.workshop.domain.usecases.authentication.Authentication;
import br.com.lucas.santos.workshop.infrastructure.adapters.cryphtography.BcryptAdapter;
import br.com.lucas.santos.workshop.infrastructure.adapters.db.UserRepository;

import br.com.lucas.santos.workshop.infrastructure.exceptions.InvalidCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthenticationService implements Authentication {


    private final UserRepository userJpaRepository;
    private final BcryptAdapter bcryptAdapter;
    public AuthenticationService(UserRepository userJpaRepository, BcryptAdapter bcryptAdapter){
        this.userJpaRepository = userJpaRepository;
        this.bcryptAdapter = bcryptAdapter;
    }

    @Override
    @Transactional
    public AuthenticationResponseDto authenticate(AuthenticationRequestDto authenticationRequestDto) {
        User user =  userJpaRepository.loadUserByEmail(authenticationRequestDto.email());
        boolean isMatchedPassword = bcryptAdapter.compare(authenticationRequestDto.password(), user.getPassword());
        if(!isMatchedPassword) throw new InvalidCredentialsException("Invalid Credentials");
        return null;
    }
}
