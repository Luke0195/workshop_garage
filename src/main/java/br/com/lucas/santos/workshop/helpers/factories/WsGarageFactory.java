package br.com.lucas.santos.workshop.helpers.factories;

import br.com.lucas.santos.workshop.domain.dto.response.FieldErrorResponseDto;
import br.com.lucas.santos.workshop.domain.dto.response.WsGarageStandardErrorDto;

import java.time.Instant;
import java.util.Set;

public class WsGarageFactory {

    private WsGarageFactory(){};


    public static WsGarageStandardErrorDto makeWsGarageStandardErrorDto(
            Integer statusCode, String error, String message, String path, Set<FieldErrorResponseDto> errors){
        return new WsGarageStandardErrorDto(Instant.now(), statusCode, error, message, path, errors);
    }
}
