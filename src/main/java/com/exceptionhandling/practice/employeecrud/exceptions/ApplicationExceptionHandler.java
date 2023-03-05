package com.exceptionhandling.practice.employeecrud.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

//@RestControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        Map<String, String> errorMap = new HashMap<>();
        exception.getBindingResult()
                .getFieldErrors()
                .forEach(error -> errorMap.put(error.getField(), error.getDefaultMessage()));

        exception.printStackTrace();

        return errorMap;
    }

    // We can return a simple string or a ResponseEntity (this is the best practice). If we are returning a ResponseEntity
    // then we  don't need the @ResponseStatus annotation because the  http status is already present in the response entity
    // and is returned in the ResponseEntity along with the message.
/*
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleUserNotFoundException(UserNotFoundException exception) {
        exception.printStackTrace();
        return exception.getMessage();
    }
*/
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException exception) {
        exception.printStackTrace();
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleException(Exception exception) {
        exception.printStackTrace();
        return exception.getMessage();
    }
}
