package com.exceptionhandling.practice.employeecrud.controller;

import com.exceptionhandling.practice.employeecrud.exceptions.BusinessException;
import com.exceptionhandling.practice.employeecrud.exceptions.UserNotFoundException;
import com.exceptionhandling.practice.employeecrud.exceptions.ValidationFailedException;
import com.exceptionhandling.practice.employeecrud.util.EmployeeLogger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@Controller
public abstract class BaseController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        Map<String, String> errorMap = new HashMap<>();
        exception.getBindingResult()
                .getFieldErrors()
                .forEach(error -> errorMap.put(error.getField(), error.getDefaultMessage()));

 //       exception.printStackTrace();
        EmployeeLogger.logError(this, exception);

        return errorMap;
    }

    @ExceptionHandler(ValidationFailedException.class)
    public ResponseEntity<String> handleValidationFailedException(ValidationFailedException exception) {
        EmployeeLogger.logError(this, exception);
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
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
  //      exception.printStackTrace();
        EmployeeLogger.logError(this, exception);
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<String> handleBusinessException(BusinessException exception) {
   //     exception.printStackTrace();
        EmployeeLogger.logError(this, exception);
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleException(Exception exception) {
    //    exception.printStackTrace();
        EmployeeLogger.logError(this, exception);
        return exception.getMessage();
    }
}
