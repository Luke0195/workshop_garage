package br.com.lucas.santos.workshop.infrastructure.repository;

import br.com.lucas.santos.workshop.domain.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientJpaRepository extends JpaRepository<Client, Long> {
    Optional<Client> findByEmail(String email);
}
