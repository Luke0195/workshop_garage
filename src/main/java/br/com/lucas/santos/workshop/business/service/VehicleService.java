package br.com.lucas.santos.workshop.business.service;

import br.com.lucas.santos.workshop.business.contractors.repositories.client.DbLoadClientById;
import br.com.lucas.santos.workshop.business.contractors.repositories.vehicle.DbAddVehicle;
import br.com.lucas.santos.workshop.business.contractors.repositories.vehicle.DbLoadVehicleByPlate;
import br.com.lucas.santos.workshop.business.contractors.validators.client.ValidateClientExistsById;
import br.com.lucas.santos.workshop.business.contractors.validators.vehicle.ValidateIfPlateExists;
import br.com.lucas.santos.workshop.domain.dto.request.VehicleRequestDto;
import br.com.lucas.santos.workshop.domain.dto.response.VehicleResponseDto;
import br.com.lucas.santos.workshop.domain.entities.Client;
import br.com.lucas.santos.workshop.domain.entities.Vehicle;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class VehicleService {

    private final DbLoadVehicleByPlate dbLoadVehicleByPlate;
    private final DbLoadClientById dbLoadClientById;
    private final DbAddVehicle dbAddVehicle;
    private final ValidateIfPlateExists validateIfPlateExists;
    private final ValidateClientExistsById validateClientExistsById;


    public VehicleService(
        final DbLoadVehicleByPlate dbLoadVehicleByPlate,
        final DbLoadClientById dbLoadClientById,
        final ValidateIfPlateExists validateIfPlateExists,
        final ValidateClientExistsById validateClientExistsById,
        final DbAddVehicle dbAddVehicle){
        this.dbLoadVehicleByPlate = dbLoadVehicleByPlate;
        this.validateIfPlateExists = validateIfPlateExists;
        this.dbLoadClientById = dbLoadClientById;
        this.validateClientExistsById = validateClientExistsById;
        this.dbAddVehicle = dbAddVehicle;
    }

    @Transactional(rollbackFor = Exception.class)
    public VehicleResponseDto add(VehicleRequestDto vehicleRequestDto){
        Vehicle findVehicleByPlate = dbLoadVehicleByPlate.loadVehicleByPlate(vehicleRequestDto.plate()).orElse(null);
        this.validateIfPlateExists.validate(findVehicleByPlate);
        Client findOwnerById = dbLoadClientById.loadById(vehicleRequestDto.ownerId()).orElse(null);
        this.validateClientExistsById.validate(findOwnerById);
        Vehicle createdVehicle = Vehicle.makeVehicle(vehicleRequestDto, findOwnerById);
        createdVehicle = dbAddVehicle.add(createdVehicle);
        return VehicleResponseDto.vehicleResponseDto(createdVehicle);
    }



}
