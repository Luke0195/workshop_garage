package br.com.lucas.santos.workshop.bunisses.service;

import br.com.lucas.santos.workshop.domain.dto.request.AuthenticationRequestDto;
import br.com.lucas.santos.workshop.domain.dto.response.AuthenticationResponseDto;
import br.com.lucas.santos.workshop.domain.entities.User;
import br.com.lucas.santos.workshop.domain.usecases.authentication.Authentication;
import br.com.lucas.santos.workshop.infrastructure.adapters.db.UserJpaRepository;
import br.com.lucas.santos.workshop.infrastructure.exceptions.InvalidCredentialsException;
import br.com.lucas.santos.workshop.infrastructure.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthenticationService implements Authentication {


    private final UserJpaRepository userJpaRepository;
    private final BCryptPasswordEncoder encoder;
    public AuthenticationService(UserJpaRepository userJpaRepository, BCryptPasswordEncoder bCryptPasswordEncoder){
        this.userJpaRepository = userJpaRepository;
        this.encoder = bCryptPasswordEncoder;
    }

    @Override
    @Transactional
    public AuthenticationResponseDto authenticate(AuthenticationRequestDto authenticationRequestDto) {
        User user =  userJpaRepository.loadUserByEmail(authenticationRequestDto.email());
        //encoder.m
        return null;
    }
}
