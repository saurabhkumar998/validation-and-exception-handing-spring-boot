package com.exceptionhandling.practice.employeecrud.junit5practice;

import org.junit.jupiter.api.*;

public class LifecycleMethodsDemoTest {
    @BeforeAll
    static void beforeAll() {
        System.out.println("beforeAll method called...");
    }
    @AfterAll
    static void afterAll() {
        System.out.println("afterAll method called...");
    }

    @BeforeEach
    void setUp() {
        System.out.println("beforeEach setUp method called...");
    }

    @AfterEach
    void tearDown() {
        System.out.println("afterEach tearDown method called...");
    }

    @Test
    void sampleTest1() {
        System.out.println("sampleTest1 method called...");
    }

    @Test
    void sampleTest2() {
        System.out.println("sampleTest2 method called...");
    }
}
