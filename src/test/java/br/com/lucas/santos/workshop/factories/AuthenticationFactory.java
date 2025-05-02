package br.com.lucas.santos.workshop.factories;

import br.com.lucas.santos.workshop.domain.dto.request.AuthenticationRequestDto;
import br.com.lucas.santos.workshop.domain.dto.response.AuthenticationResponseDto;

public class AuthenticationFactory {

    private AuthenticationFactory(){};


    public static AuthenticationRequestDto makeAuthenticationRequestDto(){
        return new AuthenticationRequestDto("any_mail@mail.com", "any_password");
    }

    public static AuthenticationResponseDto makeAuthenticationResponseDto(){
        return new AuthenticationResponseDto("any_token", 3000L);
    }
}
