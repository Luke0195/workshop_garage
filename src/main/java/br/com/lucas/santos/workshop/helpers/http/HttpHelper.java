package br.com.lucas.santos.workshop.helpers.http;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;

public class HttpHelper {

    private HttpHelper(){}

    public static String getPathUrlFromRequest(HttpServletRequest httpServletRequest){
        return httpServletRequest.getRequestURI();
    }

    public static Integer getHttpStatusCode(HttpStatus httpStatus){
        return httpStatus.value();
    }





}
