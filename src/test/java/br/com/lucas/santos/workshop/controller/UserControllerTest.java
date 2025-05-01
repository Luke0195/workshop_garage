package br.com.lucas.santos.workshop.controller;

import br.com.lucas.santos.workshop.dto.request.UserRequestDto;
import br.com.lucas.santos.workshop.factories.UserFactory;
import br.com.lucas.santos.workshop.utils.UtilFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("dev")
class UserControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

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
        String jsonBody = objectMapper.writeValueAsString(userRequestDto);
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/user")
                .content(jsonBody)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
        );
        resultActions.andExpect(MockMvcResultMatchers.status().isBadRequest());
        Assertions.assertEquals(UtilFactory.getExceptionMessage(resultActions), "Validation Exception");
    }


}