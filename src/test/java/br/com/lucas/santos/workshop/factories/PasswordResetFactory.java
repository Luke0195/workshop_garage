package br.com.lucas.santos.workshop.factories;

import br.com.lucas.santos.workshop.domain.entities.PasswordResetToken;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

public class PasswordResetFactory {

    private PasswordResetFactory(){};


    public static PasswordResetToken makePasswordResetToken(){
        Instant currentDate = Instant.now();
        return PasswordResetToken.builder()
            .id(UUID.randomUUID())
            .token(UUID.randomUUID())
            .used(Boolean.FALSE)
            .expiresAt(currentDate.plus(15, ChronoUnit.MINUTES))
            .build();
    }
}
