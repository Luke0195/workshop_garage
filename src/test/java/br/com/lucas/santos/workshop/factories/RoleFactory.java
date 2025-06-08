package br.com.lucas.santos.workshop.factories;

import br.com.lucas.santos.workshop.domain.entities.Role;

public class RoleFactory {

    private RoleFactory(){}

    public static Role makeRole(){
        return Role.builder().id(1L).name("any_role").build();
    }
}
