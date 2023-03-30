package com.exceptionhandling.practice.employeecrud.exceptions;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(String message) {
        super(message);
    }
}
