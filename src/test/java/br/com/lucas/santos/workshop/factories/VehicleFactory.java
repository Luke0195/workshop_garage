package br.com.lucas.santos.workshop.factories;

import br.com.lucas.santos.workshop.domain.dto.request.VehicleRequestDto;

public class VehicleFactory {

    private VehicleFactory(){};

    public static VehicleRequestDto makeVehicleRequestDto(){
        return new VehicleRequestDto("any_brand", "any_model", 2024, "ZXC-1012", 1L);
    }
}
