package br.com.lucas.santos.workshop.business.contractors.repositories.user;

import br.com.lucas.santos.workshop.domain.entities.User;

import java.util.Optional;

public interface DbLoadUserByEmailRepository {

    Optional<User> loadUserByEmail(String email);
}
