package br.com.lucas.santos.workshop.domain.dto.response;

import br.com.lucas.santos.workshop.domain.entities.Role;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

public record UserResponseDto(
        UUID id,
        String name,
        String email,
        String password,
        Set<Role> roles,
        @JsonProperty("created_at")
        LocalDateTime createdAt,
        @JsonProperty("updated_at")
        LocalDateTime updatedAt

) {
}
