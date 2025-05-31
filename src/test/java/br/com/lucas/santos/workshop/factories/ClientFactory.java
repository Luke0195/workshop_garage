package br.com.lucas.santos.workshop.factories;

import br.com.lucas.santos.workshop.domain.dto.request.ClientRequestDto;
import br.com.lucas.santos.workshop.domain.dto.response.ClientResponseDto;
import br.com.lucas.santos.workshop.domain.entities.Client;
import br.com.lucas.santos.workshop.domain.entities.enums.ClientStatus;
import net.datafaker.Faker;

import java.time.LocalDateTime;

public class ClientFactory {

    private ClientFactory(){};

    public static ClientRequestDto makeClientRequestDto(){
        return  new ClientRequestDto("any_name", "any_phone",
            "any_mail@mail.com", "870.296.190-30", "69310-030", "any_address", 1,
            "any_complement", ClientStatus.ACTIVE);
    }

    public static Client makeClient(ClientRequestDto clientRequestDto){
        return Client.builder()
            .id(1L)
            .name(clientRequestDto.name())
            .phone(clientRequestDto.phone())
            .email(clientRequestDto.email())
            .cpf(clientRequestDto.cpf())
            .zipcode(clientRequestDto.zipcode())
            .address(clientRequestDto.address())
            .number(clientRequestDto.number())
            .complement(clientRequestDto.complement())
            .status(ClientStatus.ACTIVE)
            .build();
    }

    public static ClientResponseDto makeClientResponseDto(Client client){
        return new ClientResponseDto(client.getId(), client.getName(), client.getPhone(),
            client.getEmail(), client.getCpf(), client.getZipcode(), client.getAddress(), client.getNumber(),
            client.getComplement(), client.getStatus(), LocalDateTime.now(), LocalDateTime.now());
    }
}
