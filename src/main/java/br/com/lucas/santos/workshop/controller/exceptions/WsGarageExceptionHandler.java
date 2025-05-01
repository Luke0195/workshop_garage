package br.com.lucas.santos.workshop.controller.exceptions;

import br.com.lucas.santos.workshop.dto.response.FieldErrorResponseDto;
import br.com.lucas.santos.workshop.dto.response.WsGarageStandardErrorDto;
import br.com.lucas.santos.workshop.helpers.factories.WsGarageFactory;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@ControllerAdvice
public class WsGarageExceptionHandler {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<WsGarageStandardErrorDto> handleHibernateValidation(
            HttpServletRequest httpServletRequest, MethodArgumentNotValidException methodArgumentNotValidException){
        Set<FieldErrorResponseDto> errors = new HashSet<>();
        String requestUrl = httpServletRequest.getRequestURI();
        int badRequest = HttpStatus.BAD_REQUEST.value();
        String error = "Validation Exception";
        String message = "Please validate the errors field to validate the payload";
        methodArgumentNotValidException.getFieldErrors().forEach(fieldError -> {
            errors.add(new FieldErrorResponseDto(fieldError.getField(), fieldError.getDefaultMessage()));
        });
        WsGarageStandardErrorDto wsGarageStandardErrorDto = WsGarageFactory.makeWsGarageStandardErrorDto(badRequest,
                error, message, requestUrl, errors);
        return ResponseEntity.status(badRequest).body(wsGarageStandardErrorDto);

    };
}
