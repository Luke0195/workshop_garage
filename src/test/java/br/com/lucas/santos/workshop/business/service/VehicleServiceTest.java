package br.com.lucas.santos.workshop.business.service;

import br.com.lucas.santos.workshop.business.contractors.repositories.client.DbLoadClientById;
import br.com.lucas.santos.workshop.business.contractors.repositories.vehicle.DbAddVehicle;
import br.com.lucas.santos.workshop.business.contractors.repositories.vehicle.DbLoadVehicleByPlate;
import br.com.lucas.santos.workshop.business.contractors.validators.client.ValidateClientExistsById;
import br.com.lucas.santos.workshop.business.contractors.validators.vehicle.ValidateIfPlateExists;
import br.com.lucas.santos.workshop.domain.dto.request.VehicleRequestDto;
import br.com.lucas.santos.workshop.domain.dto.response.VehicleResponseDto;
import br.com.lucas.santos.workshop.domain.entities.Client;
import br.com.lucas.santos.workshop.domain.entities.Vehicle;
import br.com.lucas.santos.workshop.factories.ClientFactory;
import br.com.lucas.santos.workshop.factories.VehicleFactory;
import br.com.lucas.santos.workshop.infrastructure.exceptions.ResourceAlreadyExistsException;

import jakarta.xml.bind.ValidationException;
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
    private DbLoadClientById dbLoadClientById;
    @Mock
    private DbAddVehicle dbAddVehicle;

    @Mock
    private ValidateIfPlateExists validateIfPlateExists;

    @Mock
    private ValidateClientExistsById validateClientExistsById;

    private Client client;

    @BeforeEach
    void setup(){
        this.vehicleRequestDto = VehicleFactory.makeVehicleRequestDto();
        this.client = ClientFactory.makeClient(ClientFactory.makeClientRequestDto());

    }


    @DisplayName("add should calls vehicle with correct values")
    @Test
    void addShouldCallVehicleWithCorrectValue(){
        VehicleRequestDto vehicleRequestDto = VehicleFactory.makeVehicleRequestDto();
        Vehicle vehicle = VehicleFactory.makeVehicle(vehicleRequestDto);
        vehicle.setId(1L);
        Mockito.when(dbLoadVehicleByPlate.loadVehicleByPlate(Mockito.anyString())).thenReturn(Optional.of(vehicle));
        Mockito.when(dbLoadClientById.loadById(Mockito.anyLong())).thenReturn(client);
        Mockito.when(dbAddVehicle.add(Mockito.any())).thenReturn(vehicle);
        vehicleService.add(vehicleRequestDto);
        Mockito.verify(validateIfPlateExists).validate(vehicle);
        Mockito.verify(validateClientExistsById).validate(client);
    }

    @DisplayName("add should returns an VehicleResponseDto on success")
    @Test
    void addShouldReturnsAnVehicleResponseDtoOnSuccess(){
        Vehicle vehicle = VehicleFactory.makeVehicle(vehicleRequestDto);
        vehicle.setId(1L);
        Mockito.when(dbLoadVehicleByPlate.loadVehicleByPlate(Mockito.anyString())).thenReturn(Optional.empty());
        Mockito.when(dbLoadClientById.loadById(Mockito.anyLong())).thenReturn(client);
        Mockito.when(dbAddVehicle.add(Mockito.any())).thenReturn(vehicle);
        VehicleResponseDto vehicleResponseDto = vehicleService.add(vehicleRequestDto);
        Assertions.assertNotNull(vehicleResponseDto);
        Assertions.assertEquals(1, vehicleResponseDto.id());
    }

}