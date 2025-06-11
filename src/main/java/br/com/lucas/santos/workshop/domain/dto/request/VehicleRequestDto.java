package br.com.lucas.santos.workshop.domain.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record VehicleRequestDto(
    @NotEmpty(message = "The field brand must be required")
    String brand,
    @NotEmpty(message = "The field model must be required")
    String model,
    @Pattern(regexp = "^(19|20)\\d{2}$", message = "Invalid year. Should been between 1900 and 2099.")
    @NotNull(message = "The field vehicle_year must be required")
    @JsonProperty("vehicle_year")
    Integer vehicleYear,
    @NotEmpty(message =  "The field plate must be required")
    String plate,
    @NotNull(message = "The field owner_id must be required")
    @JsonProperty("owner_id")
    Long ownerId) {
}
