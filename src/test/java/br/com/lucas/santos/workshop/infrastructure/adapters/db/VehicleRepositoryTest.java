package br.com.lucas.santos.workshop.infrastructure.adapters.db;

import br.com.lucas.santos.workshop.domain.dto.request.VehicleRequestDto;
import br.com.lucas.santos.workshop.domain.entities.Vehicle;
import br.com.lucas.santos.workshop.factories.VehicleFactory;
import br.com.lucas.santos.workshop.infrastructure.repository.VehicleJpaRepository;
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
class VehicleRepositoryTest {

    @InjectMocks
    private VehicleRepository vehicleRepository;
    @Mock
    private VehicleJpaRepository vehicleJpaRepository;

    private Vehicle vehicle;
    private VehicleRequestDto vehicleRequestDto;

    @BeforeEach
    void setup(){
        this.vehicleRequestDto = VehicleFactory.makeVehicleRequestDto();
        this.vehicle = VehicleFactory.makeVehicle(vehicleRequestDto);
    }

    @DisplayName("loadByPlate should return an optional with empty value when vehicle not found")
    @Test
    void loadByPlateShouldReturnsAnEmptyOptionalWhenInvalidPlateIsProvided(){
        Optional<Vehicle> findVehicleByPlate = vehicleRepository.loadVehicleByPlate("invalid_plate");
        Assertions.assertTrue(findVehicleByPlate.isEmpty());
    }

    @DisplayName("loadByPlate should return an optional with empty value when vehicle not found")
    @Test
    void loadByPlateShouldReturnsAVehicleWhenValidPlateIsProvided(){
        Mockito.when(vehicleJpaRepository.findByPlate(Mockito.anyString())).thenReturn(Optional.of(vehicle));
        Optional<Vehicle> findVehicleByPlate = vehicleRepository.loadVehicleByPlate("valid_plate");
        Assertions.assertFalse(findVehicleByPlate.isEmpty());
    }


}