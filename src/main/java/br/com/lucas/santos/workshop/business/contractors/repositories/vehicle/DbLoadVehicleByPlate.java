package br.com.lucas.santos.workshop.business.contractors.repositories.vehicle;

import br.com.lucas.santos.workshop.domain.entities.Vehicle;

import java.util.Optional;

public interface DbLoadVehicleByPlate {

    Optional<Vehicle> loadVehicleByPlate(String name);
}
