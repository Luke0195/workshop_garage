package br.com.lucas.santos.workshop.business.service;


import br.com.lucas.santos.workshop.domain.dto.request.ClientRequestDto;
import br.com.lucas.santos.workshop.domain.dto.response.ClientResponseDto;
import br.com.lucas.santos.workshop.domain.entities.Client;
import br.com.lucas.santos.workshop.factories.ClientFactory;
import br.com.lucas.santos.workshop.infrastructure.adapters.db.ClientRepository;
import br.com.lucas.santos.workshop.infrastructure.exceptions.ResourceAlreadyExistsException;
import br.com.lucas.santos.workshop.infrastructure.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
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

    @DisplayName("loadClient should returns an empty list if no data was found")
    @Test
    void loadClientShouldReturnsAnEmptyListIfNoDataWasFound(){
        List<Client> clients = List.of();
        Page<Client> page = new PageImpl<>(clients, pageable, 0);
        Mockito.when(clientRepository.loadClient(pageable)).thenReturn(Page.empty());
        Page<ClientResponseDto> clientResponseDtoPage = clientService.loadClients(pageable);
        Assertions.assertEquals(0, clientResponseDtoPage.getNumberOfElements());
    }

    @DisplayName("load should throws ResourceNotFoundException when an invalid id is provided")
    @Test
    void loadShouldThrowsResourceNotFoundExceptionWhenAnInvalidIdIsProvided(){
        Mockito.when(clientRepository.loadById(Mockito.any())).thenThrow(ResourceNotFoundException.class);
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            clientService.load(999L);
        });
    }

    @DisplayName("load should returns a client when valid id is provided")
    @Test
    void loadShouldReturnsAnClientWhenValidIdIsProvided(){
        Mockito.when(clientRepository.loadById(Mockito.any())).thenReturn(Optional.of(client));
        ClientResponseDto clientResponseDto = clientService.load(1L);
        Assertions.assertNotNull(client);
        Assertions.assertNotNull(clientResponseDto.id());
    }

    @DisplayName("remove should throws ResourceNotFoundException when an invalid id is provided")
    @Test
    void removeShouldThrowsResourceNotFoundExceptionWhenAnInvalidIdIsProvided(){
        Mockito.doThrow(ResourceNotFoundException.class).when(clientRepository).deleteById(Mockito.anyLong());
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            clientService.remove(1L);
        });
    }

    @DisplayName("remove should delete an client on success")
    @Test
    void removeShouldDeleteAnClientOnSuccess(){
        Long clientId = 1L;
        Mockito.when(clientRepository.loadById(Mockito.anyLong())).thenReturn(Optional.of(client));
        clientService.remove(clientId);
        Mockito.verify(clientRepository).loadById(1L);
        Mockito.verify(clientRepository).deleteById(clientId);

    }
}