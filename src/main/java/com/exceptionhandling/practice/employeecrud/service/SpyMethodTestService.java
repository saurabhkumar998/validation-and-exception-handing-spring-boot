package com.exceptionhandling.practice.employeecrud.service;

import org.springframework.stereotype.Service;

@Service
public class SpyMethodTestService {

    public String printHello() {
        return "Hello from printHello method";
    }
}
