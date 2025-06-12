package br.com.lucas.santos.workshop.business.validators;

import br.com.lucas.santos.workshop.business.contractors.validators.vehicle.ValidateIfPlateExists;
import br.com.lucas.santos.workshop.domain.entities.Vehicle;
import br.com.lucas.santos.workshop.infrastructure.exceptions.ValidationException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class VehicleValidator implements ValidateIfPlateExists {


    @Override
    public void validate(Optional<Vehicle> vehicle) {
        if(vehicle.isPresent()) throw new ValidationException("This plate already exists!");
    }
}
