package br.com.lucas.santos.workshop.bunisses.service;


import br.com.lucas.santos.workshop.domain.dto.request.ClientRequestDto;
import br.com.lucas.santos.workshop.domain.dto.response.ClientResponseDto;
import br.com.lucas.santos.workshop.domain.entities.Client;
import br.com.lucas.santos.workshop.factories.ClientFactory;
import br.com.lucas.santos.workshop.infrastructure.adapters.db.ClientRepository;
import br.com.lucas.santos.workshop.infrastructure.exceptions.ResourceAlreadyExistsException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;


import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("dev")
class ClientServiceTest {

    @InjectMocks
    private ClientService clientService;

    @Mock
    private ClientRepository clientRepository;

    private ClientRequestDto clientRequestDto;



    private Client client;

    private final Pageable pageable = PageRequest.of(0, 12);
    @BeforeEach
    void setupValues(){
        this.clientRequestDto = ClientFactory.makeClientRequestDto();
        this.client = ClientFactory.makeClient(clientRequestDto);
    }

    @DisplayName(" add should throws ResourceAlreadyExistsException if client email already exists")
    @Test
    void addShouldThrowsResourceAlreadyExistsExceptionIfClientEmailAlreadyExists(){
        Mockito.when(clientRepository.loadClientByEmail(Mockito.any())).thenReturn(Optional.of(ClientFactory.makeClient(ClientFactory.makeClientRequestDto())));
        Assertions.assertThrows(ResourceAlreadyExistsException.class, () -> {
            clientService.add(clientRequestDto);
        });
    }

    @DisplayName("add should throws ResourceAlreadyExistsException if client cpf already exists")
    @Test
    void addShouldThrowsResourceAlreadyExistsExceptionIfClientEmailAlreadyExist(){
        Mockito.when(clientRepository.loadClientByEmail(Mockito.any())).thenReturn(Optional.empty());
        Mockito.when(clientRepository.loadClientByCode(Mockito.any())).thenReturn(Optional.of(ClientFactory.makeClient(ClientFactory.makeClientRequestDto())));
        Assertions.assertThrows(ResourceAlreadyExistsException.class, () -> {
            clientService.add(clientRequestDto);
        });
    }

    @DisplayName("add should  save an client when valid data is provided")
    @Test
    void addShouldReturnsAClientWhenValidDataIsProvided(){
        Mockito.when(clientRepository.loadClientByEmail(Mockito.any())).thenReturn(Optional.empty());
        Mockito.when(clientRepository.loadClientByCode(Mockito.any())).thenReturn(Optional.empty());
        Mockito.when(clientRepository.add(Mockito.any())).thenReturn(ClientFactory.makeClient(clientRequestDto));
        ClientResponseDto clientResponseDto = clientService.add(clientRequestDto);
        Assertions.assertNotNull(clientResponseDto);
        Assertions.assertNotNull(clientResponseDto.id());
    }

    @DisplayName("loadClients should load an page of clients on success")
    @Test
    void loadClientsShouldLoadAnPageOfClientsOnSuccess(){
        List<Client> clients = List.of(client);
        Page<Client> page = new PageImpl<>(clients, pageable, clients.size());
        Mockito.when(clientRepository.loadClient(pageable)).thenReturn(page);
        Page<ClientResponseDto> clientResponseDtoPage = clientService.loadClients(pageable);
        Assertions.assertEquals(1, clientResponseDtoPage.getNumberOfElements());
    }
}