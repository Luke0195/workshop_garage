package br.com.lucas.santos.workshop.domain.dto.response;

import br.com.lucas.santos.workshop.domain.entities.Client;
import br.com.lucas.santos.workshop.domain.entities.enums.ClientStatus;

import java.time.LocalDateTime;

public record ClientResponseDto(
    Long id, String name, String phone,
    String email, String cpf, String zipcode,
    String address, Integer number, String complement,
    ClientStatus status, LocalDateTime createdAt,
                                LocalDateTime updatedAt) {


    public static ClientResponseDto makeClientResponseDto(Client client){
        return new ClientResponseDto(
            client.getId(), client.getName(), client.getPhone(),
            client.getEmail(), client.getCpf(), client.getZipcode(), client.getAddress(), client.getNumber(),
            client.getComplement(), client.getStatus(), client.getCreatedAt(), client.getUpdateAt());
    }
}
