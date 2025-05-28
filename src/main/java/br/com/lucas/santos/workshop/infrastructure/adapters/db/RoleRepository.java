package br.com.lucas.santos.workshop.infrastructure.adapters.db;

import br.com.lucas.santos.workshop.business.contractors.repositories.role.DbLoadRoleByName;
import br.com.lucas.santos.workshop.domain.entities.Role;

import br.com.lucas.santos.workshop.infrastructure.exceptions.RoleNotFoundException;
import br.com.lucas.santos.workshop.infrastructure.repository.RoleJpaRepository;
import org.springframework.stereotype.Component;

@Component
public class RoleRepository implements DbLoadRoleByName {

    private final RoleJpaRepository roleJpaRepository;

    public RoleRepository(RoleJpaRepository roleJpaRepository){
        this.roleJpaRepository = roleJpaRepository;
    }
    @Override
    public Role loadUserByRole(String role) {
      return roleJpaRepository.findByName(role)
          .orElseThrow(() -> new RoleNotFoundException("This role does not exists : " + role));
    }
}
