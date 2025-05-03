package br.com.lucas.santos.workshop.controller;

import br.com.lucas.santos.workshop.domain.dto.request.AuthenticationRequestDto;
import br.com.lucas.santos.workshop.utils.ParseHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("dev")
class AuthenticationControllerTest {

    private static final String ROUTE_NAME = "/signin";
    private AuthenticationRequestDto authenticationRequestDto;

    @Autowired
    private MockMvc mockMvc;


    @DisplayName("POST - handleAuthentication should returns 400 if no email is provided")
    @Test
    void handleAuthenticationShouldReturnsBadRequestWhenNoEmailIsProvided() throws Exception{
        AuthenticationRequestDto authenticationRequestDto = new AuthenticationRequestDto(null, "any_password");
        String jsonBody = ParseHelper.parseObjectToString(authenticationRequestDto);
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post(ROUTE_NAME)
                .content(jsonBody)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
        );
        resultActions.andExpect(MockMvcResultMatchers.status().isBadRequest());
        Assertions.assertEquals("Validation Exception", ParseHelper.getExceptionMessage(resultActions));
    }

    @DisplayName("POST - handleAuthentication should returns 400 if no password is provided")
    @Test
    void handleAuthenticationShouldReturnsBadRequestWhenNoPasswordIsProvided() throws Exception{
        AuthenticationRequestDto authenticationRequestDto = new AuthenticationRequestDto("any_mail@mail.com", null);
        String jsonBody = ParseHelper.parseObjectToString(authenticationRequestDto);
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post(ROUTE_NAME)
                .content(jsonBody)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
        );
        resultActions.andExpect(MockMvcResultMatchers.status().isBadRequest());
        Assertions.assertEquals("Validation Exception", ParseHelper.getExceptionMessage(resultActions));
    }

    @DisplayName("POST - handleAuthentication should return 400 if an invalid e-mail is provided")
    @Test
    void handleAuthenticationShouldReturnsBadRequestWhenAnInvalidEmailIsProvided() throws Exception{
        AuthenticationRequestDto authenticationRequestDto = new AuthenticationRequestDto("any_mail", "any_password");
        String jsonBody = ParseHelper.parseObjectToString(authenticationRequestDto);
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post(ROUTE_NAME)
                .content(jsonBody)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
        );
        resultActions.andExpect(MockMvcResultMatchers.status().isBadRequest());
        Assertions.assertEquals("Validation Exception", ParseHelper.getExceptionMessage(resultActions));
    }
}