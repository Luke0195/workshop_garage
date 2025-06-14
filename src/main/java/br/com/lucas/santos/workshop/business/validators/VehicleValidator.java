package br.com.lucas.santos.workshop.business.validators;

import br.com.lucas.santos.workshop.business.contractors.validators.vehicle.ValidateIfPlateExists;
import br.com.lucas.santos.workshop.domain.dto.request.VehicleRequestDto;
import br.com.lucas.santos.workshop.domain.entities.User;
import br.com.lucas.santos.workshop.domain.entities.Vehicle;
import br.com.lucas.santos.workshop.infrastructure.exceptions.ValidationException;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class VehicleValidator implements ValidateIfPlateExists {


    @Override
    public void validate(Vehicle vehicle) {
        if(Objects.isNull(vehicle.getId())) throw new ValidationException("This plate already exists!");
    }


    public Vehicle makeVehicle(VehicleRequestDto vehicleRequestDto, User user){
        return Vehicle.builder()
            .model(vehicleRequestDto.model())
            .brand(vehicleRequestDto.brand())
            .plate(vehicleRequestDto.plate())
           // .ownerId(vehicleRequestDto.ownerId());
            .build();
    }
}
