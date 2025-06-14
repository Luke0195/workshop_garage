package br.com.lucas.santos.workshop.business.validators;

import br.com.lucas.santos.workshop.domain.entities.Client;
import br.com.lucas.santos.workshop.factories.ClientFactory;
import br.com.lucas.santos.workshop.infrastructure.exceptions.ValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class ClientValidatorTest {

    @InjectMocks
    private ClientValidator clientValidator;

    private Client client;

    @BeforeEach
    void setup(){
        this.client = ClientFactory.makeClient(ClientFactory.makeClientRequestDto());
    }

    @DisplayName("validate should throws ValidationException when no client was found")
    @Test
    void validateShouldThrowsValidationExceptionWhenNoClientWasFound(){
        client.setId(null);
        Assertions.assertThrows(ValidationException.class, () -> {
            clientValidator.validate(client);
        });
    }

    @DisplayName("validate should do nothing when an valid valid client is provided")
    @Test
    void validateShouldDoNothingWhenValidClientIsProvided(){
        clientValidator.validate(client);
        Assertions.assertDoesNotThrow(() -> clientValidator.validate(client));
    }
}