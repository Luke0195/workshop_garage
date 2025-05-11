package br.com.lucas.santos.workshop.bunisses.contractors.repositories.role;

import br.com.lucas.santos.workshop.domain.entities.Role;


public interface LoadRoleByName {
    Role loadUserByRole(String role);
}
