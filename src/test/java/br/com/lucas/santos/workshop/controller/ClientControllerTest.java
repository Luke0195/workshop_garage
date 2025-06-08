package br.com.lucas.santos.workshop.controller;


import br.com.lucas.santos.workshop.business.contractors.repositories.client.*;
import br.com.lucas.santos.workshop.business.service.AuthenticationService;
import br.com.lucas.santos.workshop.business.service.ClientService;
import br.com.lucas.santos.workshop.domain.dto.request.AuthenticationRequestDto;
import br.com.lucas.santos.workshop.domain.dto.request.ClientRequestDto;
import br.com.lucas.santos.workshop.domain.dto.request.UserRequestDto;
import br.com.lucas.santos.workshop.domain.dto.response.AuthenticationResponseDto;
import br.com.lucas.santos.workshop.domain.dto.response.ClientResponseDto;
import br.com.lucas.santos.workshop.domain.entities.Client;
import br.com.lucas.santos.workshop.domain.entities.User;
import br.com.lucas.santos.workshop.domain.entities.enums.ClientStatus;
import br.com.lucas.santos.workshop.factories.ClientFactory;
import br.com.lucas.santos.workshop.factories.UserFactory;
import br.com.lucas.santos.workshop.infrastructure.adapters.cryphtography.BcryptAdapter;
import br.com.lucas.santos.workshop.infrastructure.adapters.cryphtography.JwtAdapter;
import br.com.lucas.santos.workshop.infrastructure.adapters.db.UserRepository;
import br.com.lucas.santos.workshop.infrastructure.exceptions.ResourceNotFoundException;
import br.com.lucas.santos.workshop.utils.AuthenticationUtil;
import br.com.lucas.santos.workshop.utils.ParseUtil;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import java.util.Optional;


@ActiveProfiles("dev")
@WebMvcTest(ClientController.class)
@AutoConfigureMockMvc(addFilters = false)
class ClientControllerTest {

    private static final String ROUTE_NAME = "/client";

    @Autowired
    private MockMvc mockMvc;

    private ClientRequestDto clientRequestDto;

    @MockitoBean
    private ClientService clientService;

    @MockitoBean
    private AuthenticationService authenticationService;
    @MockitoBean
    private DbLoadClientById dbLoadClientById;

    private User user;
    @MockitoBean
    private BcryptAdapter bcryptAdapter;
    @MockitoBean
    private UserRepository userRepository;
    @MockitoBean
    private JwtAdapter jwtAdapter;
    private String token;

    @MockitoBean
    private JwtDecoder jwtDecoder;


    @MockitoBean
    private DbLoadClientByEmail dbLoadClientByEmail;

    @MockitoBean
    private DbLoadClientByCode dbLoadClientByCode;


    @MockitoBean
    private DbAddClient dbAddClient;


    @MockitoBean
    private DbLoadClient dbLoadClient;

    private Client client;




    @BeforeEach
    void setup(){
        UserRequestDto userRequestDto = UserFactory.makeUserRequestDto();
        this.user = UserFactory.makeUser(userRequestDto);
        this.client = ClientFactory.makeClient(ClientFactory.makeClientRequestDto());
        Mockito.when(userRepository.loadUserByEmail(Mockito.any())).thenReturn(Optional.of(user));
        Mockito.when(bcryptAdapter.compare(Mockito.anyString(), Mockito.anyString())).thenReturn(true);
        Mockito.when(jwtAdapter.generateToken(Mockito.anyString())).thenReturn(new AuthenticationResponseDto("any_token", 300L));

        // Mock do authenticate para retornar um AuthenticationResponseDto válido
        Mockito.when(authenticationService.authenticate(Mockito.any(AuthenticationRequestDto.class)))
            .thenReturn(new AuthenticationResponseDto("any_token", 300L));

        this.token = authenticationService.authenticate(new AuthenticationRequestDto("any_mail@mail.com", "any_password")).token();

        AuthenticationUtil.makeGenerateFakeToken(jwtDecoder);
    }

