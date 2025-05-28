package br.com.lucas.santos.workshop.business.contractors.repositories.user;

import br.com.lucas.santos.workshop.domain.entities.User;

public interface DbAddUserRepository {

    User add(User user);
}
