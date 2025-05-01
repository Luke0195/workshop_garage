package br.com.lucas.santos.workshop.infrastructure.exceptions;

public class ResourceAlreadyExistsException extends RuntimeException{

    private String message;

    public ResourceAlreadyExistsException(String message){
        super(message);
    }
}
