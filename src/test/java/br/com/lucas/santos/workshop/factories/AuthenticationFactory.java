package br.com.lucas.santos.workshop.factories;

import br.com.lucas.santos.workshop.domain.dto.request.AuthenticationRequestDto;
import br.com.lucas.santos.workshop.domain.dto.request.ForgotEmailDto;
import br.com.lucas.santos.workshop.domain.dto.response.AuthenticationResponseDto;
import net.datafaker.Faker;

public class AuthenticationFactory {

    private static final Faker faker = FakerFactory.makeFaker();
    private static final String AUTHENTICATION_EMAIL = faker.internet().emailAddress();
    private static final String AUTHENTICATION_PASSWORD = faker.internet().password();

    private AuthenticationFactory(){};


    public static AuthenticationRequestDto makeAuthenticationRequestDto(){
        return new AuthenticationRequestDto(AUTHENTICATION_EMAIL, AUTHENTICATION_PASSWORD);
    }

    public static AuthenticationResponseDto makeAuthenticationResponseDto(){

        return new AuthenticationResponseDto("any_token", faker.number().numberBetween(1L, 3000L));
    }

    public static ForgotEmailDto makeForgotEmailDto(){
        return new ForgotEmailDto(AUTHENTICATION_EMAIL);
    }
}
