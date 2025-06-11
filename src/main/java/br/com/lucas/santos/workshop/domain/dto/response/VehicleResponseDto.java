package br.com.lucas.santos.workshop.domain.dto.response;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public record VehicleResponseDto(
    Long id,
    String brand,
    String model,
    @JsonProperty("vehicle_year")
    Integer vehicleYear,
    String plate,
    @JsonProperty("owner_id")
    Long ownerId,
    @JsonProperty("created_at")
    LocalDateTime creteadAt,
    @JsonProperty("updated_at")
    LocalDateTime updatedAt) {
}
