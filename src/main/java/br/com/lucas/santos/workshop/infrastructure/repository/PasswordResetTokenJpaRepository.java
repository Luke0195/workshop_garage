package br.com.lucas.santos.workshop.infrastructure.repository;


import br.com.lucas.santos.workshop.domain.entities.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface PasswordResetTokenJpaRepository extends JpaRepository<PasswordResetToken, Long> {
    Optional<PasswordResetToken> findByToken(String token);

    List<PasswordResetToken> findByUsed(Boolean used);

    void deletePasswordResetTokenByUsed(Boolean used);
}
