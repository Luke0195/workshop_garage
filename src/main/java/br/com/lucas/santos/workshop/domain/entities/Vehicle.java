package br.com.lucas.santos.workshop.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
    private User ownerId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
