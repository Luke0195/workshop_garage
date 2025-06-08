package br.com.lucas.santos.workshop.infrastructure.adapters.db;

import br.com.lucas.santos.workshop.domain.entities.Role;
import br.com.lucas.santos.workshop.factories.RoleFactory;
import br.com.lucas.santos.workshop.infrastructure.exceptions.ResourceAlreadyExistsException;
import br.com.lucas.santos.workshop.infrastructure.repository.RoleJpaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;


@ExtendWith(MockitoExtension.class)
@ActiveProfiles("dev")
class RoleRepositoryTest {

    @InjectMocks
    private RoleRepository sut;

    @Mock
    private RoleJpaRepository roleJpaRepository;

    private Role role;

    @BeforeEach
    void setup(){
        this.role = RoleFactory.makeRole();
    }

    @DisplayName("loadByName should be called with correct value")
    @Test
    void loadByNameShouldBeCalledWithCorrectValue(){
      Mockito.when(roleJpaRepository.findByName("any_role")).thenReturn(Optional.of(this.role));
      sut.loadUserByRole("any_role");
      Mockito.verify(roleJpaRepository).findByName("any_role");
    }

    @DisplayName("loadByName should returns a role when valid role is provided")
    @Test
    void loadByNameShouldReturnsARoleWhenValidRoleIsProvided(){
       Mockito.when(roleJpaRepository.findByName("any_role")).thenReturn(Optional.of(role));
       Role role =  sut.loadUserByRole("any_role");
       Assertions.assertNotNull(role);
       Assertions.assertEquals(1L, role.getId());
       Assertions.assertEquals("any_role", role.getName());
    }

    @DisplayName("loadByName should throws ResourceNotFoundException when an invalid role name is provided")
    @Test
    void loadByNameShouldThrowsResourceNotFoundExceptionWhenInvalidRoleNameIsProvided(){
        Mockito.when(roleJpaRepository.findByName("any_role")).thenThrow(ResourceAlreadyExistsException.class);
        Assertions.assertThrows(ResourceAlreadyExistsException.class, () -> {
            sut.loadUserByRole("any_role");
        });
    }


}