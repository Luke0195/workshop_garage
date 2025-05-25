package br.com.lucas.santos.workshop.controller;


import br.com.lucas.santos.workshop.domain.dto.request.ClientRequestDto;
import br.com.lucas.santos.workshop.domain.entities.enums.ClientStatus;
import br.com.lucas.santos.workshop.utils.ParseHelper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@ActiveProfiles("dev")
@AutoConfigureMockMvc
@SpringBootTest
@WithMockUser(username = "admin", roles = {"ADMIN"})
class ClientControllerTest {

    private static final String ROUTE_NAME = "/client";

    @Autowired
    private MockMvc mockMvc;

    private ClientRequestDto clientRequestDto;

    @DisplayName("POST - handleAddUser should returns 400 if no client name is provided")
    @Test
    void handleAddUserShouldReturnsBadRequestIfNoClientNameIsProvided() throws Exception{
        ClientRequestDto clientRequestDto =  new ClientRequestDto(null, "any_phone", "any_mail@mail.com", "any_cpf",
            "any_zipcode", "any_address", 1, "any_complement", ClientStatus.ACTIVE);
        String jsonBody = ParseHelper.parseObjectToString(clientRequestDto);
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post(ROUTE_NAME)
            .content(jsonBody)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
        );
        resultActions.andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}