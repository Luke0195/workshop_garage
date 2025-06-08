package br.com.lucas.santos.workshop.infrastructure.exceptions;

public class ResourceAlreadyExistsException extends RuntimeException{


    public ResourceAlreadyExistsException(String message){
        super(message);
    }
}
