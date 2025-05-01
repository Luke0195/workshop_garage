package br.com.lucas.santos.workshop.bunisses.protocols;

import org.springframework.stereotype.Component;


public interface DataMapper<Input, Output> {
    Output mapToDto(Input data);
    Input mapToDomain(Output entity);
}
