package br.com.lucas.santos.workshop.domain.entities;

import br.com.lucas.santos.workshop.domain.dto.request.ClientRequestDto;
import br.com.lucas.santos.workshop.domain.entities.enums.ClientStatus;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name="tb_clients")
public class Client implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String phone;
    @Column(unique = true)
    private String email;
    @Column(unique = true)
    private String cpf;
    private String zipcode;
    private String address;
    private Integer number;
    private String complement;
    @Enumerated(EnumType.STRING)
    private ClientStatus status;
    @Column(name="created_at")
    private LocalDateTime createdAt;
    @Column(name="updated_at")
    private LocalDateTime updateAt;


    public static Client makeClient(ClientRequestDto clientRequestDto){
        return Client.builder()
            .name(clientRequestDto.name())
            .phone(clientRequestDto.phone())
            .email(clientRequestDto.email())
            .cpf(clientRequestDto.cpf())
            .zipcode(clientRequestDto.zipcode())
            .address(clientRequestDto.address())
            .number(clientRequestDto.number())
            .complement(clientRequestDto.complement())
            .status(clientRequestDto.status())
            .build();
    }

}
