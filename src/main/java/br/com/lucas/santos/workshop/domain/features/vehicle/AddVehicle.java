package br.com.lucas.santos.workshop.domain.features.vehicle;

import br.com.lucas.santos.workshop.domain.dto.request.VehicleRequestDto;
import br.com.lucas.santos.workshop.domain.dto.response.VehicleResponseDto;

public interface AddVehicle {

    VehicleResponseDto add(VehicleRequestDto vehicleRequestDto);

}
