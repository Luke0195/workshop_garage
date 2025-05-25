package br.com.lucas.santos.workshop.domain.dto.response;

import br.com.lucas.santos.workshop.domain.entities.enums.ClientStatus;

import java.time.LocalDateTime;

public record ClientResponseDto(
    Long id, String name, String phone,
    String email, String cpf, String zipcode,
    String address, Integer number, String complement,
    ClientStatus status, LocalDateTime createdAt,
                                LocalDateTime updatedAt) {
}
