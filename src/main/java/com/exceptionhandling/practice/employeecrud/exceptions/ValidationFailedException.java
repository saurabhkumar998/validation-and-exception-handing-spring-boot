package com.exceptionhandling.practice.employeecrud.exceptions;

public class ValidationFailedException extends RuntimeException {

    public ValidationFailedException(String message) {
        super(message);
    }
}
