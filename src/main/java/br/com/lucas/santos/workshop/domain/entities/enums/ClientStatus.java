package br.com.lucas.santos.workshop.domain.entities.enums;

import lombok.Getter;

@Getter
public enum ClientStatus {
    ACTIVE("ACTIVE"),
    INACTIVE("INACTIVE");

    private final String status;

    ClientStatus(String status){
        this.status = status;
    }
}
