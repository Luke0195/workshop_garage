package br.com.lucas.santos.workshop.business.validators;


import br.com.lucas.santos.workshop.domain.entities.Vehicle;
import br.com.lucas.santos.workshop.factories.VehicleFactory;
import br.com.lucas.santos.workshop.infrastructure.exceptions.ValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class VehicleValidatorTest {

    @InjectMocks
    private VehicleValidator vehicleValidator;

    private Vehicle vehicle;

    @BeforeEach
    void setup(){
      this.vehicle = VehicleFactory.makeVehicle(VehicleFactory.makeVehicleRequestDto());
    }

    @DisplayName("validate should throws ResourceNotFoundException if no vehicle was found")
    @Test
    void validateShouldThrowsResourceNotFoundExceptionIfNoVehicleWasFound(){
        Assertions.assertThrows(ValidationException.class, () -> {
           vehicleValidator.validate(vehicle);
        });
    }

    @DisplayName("validate should do nothing when vehicle was found")
    @Test
    void validateShouldDoNothingWhenVehicleWasFound(){
        vehicle.setId(1L);
        Assertions.assertDoesNotThrow(() -> vehicleValidator.validate(vehicle));
    }


}