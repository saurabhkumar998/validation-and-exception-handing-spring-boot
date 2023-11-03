package com.exceptionhandling.practice.employeecrud.junit5practice;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class NestedTestsDemo {

    @Test
    public void test1() {
        System.out.println("Non nested test");
    }

    @Nested
    class nestedTest {

        @Test
        public void nestedTest1() {
            System.out.println("nested test1");
        }

        @Test
        public void nestedTest2() {
            System.out.println("nested test2");
        }
    }
}
