package br.com.lucas.santos.workshop.bunisses.protocols.db.user;

import br.com.lucas.santos.workshop.domain.entities.User;

public interface AddUserRepository {

    User add(User user);
}
