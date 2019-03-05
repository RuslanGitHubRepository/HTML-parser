package com.simbirsoft.kondratyev.ruslan.pizzeria.controllers.exception;

import com.simbirsoft.kondratyev.ruslan.pizzeria.dto.ResponseMsg;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseMsg> handleException(Exception ex) {
        ResponseMsg response =  new ResponseMsg();//Define this Bean in a way that it contains all required paramteres to be sent in response when exception occurs
        response.setMessage(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
