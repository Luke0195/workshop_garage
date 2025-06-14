package br.com.lucas.santos.workshop.domain.features.authentication;

import br.com.lucas.santos.workshop.domain.dto.request.AuthenticationRequestDto;
import br.com.lucas.santos.workshop.domain.dto.response.AuthenticationResponseDto;

public interface Authentication {

    AuthenticationResponseDto authenticate(AuthenticationRequestDto authenticationRequestDto);
}
