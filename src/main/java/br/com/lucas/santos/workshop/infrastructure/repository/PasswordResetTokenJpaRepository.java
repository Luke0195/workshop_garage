package br.com.lucas.santos.workshop.infrastructure.repository;


import br.com.lucas.santos.workshop.domain.entities.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PasswordResetTokenJpaRepository extends JpaRepository<PasswordResetToken, UUID> {
}
