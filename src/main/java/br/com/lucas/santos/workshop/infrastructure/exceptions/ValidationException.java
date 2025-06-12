package br.com.lucas.santos.workshop.infrastructure.exceptions;

public class ValidationException extends RuntimeException{

    public ValidationException(String message){
        super(message);
    }
}
