package br.com.lucas.santos.workshop.bunisses.service;

import br.com.lucas.santos.workshop.domain.dto.request.AuthenticationRequestDto;
import br.com.lucas.santos.workshop.domain.dto.response.AuthenticationResponseDto;
import br.com.lucas.santos.workshop.domain.usecases.authentication.Authentication;
import br.com.lucas.santos.workshop.infrastructure.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthenticationService implements Authentication {


    private final UserRepository userRepository;

    public AuthenticationService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public AuthenticationResponseDto authenticate(AuthenticationRequestDto authenticationRequestDto) {
        userRepository.findByEmail(authenticationRequestDto.email());
        return null;
    }
}
