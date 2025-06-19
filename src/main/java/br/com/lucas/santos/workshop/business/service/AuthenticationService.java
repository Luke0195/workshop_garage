package br.com.lucas.santos.workshop.business.service;

import br.com.lucas.santos.workshop.business.contractors.externallibs.cryptography.HashComparer;
import br.com.lucas.santos.workshop.business.contractors.externallibs.cryptography.TokenGenerator;
import br.com.lucas.santos.workshop.business.contractors.repositories.user.DbLoadUserByEmailRepository;
import br.com.lucas.santos.workshop.domain.dto.request.AuthenticationRequestDto;
import br.com.lucas.santos.workshop.domain.dto.response.AuthenticationResponseDto;
import br.com.lucas.santos.workshop.domain.entities.User;
import br.com.lucas.santos.workshop.infrastructure.exceptions.InvalidCredentialsException;
import br.com.lucas.santos.workshop.infrastructure.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;


@Service
public class AuthenticationService {

    private final DbLoadUserByEmailRepository dbLoadUserByEmailRepository;
    private final HashComparer hashComparer;
    private final TokenGenerator tokenGenerator;


    public AuthenticationService(
          final DbLoadUserByEmailRepository dbLoadUserByEmailRepository,
          final HashComparer hashComparer,
          final TokenGenerator tokenGenerator
    ){
        this.dbLoadUserByEmailRepository = dbLoadUserByEmailRepository;
        this.hashComparer = hashComparer;
        this.tokenGenerator = tokenGenerator;
    }

    @Transactional
    public AuthenticationResponseDto authenticate(AuthenticationRequestDto authenticationRequestDto) {
        User user =  dbLoadUserByEmailRepository.loadUserByEmail(authenticationRequestDto.email());
        if( Objects.isNull(user)) throw new InvalidCredentialsException("Client id not found");
        boolean isMatchedPassword = hashComparer.compare(authenticationRequestDto.password(), user.getPassword());
        if(!isMatchedPassword) throw new InvalidCredentialsException("Invalid Credentials");
        return tokenGenerator.generateToken(user.getId().toString());
    }


}
