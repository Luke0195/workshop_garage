package br.com.lucas.santos.workshop.domain.dto.response;

import br.com.lucas.santos.workshop.domain.entities.Role;
import br.com.lucas.santos.workshop.domain.entities.User;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.Set;

public record UserResponseDto(
        Long id,
        String name,
        String email,
        String password,
        Set<Role> roles,
        @JsonProperty("created_at")
        LocalDateTime createdAt,
        @JsonProperty("updated_at")
        LocalDateTime updatedAt

) {

    public static UserResponseDto mapEntityToDto(User user){
       return new UserResponseDto(user.getId(), user.getName(), user.getEmail(),
            user.getPassword(), user.getRoles(), user.getCreatedAt(), user.getUpdatedAt());
    }
}
