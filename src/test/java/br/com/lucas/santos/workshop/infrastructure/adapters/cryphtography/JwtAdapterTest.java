package br.com.lucas.santos.workshop.infrastructure.adapters.cryphtography;


import br.com.lucas.santos.workshop.domain.dto.response.AuthenticationResponseDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtEncoder;

@ExtendWith(MockitoExtension.class)
class JwtAdapterTest {

    @InjectMocks
    private JwtAdapter sut;

    @Mock
    private JwtEncoder jwtEncoder;


    @DisplayName("generateToken should returns a token when valid id is provided")
    @Test
    void generateTokenShouldReturnsATokenWhenValidIdIsProvided(){
        Jwt jwtMock = Mockito.mock(Jwt.class);
        Mockito.when(jwtMock.getTokenValue()).thenReturn("any_token");
        Mockito.when(jwtEncoder.encode(Mockito.any())).thenReturn(jwtMock);
        AuthenticationResponseDto token = sut.generateToken("any_id");
        Assertions.assertEquals("any_token", token.token());
    }
}