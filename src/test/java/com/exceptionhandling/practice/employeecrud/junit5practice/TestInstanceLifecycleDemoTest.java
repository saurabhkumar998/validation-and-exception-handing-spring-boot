package com.exceptionhandling.practice.employeecrud.junit5practice;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(value = TestInstance.Lifecycle.PER_CLASS)   // creates an instance which will be used for all the test methods of this class
//@TestInstance(value = TestInstance.Lifecycle.PER_METHOD)    // default behaviour (creates different instance for each test methods)
public class TestInstanceLifecycleDemoTest {

    @Test
    @Tag("unit")
    void test1() {
        System.out.println("test1 : " + this.hashCode());
    }

    @Test
    @Tag("integration")
    void test2() {
        System.out.println("test2 : " + this.hashCode());
    }
}
