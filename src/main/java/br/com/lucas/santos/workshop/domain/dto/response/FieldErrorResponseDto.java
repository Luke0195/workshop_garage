package br.com.lucas.santos.workshop.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record FieldErrorResponseDto(
        @JsonProperty("field_name")
        String fieldName,
        String description) {
}
