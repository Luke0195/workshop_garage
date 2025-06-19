package br.com.lucas.santos.workshop.infrastructure.adapters.db;

import br.com.lucas.santos.workshop.domain.entities.Client;
import br.com.lucas.santos.workshop.factories.ClientFactory;
import br.com.lucas.santos.workshop.infrastructure.exceptions.ResourceNotFoundException;
import br.com.lucas.santos.workshop.infrastructure.repository.ClientJpaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("dev")
class ClientRepositoryTest {

    @InjectMocks
    private ClientRepository clientRepository;

    @Mock
    private ClientJpaRepository clientJpaRepository;

    private Client client;


    @BeforeEach
    void setup(){
        this.client = ClientFactory.makeClient(ClientFactory.makeClientRequestDto());
    }

    @DisplayName("loadClientByEmail should return an empty data when no client was found")
    @Test
    void loadClientByEmailShouldReturnsAnEmptyDataWhenNoClientWasFound(){
        Mockito.when(clientJpaRepository.findByEmail(Mockito.any())).thenReturn(Optional.empty());
        Optional<Client> clientExists = clientRepository.loadClientByEmail("any_mail@mail.com");
        Assertions.assertTrue(clientExists.isEmpty());
    }

    @DisplayName("loadClientByEmail should return an user when valid email is provided")
    @Test
    void loadClientByEmailShouldReturnsAnUserWhenValidDataIsProvided(){

        Mockito.when(clientJpaRepository.findByEmail(Mockito.any())).thenReturn(Optional.of(client));
        Optional<Client> clientExists = clientRepository.loadClientByEmail("any_mail@mail.com");
        Assertions.assertNotNull(clientExists);
    }

    @DisplayName("add should returns an client when valid data is provided")
    @Test
    void addShouldReturnsClientResponseDtoWhenValidDataIsProvided(){
        Mockito.when(clientJpaRepository.save(Mockito.any())).thenReturn(client);
        Client createdClient = clientRepository.add(client);
        Assertions.assertNotNull(createdClient);
        Assertions.assertEquals(1, createdClient.getId());
    }

    @DisplayName("loadClientByCode should throws ResourceNotFoundException if no client was found.")
    @Test
    void loadClientByCodeShouldReturnsAnEmptyDataWhenNoClientWasFound(){
        Mockito.when(clientJpaRepository.findByCpf(Mockito.anyString())).thenThrow(ResourceNotFoundException.class);
        Assertions.assertThrows(ResourceNotFoundException.class, () ->{
            clientRepository.loadClientByCode("any_code");
        });
    }

    @DisplayName("loadClientByCode should return an user when valid email is provided")
    @Test
    void loadClientByCodeShouldReturnsAnUserWhenValidDataIsProvided(){
        Mockito.when(clientJpaRepository.findByCpf(Mockito.any())).thenReturn(Optional.of(client));
        Client result = clientRepository.loadClientByCode("any_code");
        Assertions.assertNotNull(result);
    }

    @DisplayName("loadClientByCode should throw ResourceNotFoundException if no id was found")
    @Test
    void  loadClientByIdShouldReturnsAnOptionalWithEmptyValueWhenValidIdIsProvided(){
        Mockito.lenient().when(clientJpaRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        Client client = clientRepository.loadById(1L);
        Assertions.assertNull(client);

    }

    @DisplayName("loadClientById should returns an client when valid id is provided")
    @Test
    void loadClientByIdShouldReturnsAnClientWhenValidIdIsProvided(){
        Mockito.when(clientJpaRepository.findById(Mockito.any())).thenReturn(Optional.of(client));
        Client result = clientRepository.loadById(1L);
        Assertions.assertNotNull(result);
        Assertions.assertNotNull(result.getId());
    }

    @DisplayName("update client should update an client when valid data is provided")
    @Test
    void updateClientShouldUpdateClientWhenValidDataIsDataIsProvided(){
        Client client = ClientFactory.makeClient(ClientFactory.makeClientRequestDto());
        Mockito.when(clientJpaRepository.save(Mockito.any())).thenReturn(client);
        client.setName("Lucas");
        client = clientRepository.update(client);
        Assertions.assertNotNull(client);
        Assertions.assertEquals("Lucas", client.getName());
    }

}