package br.com.lucas.santos.workshop.utils;

import br.com.lucas.santos.workshop.dto.response.FieldErrorResponseDto;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.HashSet;
import java.util.Set;

public class UtilHelper {

    public static Set<FieldErrorResponseDto> getErrorsFromHibernateValidation(MethodArgumentNotValidException exception){
      Set<FieldErrorResponseDto> errors = new HashSet<>();
      exception.getFieldErrors().forEach(fieldError -> {
          errors.add(new FieldErrorResponseDto(fieldError.getField(), fieldError.getDefaultMessage()));
      });
      return errors;
    };
}
