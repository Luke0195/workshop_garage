package br.com.lucas.santos.workshop.controller;

import br.com.lucas.santos.workshop.business.service.UserService;
import br.com.lucas.santos.workshop.domain.entities.User;
import br.com.lucas.santos.workshop.domain.dto.request.UserRequestDto;
import br.com.lucas.santos.workshop.factories.UserFactory;
import br.com.lucas.santos.workshop.utils.ParseUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("dev")
class UserControllerTest {

    private static final String ROUTE_NAME = "/user";

    @Autowired
    private MockMvc mockMvc;

    private UserRequestDto userRequestDto;

    @MockitoBean
    private UserService userService;

    @BeforeEach
    void setup(){
        this.userRequestDto = UserFactory.makeUserRequestDto();
    }


    @DisplayName("POST- should returns 400 if no name is provided")
    @Test
    void handleAddUserShouldReturnsBadRequestIfNoNameIsProvided() throws Exception{
        UserRequestDto userRequestDto = new UserRequestDto(null, "any_name@mail.com",
                "any_password", this.userRequestDto.roles());
        String jsonBody = ParseUtil.parseObjectToString(userRequestDto);
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post(ROUTE_NAME)
                .content(jsonBody)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
        );
        Assertions.assertEquals("Validation Exception", ParseUtil.getExceptionMessage(resultActions));
        resultActions.andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @DisplayName("POST - should returns 400 is no email is provided")
    @Test
    void handleAddUserShouldReturnsBadRequestIfNoEmailIsProvided() throws Exception{
        UserRequestDto userRequestDto = new UserRequestDto("any_name", "", "any_password",
                this.userRequestDto.roles());
        String jsonBody = ParseUtil.parseObjectToString(userRequestDto);
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post(ROUTE_NAME)
                .content(jsonBody)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
        );
        resultActions.andExpect(MockMvcResultMatchers.status().isBadRequest());
        Assertions.assertEquals("Validation Exception", ParseUtil.getExceptionMessage(resultActions));
    }


    @DisplayName("POST - should returns 400 if no roles is provided")
    @Test
    void handleAddUSerShouldReturnsBadRequestIfNoRoleAreProvided() throws Exception{
        UserRequestDto userRequestDto = new UserRequestDto("any_name", "any_mail@mail.com",
            "any_password", null);
        String jsonBody = ParseUtil.parseObjectToString(userRequestDto);
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post(ROUTE_NAME)
            .content(jsonBody)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
        );
        resultActions.andExpect(MockMvcResultMatchers.status().isBadRequest());
        Assertions.assertEquals("Validation Exception", ParseUtil.getExceptionMessage(resultActions));
    }

    @DisplayName("POST - should returns 400 if an invalid email is provided")
    @Test
    void handleAddUserShouldReturnsBadRequestIfAnInvalidEmailIsProvided() throws Exception{
        UserRequestDto userRequestDto = new UserRequestDto("any_name", "lucas", "any_password",
                this.userRequestDto.roles());
        String jsonBody = ParseUtil.parseObjectToString(userRequestDto);
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post(ROUTE_NAME)
                .content(jsonBody)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
        );
        resultActions.andExpect(MockMvcResultMatchers.status().isBadRequest());
        Assertions.assertEquals("Validation Exception", ParseUtil.getExceptionMessage(resultActions));
    }




    @DisplayName("POST - should return 400 if password does not have a min size")
    @Test
    void handleAddUserShouldReturnsBadRequestIfAnInvalidPasswordDoesNotHaveAMinSize() throws Exception{
        UserRequestDto userRequestDto = new UserRequestDto("any_name", "any_mail@mail.com", "hi",
                this.userRequestDto.roles());
        String jsonBody = ParseUtil.parseObjectToString(userRequestDto);
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post(ROUTE_NAME)
                .content(jsonBody)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
        );
        resultActions.andExpect(MockMvcResultMatchers.status().isBadRequest());
        Assertions.assertEquals("Validation Exception", ParseUtil.getExceptionMessage(resultActions));
    }

    @DisplayName("POST - should return 400 if password has more that max size")
    @Test
    void handleAddUserShouldReturnsBadRequestIfAnInvalidPasswordHasMoreThatTheMaxSize() throws Exception{
        UserRequestDto userRequestDto = new UserRequestDto("any_name", "any_mail@mail.com",
                "hihihihihihihiihihiihih", this.userRequestDto.roles());
        String jsonBody = ParseUtil.parseObjectToString(userRequestDto);
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post(ROUTE_NAME)
                .content(jsonBody)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
        );
        resultActions.andExpect(MockMvcResultMatchers.status().isBadRequest());
        Assertions.assertEquals("Validation Exception", ParseUtil.getExceptionMessage(resultActions));
    }


    @DisplayName("Should calls UserService add with correct values")
    @Test
    void handleAddUserShouldCallsAddWithCorrectValues() throws Exception{
        UserRequestDto userRequestDto = new UserRequestDto("any_name", "anymail@mail.com",
            "any_password", this.userRequestDto.roles());
        User user = UserFactory.makeUser(userRequestDto);
        Mockito.when(userService.add(userRequestDto)).thenReturn(UserFactory.makeUserResponseDto(user));
        String jsonBody = ParseUtil.parseObjectToString(userRequestDto);
       mockMvc.perform(MockMvcRequestBuilders.post(ROUTE_NAME)
                .content(jsonBody)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
        );
        Mockito.verify(userService).add(userRequestDto);
    }

    @DisplayName("POST - Should returns 201 when valid data is provided")
    @Test
    void handleAddUserShouldReturnsCreatedWhenValidDataIsProvided() throws Exception{
        UserRequestDto userRequestDto = new UserRequestDto("any_name", "anymail@mail.com",
            "any_password", this.userRequestDto.roles());
        User user = UserFactory.makeUser(userRequestDto);
        Mockito.when(userService.add(userRequestDto)).thenReturn(UserFactory.makeUserResponseDto(user));
        String jsonBody = ParseUtil.parseObjectToString(userRequestDto);
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post(ROUTE_NAME)
                .content(jsonBody)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
        );
        resultActions.andExpect(MockMvcResultMatchers.status().isCreated());
    }



}