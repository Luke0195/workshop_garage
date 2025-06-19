package br.com.lucas.santos.workshop.domain.entities;

import br.com.lucas.santos.workshop.domain.dto.request.VehicleRequestDto;
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
@Table(name="tb_vehicles")
public class Vehicle implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String brand;
    private String model;
    @Column(name = "vehicle_year")
    private Integer vehicleYear;
    private String plate;
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Client ownerId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    public static Vehicle makeVehicle(VehicleRequestDto vehicleRequestDto, Client client){
       return Vehicle.builder()
            .model(vehicleRequestDto.model())
            .brand(vehicleRequestDto.brand())
            .vehicleYear(vehicleRequestDto.vehicleYear())
            .plate(vehicleRequestDto.plate())
            .ownerId(client)
            .build();
    }
}
