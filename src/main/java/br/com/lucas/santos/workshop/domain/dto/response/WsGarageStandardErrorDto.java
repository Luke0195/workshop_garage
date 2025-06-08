package br.com.lucas.santos.workshop.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.util.Set;


public record WsGarageStandardErrorDto(
        Instant timestamp,
        @JsonProperty("status_code")
        Integer statusCode,
        String error,
        String message,
        String path,
        Set<FieldErrorResponseDto> errors
) {


}
