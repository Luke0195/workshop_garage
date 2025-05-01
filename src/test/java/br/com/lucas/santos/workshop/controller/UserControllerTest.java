package br.com.lucas.santos.workshop.controller;

import br.com.lucas.santos.workshop.dto.request.UserRequestDto;
import br.com.lucas.santos.workshop.factories.UserFactory;
import br.com.lucas.santos.workshop.utils.UtilFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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
class UserControllerTest {

    private static final String ROUTE_NAME = "/user";


    @Autowired
    private MockMvc mockMvc;

    private UserRequestDto userRequestDto;


    @BeforeEach
    void setup(){
        this.userRequestDto = UserFactory.makeUserRequestDto();
    }


    @DisplayName("POST- should returns 400 if no name is provided")
    @Test
    void handleAddUserShouldReturnsBadRequestIfNoNameIsProvided() throws Exception{
        UserRequestDto userRequestDto = new UserRequestDto(null, "any_name@mail.com", "any_password");
        String jsonBody = UtilFactory.parseObjectToString(userRequestDto);
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post(ROUTE_NAME)
                .content(jsonBody)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
        );
        Assertions.assertEquals("Validation Exception", UtilFactory.getExceptionMessage(resultActions));
        resultActions.andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @DisplayName("POST - should returns 400 is no email is provided")
    @Test
    void handleAddUserShouldReturnsBadRequestIfNoEmailIsProvided() throws Exception{
        UserRequestDto userRequestDto = new UserRequestDto("any_name", "", "any_password");
        String jsonBody = UtilFactory.parseObjectToString(userRequestDto);
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post(ROUTE_NAME)
                .content(jsonBody)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
        );
        resultActions.andExpect(MockMvcResultMatchers.status().isBadRequest());
        Assertions.assertEquals("Validation Exception", UtilFactory.getExceptionMessage(resultActions));
    }

    @DisplayName("POST - should returns 400 if an invalid email is provided")
    @Test
    void handleAddUserShouldReturnsBadRequestIfAnInvalidEmailIsProvided() throws Exception{
        UserRequestDto userRequestDto = new UserRequestDto("any_name", "lucas", "any_password");
        String jsonBody = UtilFactory.parseObjectToString(userRequestDto);
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post(ROUTE_NAME)
                .content(jsonBody)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
        );
        resultActions.andExpect(MockMvcResultMatchers.status().isBadRequest());
        Assertions.assertEquals("Validation Exception", UtilFactory.getExceptionMessage(resultActions));
    }

    @DisplayName("POST - should return 400 if no password is provided")
    @Test
    void handleAddUserShouldReturnsBadRequestIfAnInvalidPasswordIsProvided() throws Exception{
        UserRequestDto userRequestDto = new UserRequestDto("any_name", "lucas@mail.com", "");
        String jsonBody = UtilFactory.parseObjectToString(userRequestDto);
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post(ROUTE_NAME)
                .content(jsonBody)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
        );
        resultActions.andExpect(MockMvcResultMatchers.status().isBadRequest());
        Assertions.assertEquals("Validation Exception", UtilFactory.getExceptionMessage(resultActions));
    }


    @DisplayName("POST - should return 400 if password does not have a min size")
    @Test
    void handleAddUserShouldReturnsBadRequestIfAnInvalidPasswordDoesNotHaveAMinSize() throws Exception{
        UserRequestDto userRequestDto = new UserRequestDto("any_name", "lucas@mail.com", "hi");
        String jsonBody = UtilFactory.parseObjectToString(userRequestDto);
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post(ROUTE_NAME)
                .content(jsonBody)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
        );
        resultActions.andExpect(MockMvcResultMatchers.status().isBadRequest());
        Assertions.assertEquals("Validation Exception", UtilFactory.getExceptionMessage(resultActions));
    }



}