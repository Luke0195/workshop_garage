package br.com.lucas.santos.workshop.controller;

import br.com.lucas.santos.workshop.dto.request.AuthenticationRequestDto;
import br.com.lucas.santos.workshop.utils.UtilFactory;
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
class LoginControllerTest {

    private static final String ROUTE_NAME = "/signin";
    private AuthenticationRequestDto authenticationRequestDto;

    @Autowired
    private MockMvc mockMvc;


    @DisplayName("POST - handleAuthentication should returns 400 if no email is provided")
    @Test
    void handleAuthenticationShouldReturnsBadRequestWhenNoEmailIsProvided() throws Exception{
        AuthenticationRequestDto authenticationRequestDto = new AuthenticationRequestDto(null, "any_password");
        String jsonBody = UtilFactory.parseObjectToString(authenticationRequestDto);
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post(ROUTE_NAME)
                .content(jsonBody)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
        );
        resultActions.andExpect(MockMvcResultMatchers.status().isBadRequest());
        Assertions.assertEquals("Validation Exception", UtilFactory.getExceptionMessage(resultActions));
    }
}