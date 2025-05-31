package br.com.lucas.santos.workshop.controller;

import br.com.lucas.santos.workshop.business.service.AuthenticationService;
import br.com.lucas.santos.workshop.domain.dto.request.AuthenticationRequestDto;
import br.com.lucas.santos.workshop.domain.dto.request.ForgotEmailDto;

import br.com.lucas.santos.workshop.domain.entities.Role;
import br.com.lucas.santos.workshop.domain.entities.User;
import br.com.lucas.santos.workshop.factories.AuthenticationFactory;
import br.com.lucas.santos.workshop.infrastructure.adapters.db.UserRepository;
import br.com.lucas.santos.workshop.infrastructure.exceptions.InvalidCredentialsException;
import br.com.lucas.santos.workshop.utils.ParseHelper;
import jakarta.mail.Session;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Set;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("dev")
class AuthenticationControllerTest {

    private static final String ROUTE_NAME = "/signin";

    @MockitoBean
    private AuthenticationService authenticationService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @MockitoBean
    private JavaMailSender javaMailSender;


    private AuthenticationRequestDto authenticationRequestDto;
    private ForgotEmailDto forgotEmailDto;
    @BeforeEach
    void setup(){
        this.authenticationRequestDto = AuthenticationFactory.makeAuthenticationRequestDto();
        this.forgotEmailDto = AuthenticationFactory.makeForgotEmailDto();
    }

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
        String jsonBody = ParseHelper.parseObjectToString(new AuthenticationRequestDto("any_mail", "any_password"));
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post(ROUTE_NAME)
                .content(jsonBody)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
        );
        resultActions.andExpect(MockMvcResultMatchers.status().isBadRequest());
        Assertions.assertEquals("Validation Exception", ParseHelper.getExceptionMessage(resultActions));
    }

    @DisplayName("POST - handleAuthentication should returns 401 if an invalid password is provided")
    @Test
    void handleAuthenticationShouldReturnsUnauthorizedWhenInvalidPasswordIsProvided() throws Exception{
        Mockito.when(authenticationService.authenticate(Mockito.any(AuthenticationRequestDto.class)))
            .thenThrow(InvalidCredentialsException.class);
        String jsonBody = ParseHelper.parseObjectToString(authenticationRequestDto);
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post(ROUTE_NAME)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonBody)
        );
        resultActions.andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @DisplayName("POST - handleAuthentication should returns 200 if authentication successed")
    @Test
    void handleAuthenticationShouldReturnSuccessIfAuthenticationSuccesed()throws Exception{
        String jsonBody = ParseHelper.parseObjectToString(authenticationRequestDto);
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post(ROUTE_NAME)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonBody)
        );
        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @DisplayName("POST - handleForgotPassword should returns 400 if no email is provided")
    @Test
    void handleForgotPasswordShouldReturnsBadRequestIfNoEmailIsProvided() throws Exception{
        ForgotEmailDto forgotEmailDto = new ForgotEmailDto(null);
        String jsonBody = ParseHelper.parseObjectToString(forgotEmailDto);
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/forgotpassword")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonBody)
        );
        resultActions.andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @DisplayName("POST - handleForgotPassword should returns 400 if an invalid email is provided")
    @Test
    void handleForgotPasswordShouldReturnsBadRequestIfAnInvalidEmailIsProvided() throws  Exception{;
        String jsonBody = ParseHelper.parseObjectToString(new ForgotEmailDto("any_mail"));
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/forgotpassword")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonBody)
        );
        resultActions.andExpect(MockMvcResultMatchers.status().isBadRequest());
        Assertions.assertEquals("Validation Exception", ParseHelper.getExceptionMessage(resultActions));

    }

    @DisplayName("POST handleForgotPassword should returns 200 on sucessed")
    @Test
    void handleForgotPasswordShouldReturnsSuccessOnSuccess() throws Exception{
        MimeMessage mimiMessage =  new MimeMessage((Session) null);
        Mockito.when(javaMailSender.createMimeMessage()).thenReturn(mimiMessage);
        User user = User.builder().name("any_name").email(forgotEmailDto.email()).password("123").roles(Set.of(Role.builder().id(1L).name("ADMIN").build())).build();
        userRepository.add(user);
        String jsonBody = ParseHelper.parseObjectToString(forgotEmailDto);
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/forgotpassword")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonBody)
        );
        resultActions.andExpect(MockMvcResultMatchers.status().isNoContent());
    }

}