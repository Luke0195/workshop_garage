package br.com.lucas.santos.workshop.bunisses.service;


import br.com.lucas.santos.workshop.domain.dto.request.ClientRequestDto;
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
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("dev")
class ClientServiceTest {

    @InjectMocks
    private ClientService clientService;

    @Mock
    private ClientRepository clientRepository;

    private ClientRequestDto clientRequestDto;

    @BeforeEach
    void setupValues(){
        this.clientRequestDto = ClientFactory.makeClientRequestDto();
    }

    @DisplayName(" add should throws ResourceAlreadyExistsException if client email already exists")
    @Test
    void addShouldThrowsResourceAlreadyExistsExceptionIfClientEmailAlreadyExists(){
        Assertions.assertThrows(ResourceAlreadyExistsException.class, () -> {
            clientService.add(clientRequestDto);
        });
    }
}