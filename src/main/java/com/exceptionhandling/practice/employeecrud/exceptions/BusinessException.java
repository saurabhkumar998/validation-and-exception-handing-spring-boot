package com.exceptionhandling.practice.employeecrud.exceptions;

public class BusinessException extends RuntimeException{

    public BusinessException() {
        super();
    }

    public BusinessException(String message) {
        super(message);
    }

}
