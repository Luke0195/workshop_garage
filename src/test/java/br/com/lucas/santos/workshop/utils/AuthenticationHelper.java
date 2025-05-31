package br.com.lucas.santos.workshop.utils;

import org.mockito.Mockito;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;

import java.time.Instant;
import java.util.Map;

public class AuthenticationHelper {

    private AuthenticationHelper(){}


    public static void makeGenerateFakeToken(JwtDecoder jwtDecoder){
        Mockito.when(jwtDecoder.decode(Mockito.anyString())).thenAnswer(invocation -> {
            String token = invocation.getArgument(0);

            Map<String, Object> headers = Map.of("alg", "none");
            Map<String, Object> claims = Map.of("sub", "any_user");
            Instant now = Instant.now();

            return new Jwt(token, now, now.plusSeconds(3600), headers, claims);
        });
    }
}
