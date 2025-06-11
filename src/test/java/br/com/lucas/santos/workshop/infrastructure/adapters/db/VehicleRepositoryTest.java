package br.com.lucas.santos.workshop.infrastructure.adapters.db;

import br.com.lucas.santos.workshop.domain.entities.Vehicle;
import br.com.lucas.santos.workshop.infrastructure.repository.VehicleJpaRepository;
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

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
@ActiveProfiles("dev")
class VehicleRepositoryTest {

    @InjectMocks
    private VehicleRepository vehicleRepository;
    @Mock
    private VehicleJpaRepository vehicleJpaRepository;


    @DisplayName("loadByPlate should return an optional with empty value when vehicle not found")
    @Test
    void loadByPlateShouldReturnsAnEmptyOptionalWhenValidPlateIsProvided(){
        Optional<Vehicle> findVehicleByPlate = vehicleRepository.loadVehicleByPlate("invalid_plate");
        Assertions.assertTrue(findVehicleByPlate.isEmpty());
    }
}