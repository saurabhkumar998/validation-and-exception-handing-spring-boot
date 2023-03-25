package com.exceptionhandling.practice.employeecrud.repository;

import com.exceptionhandling.practice.employeecrud.entity.Employee;
import org.hibernate.validator.constraints.ModCheck;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
//@DataJpaTest
@SpringBootTest
class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @BeforeEach
    void setUp() {
        System.out.println("Setting Up");
    }

    @AfterEach
    void tearDown() {
        System.out.println("Tearing Down");
    }

    @Test
    public void findByEmployeeIdTest() {
        Employee employee = employeeRepository.findByEmployeeId(1);
        System.out.println(employee);
    }

    @Test
    public void findAllEmployeesUsingCustomQueryTest() {
        List<Employee> employees = employeeRepository.findAllEmployeesUsingNativeQuery();
        System.out.println(employees);
    }

    @Test
    public void findEmployeeByNameTest() {
        List<Employee> employees = employeeRepository.findEmployeeByName("Saurabh");
        System.out.println(employees);
    }


}