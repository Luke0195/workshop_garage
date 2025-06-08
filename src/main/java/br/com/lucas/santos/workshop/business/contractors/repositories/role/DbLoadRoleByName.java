package br.com.lucas.santos.workshop.business.contractors.repositories.role;

import br.com.lucas.santos.workshop.domain.entities.Role;


public interface DbLoadRoleByName {
    Role loadUserByRole(String role);
}
