package com.example.demo.exception;

import com.example.demo.statusCode.StatusCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;


@ControllerAdvice
public class ExceptionResolver extends RuntimeException {



    @ExceptionHandler
    public ResponseEntity<Object> handleAllExceptions(Exception exception){

        StatusCode errorStatus  = new StatusCode();
        HttpStatus httpResponse = null;
        if(exception instanceof HttpClientErrorException && HttpStatus.BAD_REQUEST.equals(((HttpClientErrorException)exception).getStatusCode())){
            errorStatus.setCode(Integer.toString(HttpStatus.BAD_REQUEST.value()));
            errorStatus.setMessage(exception.getMessage());
            httpResponse = HttpStatus.BAD_REQUEST;
        }else if(exception instanceof HttpClientErrorException && HttpStatus.UNAUTHORIZED.equals(((HttpClientErrorException)exception).getStatusCode())){
            errorStatus.setCode(Integer.toString(HttpStatus.UNAUTHORIZED.value()));
            errorStatus.setMessage("unauthorized");
            httpResponse = HttpStatus.UNAUTHORIZED;
        }else{
            errorStatus.setCode(Integer.toString(HttpStatus.INTERNAL_SERVER_ERROR.value()));
            errorStatus.setMessage(exception.getMessage());
            httpResponse = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(errorStatus,httpResponse);
    }
}
