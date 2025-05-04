package br.com.lucas.santos.workshop.infrastructure.exceptions;

public class RoleNotFoundException extends RuntimeException{

    public RoleNotFoundException(String message){
        super(message);
    }
}
