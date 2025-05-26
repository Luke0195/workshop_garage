package br.com.lucas.santos.workshop.infrastructure.repository;

import br.com.lucas.santos.workshop.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserJpaRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
