package br.com.lucas.santos.workshop.business.service;

import br.com.lucas.santos.workshop.infrastructure.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;


@ExtendWith(MockitoExtension.class)
@ActiveProfiles("dev")
class VehicleServiceTest {

    @InjectMocks
    private VehicleService vehicleService;


    @DisplayName("add should returns ResourceNotFoundException when vehicle name was not found")
    @Test
    void addShouldReturnsResourceNotFoundExceptionWhenVehicleNameWasNotFound(){
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
             vehicleService.
        });
    }
}