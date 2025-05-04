package br.com.lucas.santos.workshop.bunisses.protocols.db.user;

import br.com.lucas.santos.workshop.domain.entities.User;

import java.util.Optional;

public interface LoadUserByEmailRepository {

    Optional<User> loadUserByEmail(String email);
}
