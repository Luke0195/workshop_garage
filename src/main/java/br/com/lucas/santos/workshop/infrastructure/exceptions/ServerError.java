package br.com.lucas.santos.workshop.infrastructure.exceptions;

public class ServerError extends RuntimeException{

    public ServerError(){
        super("Internal Server error");
    }
}
