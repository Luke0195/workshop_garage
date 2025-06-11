package br.com.lucas.santos.workshop.infrastructure.adapters.db;

import br.com.lucas.santos.workshop.business.contractors.repositories.vehicle.DbLoadVehicleByPlate;
import br.com.lucas.santos.workshop.domain.entities.Vehicle;
import br.com.lucas.santos.workshop.infrastructure.repository.VehicleJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class VehicleRepository implements DbLoadVehicleByPlate {

    private final VehicleJpaRepository vehicleJpaRepository;

    public VehicleRepository(final VehicleJpaRepository vehicleJpaRepository){
        this.vehicleJpaRepository = vehicleJpaRepository;
    }

    @Override
    public Optional<Vehicle> loadVehicleByPlate(String name) {
        return vehicleJpaRepository.findByPlate(name);
    }
}
