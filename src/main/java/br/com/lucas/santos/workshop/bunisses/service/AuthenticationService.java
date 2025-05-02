package br.com.lucas.santos.workshop.bunisses.service;

import br.com.lucas.santos.workshop.domain.dto.request.AuthenticationRequestDto;
import br.com.lucas.santos.workshop.domain.dto.response.AuthenticationResponseDto;
import br.com.lucas.santos.workshop.domain.usecases.authentication.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthenticationService implements Authentication {



    @Override
    @Transactional
    public AuthenticationResponseDto authenticate(AuthenticationRequestDto authenticationRequestDto) {
        return null;
    }
}
