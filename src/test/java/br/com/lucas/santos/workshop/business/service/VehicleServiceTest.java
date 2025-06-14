package br.com.lucas.santos.workshop.business.service;

import br.com.lucas.santos.workshop.business.contractors.repositories.vehicle.DbLoadVehicleByPlate;
import br.com.lucas.santos.workshop.business.validators.VehicleValidator;
import br.com.lucas.santos.workshop.domain.dto.request.VehicleRequestDto;
import br.com.lucas.santos.workshop.domain.entities.Vehicle;
import br.com.lucas.santos.workshop.factories.VehicleFactory;
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
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;


@ExtendWith(MockitoExtension.class)
@ActiveProfiles("dev")
class VehicleServiceTest {

    @InjectMocks
    private VehicleService vehicleService;

    private VehicleRequestDto vehicleRequestDto;

    @Mock
    private DbLoadVehicleByPlate dbLoadVehicleByPlate;

    @Mock
    private VehicleValidator vehicleValidator;

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

    @DisplayName("add should calls vehicle with correct values")
    @Test
    void addShouldCallVehicleWithCorrectValue(){
        VehicleRequestDto vehicleRequestDto = VehicleFactory.makeVehicleRequestDto();
        Vehicle vehicle = VehicleFactory.makeVehicle(vehicleRequestDto);
        vehicle.setId(1L);
        Mockito.when(dbLoadVehicleByPlate.loadVehicleByPlate(Mockito.anyString())).thenReturn(Optional.of(vehicle));
        vehicleService.add(vehicleRequestDto);
        Mockito.verify(vehicleValidator).validate(Mockito.any());
    }


}