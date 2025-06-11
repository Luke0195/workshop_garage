package br.com.lucas.santos.workshop.infrastructure.repository;

import br.com.lucas.santos.workshop.domain.entities.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VehicleJpaRepository extends JpaRepository<Vehicle, Long> {

    Optional<Vehicle> findByPlate(String plate);
}
