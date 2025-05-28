package br.com.lucas.santos.workshop.infrastructure.adapters.db;

import br.com.lucas.santos.workshop.domain.entities.Client;
import br.com.lucas.santos.workshop.factories.ClientFactory;
import br.com.lucas.santos.workshop.infrastructure.repository.ClientJpaRepository;
import org.junit.jupiter.api.Assertions;
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
        Client client = ClientFactory.makeClient(ClientFactory.makeClientRequestDto());
        Mockito.when(clientJpaRepository.findByEmail(Mockito.any())).thenReturn(Optional.of(client));
        Optional<Client> clientExists = clientRepository.loadClientByEmail("any_mail@mail.com");
        Assertions.assertNotNull(clientExists);
    }

    @DisplayName("add should returns an client when valid data is provided")
    @Test
    void addShouldReturnsClientResponseDtoWhenValidDataIsProvided(){
        Client client = ClientFactory.makeClient(ClientFactory.makeClientRequestDto());
        Mockito.when(clientJpaRepository.save(Mockito.any())).thenReturn(client);
        Client createdClient = clientRepository.add(client);
        Assertions.assertNotNull(createdClient);
        Assertions.assertEquals(1, createdClient.getId());
    }

    @DisplayName("loadClientByCode should return an empty data when no client was found")
    @Test
    void loadClientByCodeShouldReturnsAnEmptyDataWhenNoClientWasFound(){
        Mockito.when(clientJpaRepository.findByCpf(Mockito.any())).thenReturn(Optional.empty());
        Optional<Client> clientExists = clientRepository.loadClientByCode("any_code");
        Assertions.assertTrue(clientExists.isEmpty());
    }

    @DisplayName("loadClientByCode should return an user when valid email is provided")
    @Test
    void loadClientByCodeShouldReturnsAnUserWhenValidDataIsProvided(){
        Client client = ClientFactory.makeClient(ClientFactory.makeClientRequestDto());
        Mockito.when(clientJpaRepository.findByCpf(Mockito.any())).thenReturn(Optional.of(client));
        Optional<Client> clientExists = clientRepository.loadClientByCode("any_code");
        Assertions.assertNotNull(clientExists);
    }
}