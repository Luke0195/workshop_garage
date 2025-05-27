package br.com.lucas.santos.workshop.controller.exceptions;

import br.com.lucas.santos.workshop.domain.dto.response.FieldErrorResponseDto;
import br.com.lucas.santos.workshop.domain.dto.response.WsGarageStandardErrorDto;
import br.com.lucas.santos.workshop.helpers.factories.WsGarageFactory;
import br.com.lucas.santos.workshop.helpers.http.HttpHelper;
import br.com.lucas.santos.workshop.infrastructure.exceptions.InvalidCredentialsException;
import br.com.lucas.santos.workshop.infrastructure.exceptions.ResourceNotFoundException;
import br.com.lucas.santos.workshop.utils.UtilHelper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashSet;
import java.util.Set;

@ControllerAdvice
public class WsGarageExceptionHandler {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<WsGarageStandardErrorDto> handleHibernateValidation(
            HttpServletRequest httpServletRequest, MethodArgumentNotValidException methodArgumentNotValidException){
        Set<FieldErrorResponseDto> errors = UtilHelper.getErrorsFromHibernateValidation(methodArgumentNotValidException);
        WsGarageStandardErrorDto wsGarageStandardErrorDto = WsGarageFactory.makeWsGarageStandardErrorDto(
                HttpHelper.getHttpStatusCode(HttpStatus.BAD_REQUEST),
                "Validation Exception", "Please validate the errors field to validate the payload",
                HttpHelper.getPathUrlFromRequest(httpServletRequest), errors);
        return HttpHelper.badRequest(wsGarageStandardErrorDto);
    };

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<WsGarageStandardErrorDto> handleHttpMessageNotReadableException(
            HttpServletRequest httpServletRequest, HttpMessageNotReadableException exception){
         WsGarageStandardErrorDto wsGarageStandardErrorDto = WsGarageFactory.makeWsGarageStandardErrorDto(
                 HttpHelper.getHttpStatusCode(HttpStatus.BAD_REQUEST), "HttpMessage Not Readable Exception",
                 exception.getMessage(), HttpHelper.getPathUrlFromRequest(httpServletRequest), new HashSet<>());
         return HttpHelper.badRequest(wsGarageStandardErrorDto);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<WsGarageStandardErrorDto> handleInvalidCredentialsException(
        HttpServletRequest httpServletRequest, InvalidCredentialsException exception){
        WsGarageStandardErrorDto wsGarageStandardErrorDto = WsGarageFactory.makeWsGarageStandardErrorDto(
            HttpHelper.getHttpStatusCode(HttpStatus.UNAUTHORIZED), "Invalid Credentials are provided",
            exception.getMessage(), HttpHelper.getPathUrlFromRequest(httpServletRequest), new HashSet<>());
        return HttpHelper.unauthorized(wsGarageStandardErrorDto);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<WsGarageStandardErrorDto> handleResourceNotFoundException(
        HttpServletRequest httpServletRequest, ResourceNotFoundException exception){
        WsGarageStandardErrorDto wsGarageStandardErrorDto = WsGarageFactory.makeWsGarageStandardErrorDto(
            HttpHelper.getHttpStatusCode(HttpStatus.UNPROCESSABLE_ENTITY), "Resource Not Found Exception",
            exception.getMessage(), HttpHelper.getPathUrlFromRequest(httpServletRequest), new HashSet<>());
        return HttpHelper.unauthorized(wsGarageStandardErrorDto);
    }


}
