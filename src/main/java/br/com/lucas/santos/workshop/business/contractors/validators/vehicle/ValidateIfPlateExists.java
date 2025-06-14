package br.com.lucas.santos.workshop.business.contractors.validators.vehicle;

import br.com.lucas.santos.workshop.domain.entities.Vehicle;

import java.util.Optional;

public interface ValidateIfPlateExists {

    void validate(Vehicle vehicle);
}
