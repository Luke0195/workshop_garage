package br.com.lucas.santos.workshop.controller;


import br.com.lucas.santos.workshop.domain.dto.request.ClientRequestDto;
import br.com.lucas.santos.workshop.domain.entities.enums.ClientStatus;
import br.com.lucas.santos.workshop.utils.ParseHelper;
import org.junit.jupiter.api.Assertions;
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
import org.springframework.util.Assert;

import javax.xml.transform.Result;


@ActiveProfiles("dev")
@AutoConfigureMockMvc
@SpringBootTest
@WithMockUser(username = "admin", roles = {"ADMIN"})
class ClientControllerTest {

    private static final String ROUTE_NAME = "/client";

    @Autowired
    private MockMvc mockMvc;

    private ClientRequestDto clientRequestDto;

    @DisplayName("POST -  handleAddClient should returns 400 if no client name is provided")
    @Test
    void handleAddClientShouldReturnsBadRequestIfNoClientNameIsProvided() throws Exception{
        ClientRequestDto clientRequestDto =  new ClientRequestDto(null, "any_phone", "any_mail@mail.com", "any_cpf",
            "any_zipcode", "any_address", 1, "any_complement", ClientStatus.ACTIVE);
        String jsonBody = ParseHelper.parseObjectToString(clientRequestDto);
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post(ROUTE_NAME)
            .content(jsonBody)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
        );
        String exceptionMessage = ParseHelper.getExceptionMessage(resultActions);
        resultActions.andExpect(MockMvcResultMatchers.status().isBadRequest());
        Assertions.assertEquals("Validation Exception", exceptionMessage);
    }

    @DisplayName("POST - handleAddClient should returns 400 if no phone is provided")
    @Test
    void handleAddClientShouldReturnsBadRequestIfNoPhoneIsProvided() throws Exception{
        ClientRequestDto clientRequestDto = new ClientRequestDto("any_name", null,"any_mail@mail.com", "870.296.190-30",
            "69310-030", "any_address", 1, "any_complement", ClientStatus.ACTIVE );
        String jsonBody = ParseHelper.parseObjectToString(clientRequestDto);
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post(ROUTE_NAME)
            .content(jsonBody).accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON));
        String exceptionMessage = ParseHelper.getExceptionMessage(resultActions);
        resultActions.andExpect(MockMvcResultMatchers.status().isBadRequest());
        Assertions.assertEquals("Validation Exception", exceptionMessage);
    }

    @DisplayName("POST - handleAddClient should returns 400 if no email is provided")
    @Test
    void handleAddClientShouldReturnsBadRequestIfNoEmailIsProvided() throws Exception{
        ClientRequestDto clientRequestDto = new ClientRequestDto("any_name", "any_phone",null, "870.296.190-30",
            "69310-030", "any_address", 1, "any_complement", ClientStatus.ACTIVE );
        String jsonBody = ParseHelper.parseObjectToString(clientRequestDto);
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post(ROUTE_NAME)
            .content(jsonBody).accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON));
        String exceptionMessage = ParseHelper.getExceptionMessage(resultActions);
        resultActions.andExpect(MockMvcResultMatchers.status().isBadRequest());
        Assertions.assertEquals("Validation Exception", exceptionMessage);
    }

    @DisplayName("POST - handleAddUser should returns 400 if an invalid email is provided")
    @Test
    void handleAddUserShouldReturnsBadRequestIfAnInvalidEmailsIsProvided() throws Exception{
        ClientRequestDto clientRequestDto = new ClientRequestDto("any_name", "any_phone","any_mail", "870.296.190-30",
            "69310-030", "any_address", 1, "any_complement", ClientStatus.ACTIVE );
        String jsonBody = ParseHelper.parseObjectToString(clientRequestDto);
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post(ROUTE_NAME)
            .content(jsonBody).accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON));
        String exceptionMessage = ParseHelper.getExceptionMessage(resultActions);
        resultActions.andExpect(MockMvcResultMatchers.status().isBadRequest());
        Assertions.assertEquals("Validation Exception", exceptionMessage);
    }


    @DisplayName("POST - handleAddClient should returns 400 if no cpf is provided")
    @Test
    void handleAddClientShouldReturnsBadRequestIfNoCpfIsProvided() throws Exception{
        ClientRequestDto clientRequestDto = new ClientRequestDto("any_name", "any_phone","any_mail@mail.com", null,
            "69310-030", "any_address", 1, "any_complement", ClientStatus.ACTIVE );
        String jsonBody = ParseHelper.parseObjectToString(clientRequestDto);
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post(ROUTE_NAME)
            .content(jsonBody).accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON));
        String exceptionMessage = ParseHelper.getExceptionMessage(resultActions);
        resultActions.andExpect(MockMvcResultMatchers.status().isBadRequest());
        Assertions.assertEquals("Validation Exception", exceptionMessage);
    }

    @DisplayName("POST - handleAddClient should returns 400 if an invalid cpf is provided")
    @Test
    void handleAddClientShouldReturnsBadRequestIfAnInvalidCpfProvided() throws Exception{
        ClientRequestDto clientRequestDto = new ClientRequestDto("any_name", "any_phone","any_mail@mail.com", "87029619030",
            "69310-030", "any_address", 1, "any_complement", ClientStatus.ACTIVE );
        String jsonBody = ParseHelper.parseObjectToString(clientRequestDto);
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post(ROUTE_NAME)
            .content(jsonBody)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON));
        String exceptionMessage = ParseHelper.getExceptionMessage(resultActions);
        resultActions.andExpect(MockMvcResultMatchers.status().isBadRequest());
        Assertions.assertEquals("Validation Exception", exceptionMessage);
    }

    @DisplayName("POST - handleAddClient should returns 400 if no zipcode is provided")
    @Test
    void handleAddClientShouldReturnsBadRequestIfNoZipCodeIsProvided() throws Exception{
        ClientRequestDto clientRequestDto = new ClientRequestDto("any_name", "any_phone",
            "any_mail@mail.com", "870.296.190-30", null, "any_address", 1,
            "any_complement", ClientStatus.ACTIVE);
        String jsonBody = ParseHelper.parseObjectToString(clientRequestDto);
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post(ROUTE_NAME)
            .content(jsonBody)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON));
        String exceptionMessage = ParseHelper.getExceptionMessage(resultActions);
        resultActions.andExpect(MockMvcResultMatchers.status().isBadRequest());
        Assertions.assertEquals("Validation Exception", exceptionMessage);
    }

    @DisplayName("POST - handleAddClient should returns 400 if an  invalid zipcode is provided")
    @Test
    void handleAddClientShouldReturnsBadRequestIfAnInvalidZipCodeIsProvided() throws Exception{
        ClientRequestDto clientRequestDto = new ClientRequestDto("any_name", "any_phone",
            "any_mail@mail.com", "870.296.190-30", null, "any_address", 1,
            "any_complement", ClientStatus.ACTIVE);
        String jsonBody = ParseHelper.parseObjectToString(clientRequestDto);
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post(ROUTE_NAME)
            .content(jsonBody)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON));
        String exceptionMessage = ParseHelper.getExceptionMessage(resultActions);
        resultActions.andExpect(MockMvcResultMatchers.status().isBadRequest());
        Assertions.assertEquals("Validation Exception", exceptionMessage);
    }

    @DisplayName("POST - handleAddClient should returns 400 if no address is provided")
    @Test
    void handleAddClientShouldReturnsBadRequestIfNoAddressIsProvided() throws Exception{
        ClientRequestDto clientRequestDto = new ClientRequestDto("any_name", "any_phone",
            "any_mail@mail.com", "870.296.190-30", "69310-030", null, 1,
            "any_complement", ClientStatus.ACTIVE);
        String jsonBody = ParseHelper.parseObjectToString(clientRequestDto);
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post(ROUTE_NAME)
            .content(jsonBody)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON));
        String exceptionMessage = ParseHelper.getExceptionMessage(resultActions);
        resultActions.andExpect(MockMvcResultMatchers.status().isBadRequest());
        Assertions.assertEquals("Validation Exception", exceptionMessage);
    }

    @DisplayName("POST - handleAddClient should returns 400 if no number is provided")
    @Test
    void handleAddClientShouldReturnsBadRequestIfNoNumberIsProvided() throws Exception{
        ClientRequestDto clientRequestDto = new ClientRequestDto("any_name", "any_phone",
            "any_mail@mail.com", "870.296.190-30", "69310-030", "any_address", null,
            "any_complement", ClientStatus.ACTIVE);
        String jsonBody = ParseHelper.parseObjectToString(clientRequestDto);
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post(ROUTE_NAME)
            .content(jsonBody)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON));
        String exceptionMessage = ParseHelper.getExceptionMessage(resultActions);
        resultActions.andExpect(MockMvcResultMatchers.status().isBadRequest());
        Assertions.assertEquals("Validation Exception", exceptionMessage);
    }

    @DisplayName("POST - handleAddClient should returns 400 if no complement is provided")
    @Test
    void handleAddClientShouldReturnsBadRequestIfNoComplementIsProvided() throws Exception{
        ClientRequestDto clientRequestDto = new ClientRequestDto("any_name", "any_phone",
            "any_mail@mail.com", "870.296.190-30", "69310-030", "any_address", 1,
            null, ClientStatus.ACTIVE);
        String jsonBody = ParseHelper.parseObjectToString(clientRequestDto);
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post(ROUTE_NAME)
            .content(jsonBody)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON));
        String exceptionMessage = ParseHelper.getExceptionMessage(resultActions);
        resultActions.andExpect(MockMvcResultMatchers.status().isBadRequest());
        Assertions.assertEquals("Validation Exception", exceptionMessage);
    }

    @DisplayName("POST - handleAddClient should returns 400 if no status is provided")
    @Test
    void handleAddClientShouldReturnsBadRequestIfNoStatusIsProvided() throws Exception{
        ClientRequestDto clientRequestDto = new ClientRequestDto("any_name", "any_phone",
            "any_mail@mail.com", "870.296.190-30", "69310-030", "any_address", 1,
            "any_complement", null);
        String jsonBody = ParseHelper.parseObjectToString(clientRequestDto);
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post(ROUTE_NAME)
            .content(jsonBody)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON));
        String exceptionMessage = ParseHelper.getExceptionMessage(resultActions);
        resultActions.andExpect(MockMvcResultMatchers.status().isBadRequest());
        Assertions.assertEquals("Validation Exception", exceptionMessage);
    }
}