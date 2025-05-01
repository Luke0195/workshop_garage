package br.com.lucas.santos.workshop.infrastructure.exceptions;

public class ResourceNotFoundException extends RuntimeException{

    private String message;

    public ResourceNotFoundException(String message){
        this.message = message;
    }
}
