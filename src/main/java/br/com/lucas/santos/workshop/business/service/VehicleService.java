package br.com.lucas.santos.workshop.business.service;

import br.com.lucas.santos.workshop.domain.dto.request.VehicleRequestDto;
import br.com.lucas.santos.workshop.domain.dto.response.VehicleResponseDto;
import br.com.lucas.santos.workshop.infrastructure.exceptions.ResourceAlreadyExistsException;
import br.com.lucas.santos.workshop.infrastructure.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VehicleService {

    @Transactional(rollbackFor = Exception.class)
    public VehicleResponseDto add(VehicleRequestDto vehicleRequestDto){
             throw new ResourceAlreadyExistsException("any_exception");
    }

}
