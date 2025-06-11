package br.com.lucas.santos.workshop.business.service;

import br.com.lucas.santos.workshop.domain.dto.request.VehicleRequestDto;
import br.com.lucas.santos.workshop.factories.VehicleFactory;
import br.com.lucas.santos.workshop.infrastructure.exceptions.ResourceAlreadyExistsException;
import br.com.lucas.santos.workshop.infrastructure.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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

    private VehicleRequestDto vehicleRequestDto;

    @BeforeEach
    void setup(){
        this.vehicleRequestDto = VehicleFactory.makeVehicleRequestDto();
    }

    @DisplayName("add should returns ResourceAlreadyExists when vehicle plate already exists")
    @Test
    void addShouldReturnsResourceNotFoundExceptionWhenVehicleNameWasNotFound(){
        Assertions.assertThrows(ResourceAlreadyExistsException.class, () -> {
             vehicleService.add(vehicleRequestDto);
        });
    }
}