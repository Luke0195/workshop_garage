package br.com.lucas.santos.workshop.factories;

import br.com.lucas.santos.workshop.domain.dto.request.VehicleRequestDto;
import br.com.lucas.santos.workshop.domain.entities.Vehicle;

import java.time.LocalDateTime;

public class VehicleFactory {

    private VehicleFactory(){};

    public static VehicleRequestDto makeVehicleRequestDto(){
        return new VehicleRequestDto("any_brand", "any_model", 2024, "ZXC-1012", 1L);
    }

    public static Vehicle makeVehicle(VehicleRequestDto vehicleRequestDto){
        return Vehicle.builder().brand(vehicleRequestDto.brand()).model(vehicleRequestDto.model())
            .vehicleYear(vehicleRequestDto.vehicleYear())
            .plate(vehicleRequestDto.plate())
            .ownerId(UserFactory.makeUser(UserFactory.makeUserRequestDto()))
            .createdAt(LocalDateTime.now()).build();

    }
}