    @DisplayName("POST -  handleAddClient should returns 400 if no client name is provided")
    @Test
    void handleAddClientShouldReturnsBadRequestIfNoClientNameIsProvided() throws Exception{
        System.out.println("TOKEN GERADO" + token);
        ClientRequestDto clientRequestDto =  new ClientRequestDto(null, "any_phone", "any_mail@mail.com", "any_cpf",
            "any_zipcode", "any_address", 1, "any_complement", ClientStatus.ACTIVE);
        String jsonBody = ParseUtil.parseObjectToString(clientRequestDto);
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post(ROUTE_NAME)
            .header("Authorization", "Bearer " + token)
            .content(jsonBody)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
        );
       String exceptionMessage = ParseUtil.getExceptionMessage(resultActions);
        resultActions.andExpect(MockMvcResultMatchers.status().isBadRequest());
        Assertions.assertEquals("Validation Exception", exceptionMessage);
    }

    @DisplayName("POST - handleAddClient should returns 400 if no phone is provided")
    @Test
    void handleAddClientShouldReturnsBadRequestIfNoPhoneIsProvided() throws Exception{
        ClientRequestDto clientRequestDto = new ClientRequestDto("any_name", null,"any_mail@mail.com", "870.296.190-30",
            "69310-030", "any_address", 1, "any_complement", ClientStatus.ACTIVE );
        String jsonBody = ParseUtil.parseObjectToString(clientRequestDto);
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post(ROUTE_NAME)
            .header("Authorization", "Bearer " + token)
            .content(jsonBody)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON));
        String exceptionMessage = ParseUtil.getExceptionMessage(resultActions);
        resultActions.andExpect(MockMvcResultMatchers.status().isBadRequest());
        Assertions.assertEquals("Validation Exception", exceptionMessage);
    }

    @DisplayName("POST - handleAddClient should returns 400 if no email is provided")
    @Test
    void handleAddClientShouldReturnsBadRequestIfNoEmailIsProvided() throws Exception{
        ClientRequestDto clientRequestDto = new ClientRequestDto("any_name", "any_phone",null, "870.296.190-30",
            "69310-030", "any_address", 1, "any_complement", ClientStatus.ACTIVE );
        String jsonBody = ParseUtil.parseObjectToString(clientRequestDto);
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post(ROUTE_NAME)
            .header("Authorization", "Bearer " + token)
            .content(jsonBody)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON));
        String exceptionMessage = ParseUtil.getExceptionMessage(resultActions);
        resultActions.andExpect(MockMvcResultMatchers.status().isBadRequest());
        Assertions.assertEquals("Validation Exception", exceptionMessage);
    }

    @DisplayName("POST - handleAddUser should returns 400 if an invalid email is provided")
    @Test
    void handleAddUserShouldReturnsBadRequestIfAnInvalidEmailsIsProvided() throws Exception{
        ClientRequestDto clientRequestDto = new ClientRequestDto("any_name", "any_phone","any_mail", "870.296.190-30",
            "69310-030", "any_address", 1, "any_complement", ClientStatus.ACTIVE );
        String jsonBody = ParseUtil.parseObjectToString(clientRequestDto);
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post(ROUTE_NAME)
            .header("Authorization", "Bearer " + token)
            .content(jsonBody)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON));
        String exceptionMessage = ParseUtil.getExceptionMessage(resultActions);
        resultActions.andExpect(MockMvcResultMatchers.status().isBadRequest());
        Assertions.assertEquals("Validation Exception", exceptionMessage);
    }


    @DisplayName("POST - handleAddClient should returns 400 if no cpf is provided")
    @Test
    void handleAddClientShouldReturnsBadRequestIfNoCpfIsProvided() throws Exception{
        ClientRequestDto clientRequestDto = new ClientRequestDto("any_name", "any_phone","any_mail@mail.com", null,
            "69310-030", "any_address", 1, "any_complement", ClientStatus.ACTIVE );
        String jsonBody = ParseUtil.parseObjectToString(clientRequestDto);
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post(ROUTE_NAME)
            .header("Authorization", "Bearer " + token)
            .content(jsonBody).accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON));
        String exceptionMessage = ParseUtil.getExceptionMessage(resultActions);
        resultActions.andExpect(MockMvcResultMatchers.status().isBadRequest());
        Assertions.assertEquals("Validation Exception", exceptionMessage);
    }

    @DisplayName("POST - handleAddClient should returns 400 if an invalid cpf is provided")
    @Test
    void handleAddClientShouldReturnsBadRequestIfAnInvalidCpfProvided() throws Exception{
        ClientRequestDto clientRequestDto = new ClientRequestDto("any_name", "any_phone","any_mail@mail.com", "87029619",
            "69310-030", "any_address", 1, "any_complement", ClientStatus.ACTIVE );
        String jsonBody = ParseUtil.parseObjectToString(clientRequestDto);
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post(ROUTE_NAME)
            .header("Authorization", "Bearer " + token)
            .content(jsonBody)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON));
        resultActions.andExpect(MockMvcResultMatchers.status().isBadRequest());

    }

    @DisplayName("POST - handleAddClient should returns 400 if no zipcode is provided")
    @Test
    void handleAddClientShouldReturnsBadRequestIfNoZipCodeIsProvided() throws Exception{
        ClientRequestDto clientRequestDto = new ClientRequestDto("any_name", "any_phone",
            "any_mail@mail.com", "870.296.190-30", null, "any_address", 1,
            "any_complement", ClientStatus.ACTIVE);
        String jsonBody = ParseUtil.parseObjectToString(clientRequestDto);
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post(ROUTE_NAME)
            .header("Authorization", "Bearer " + token)
            .content(jsonBody)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON));
        String exceptionMessage = ParseUtil.getExceptionMessage(resultActions);
        resultActions.andExpect(MockMvcResultMatchers.status().isBadRequest());
        Assertions.assertEquals("Validation Exception", exceptionMessage);
    }

    @DisplayName("POST - handleAddClient should returns 400 if an  invalid zipcode is provided")
    @Test
    void handleAddClientShouldReturnsBadRequestIfAnInvalidZipCodeIsProvided() throws Exception{
        ClientRequestDto clientRequestDto = new ClientRequestDto("any_name", "any_phone",
            "any_mail@mail.com", "870.296.190-30", null, "any_address", 1,
            "any_complement", ClientStatus.ACTIVE);
        String jsonBody = ParseUtil.parseObjectToString(clientRequestDto);
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post(ROUTE_NAME)
            .header("Authorization", "Bearer " + token)
            .content(jsonBody)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON));
        String exceptionMessage = ParseUtil.getExceptionMessage(resultActions);
        resultActions.andExpect(MockMvcResultMatchers.status().isBadRequest());
        Assertions.assertEquals("Validation Exception", exceptionMessage);
    }

    @DisplayName("POST - handleAddClient should returns 400 if no address is provided")
    @Test
    void handleAddClientShouldReturnsBadRequestIfNoAddressIsProvided() throws Exception{
        ClientRequestDto clientRequestDto = new ClientRequestDto("any_name", "any_phone",
            "any_mail@mail.com", "870.296.190-30", "69310-030", null, 1,
            "any_complement", ClientStatus.ACTIVE);
        String jsonBody = ParseUtil.parseObjectToString(clientRequestDto);
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post(ROUTE_NAME)
            .header("Authorization", "Bearer " + token)
            .content(jsonBody)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON));
        String exceptionMessage = ParseUtil.getExceptionMessage(resultActions);
        resultActions.andExpect(MockMvcResultMatchers.status().isBadRequest());
        Assertions.assertEquals("Validation Exception", exceptionMessage);
    }

    @DisplayName("POST - handleAddClient should returns 400 if no number is provided")
    @Test
    void handleAddClientShouldReturnsBadRequestIfNoNumberIsProvided() throws Exception{
        ClientRequestDto clientRequestDto = new ClientRequestDto("any_name", "any_phone",
            "any_mail@mail.com", "870.296.190-30", "69310-030", "any_address", null,
            "any_complement", ClientStatus.ACTIVE);
        String jsonBody = ParseUtil.parseObjectToString(clientRequestDto);
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post(ROUTE_NAME)
            .header("Authorization", "Bearer " + token)
            .content(jsonBody)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON));
        String exceptionMessage = ParseUtil.getExceptionMessage(resultActions);
        resultActions.andExpect(MockMvcResultMatchers.status().isBadRequest());
        Assertions.assertEquals("Validation Exception", exceptionMessage);
    }

    @DisplayName("POST - handleAddClient should returns 400 if no complement is provided")
    @Test
    void handleAddClientShouldReturnsBadRequestIfNoComplementIsProvided() throws Exception{
        ClientRequestDto clientRequestDto = new ClientRequestDto("any_name", "any_phone",
            "any_mail@mail.com", "870.296.190-30", "69310-030", "any_address", 1,
            null, ClientStatus.ACTIVE);
        String jsonBody = ParseUtil.parseObjectToString(clientRequestDto);
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post(ROUTE_NAME)
            .header("Authorization", "Bearer " + token)
            .content(jsonBody)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON));
        String exceptionMessage = ParseUtil.getExceptionMessage(resultActions);
        resultActions.andExpect(MockMvcResultMatchers.status().isBadRequest());
        Assertions.assertEquals("Validation Exception", exceptionMessage);
    }

    @DisplayName("POST - handleAddClient should returns 400 if no status is provided")
    @Test
    void handleAddClientShouldReturnsBadRequestIfNoStatusIsProvided() throws Exception{
        ClientRequestDto clientRequestDto = new ClientRequestDto("any_name", "any_phone",
            "any_mail@mail.com", "870.296.190-30", "69310-030", "any_address", 1,
            "any_complement", null);
        String jsonBody = ParseUtil.parseObjectToString(clientRequestDto);
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post(ROUTE_NAME)
            .header("Authorization", "Bearer " + token)
            .content(jsonBody)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON));
        String exceptionMessage = ParseUtil.getExceptionMessage(resultActions);
        resultActions.andExpect(MockMvcResultMatchers.status().isBadRequest());
        Assertions.assertEquals("Validation Exception", exceptionMessage);
    }

    @DisplayName("POST - handleAddClient should returns 201 when valid data is provided")
    @Test
    void handleAddClientShouldReturnsWhenValidDataIsProvided() throws Exception{
        Mockito.when(clientService.add(Mockito.any())).thenReturn(ClientResponseDto.makeClientResponseDto(client));
        this.clientRequestDto = ClientFactory.makeClientRequestDto();
        String jsonBody = ParseUtil.parseObjectToString(clientRequestDto);
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post(ROUTE_NAME)
            .header("Authorization", "Bearer " + token)
            .content(jsonBody)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON));
        resultActions.andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @DisplayName("GET - handleLoadClient should returns 200 on success")
    @Test
    void handleLoadClientShouldReturnsOkOnSuccess() throws  Exception{
        ResultActions resultActions = mockMvc
            .perform(MockMvcRequestBuilders.get("/clients")
                .header("Authorization", "Bearer " + token)
                .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON));
        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @DisplayName("GET - handleLoadClientById should returns 200 on success")
    @Test
    void handleLoadClientByIdShouldReturnsOkWhenValidIdIsProvided() throws Exception {
        ResultActions resultActions = mockMvc.perform(getWithAuth("/clients/{id}", 1L));
        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @DisplayName("GET - handleLoadClientByIdShouldReturns 404 when an invalid id is provided")
    @Test
    void handleLoadClientByIdShouldReturnsNotFoundWhenAnInvalidIdIsProvided() throws Exception{
        Mockito.when(clientService.load(1L)).thenThrow( new ResourceNotFoundException("This client id was not found"));
        ResultActions resultActions = mockMvc.perform(getWithAuth("/clients/{id}", 1L));
        resultActions.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @DisplayName("DELETE - handleDeleteClient should returns 404 if an invalid id is provided")
    @Test
    void handleDeleteClientShouldReturnsNotFoundWhenInvalidIdIsProvided() throws Exception{
       Mockito.doThrow(ResourceNotFoundException.class).when(clientService).remove(Mockito.anyLong());
       ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.delete("/clients/{id}", 1L)
            .header("Authorization", "Bearer " + token)
            .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON));
       resultActions.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @DisplayName("DELETE - handleDeleteClient should returns 204 when valid id is provided")
    @Test
    void handleDeleteClientShouldReturnsNoContentWhenValidIdIsProvided()throws Exception{
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.delete("/clients/{id}", 1L)
            .header("Authorization", "Bearer " + token)
            .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON));
        resultActions.andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    private RequestBuilder getWithAuth(String urlTemplate, Object... uriVars) {
        return MockMvcRequestBuilders.get(urlTemplate, uriVars)
            .header("Authorization", "Bearer " + token)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON);
    }


}