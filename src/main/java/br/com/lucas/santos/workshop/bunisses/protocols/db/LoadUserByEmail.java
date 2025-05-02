package br.com.lucas.santos.workshop.bunisses.protocols.db;

import br.com.lucas.santos.workshop.domain.entities.User;

public interface LoadUserByEmail {

    User loadUserByEmail(String email);
}
