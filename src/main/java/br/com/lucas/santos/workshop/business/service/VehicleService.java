package br.com.lucas.santos.workshop.business.service;

import br.com.lucas.santos.workshop.business.contractors.repositories.vehicle.DbLoadVehicleByPlate;
import br.com.lucas.santos.workshop.business.contractors.validators.vehicle.ValidateIfPlateExists;
import br.com.lucas.santos.workshop.domain.dto.request.VehicleRequestDto;
import br.com.lucas.santos.workshop.domain.dto.response.VehicleResponseDto;
import br.com.lucas.santos.workshop.domain.entities.Vehicle;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class VehicleService {

    private final DbLoadVehicleByPlate dbLoadVehicleByPlate;
    private final ValidateIfPlateExists validateIfPlateExists;


    public VehicleService(final DbLoadVehicleByPlate dbLoadVehicleByPlate, final ValidateIfPlateExists validateIfPlateExists){
        this.dbLoadVehicleByPlate = dbLoadVehicleByPlate;
        this.validateIfPlateExists = validateIfPlateExists;
    }

    @Transactional(rollbackFor = Exception.class)
    public VehicleResponseDto add(VehicleRequestDto vehicleRequestDto){
        Optional<Vehicle> findVehicleByPlate = dbLoadVehicleByPlate.loadVehicleByPlate(vehicleRequestDto.plate());
        this.validateIfPlateExists.validate(findVehicleByPlate);
        return null;
    }



}
