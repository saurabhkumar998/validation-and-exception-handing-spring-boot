package com.exceptionhandling.practice.employeecrud.repository;

import com.exceptionhandling.practice.employeecrud.entity.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
//@DataJpaTest
@SpringBootTest
class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    public void findByEmployeeIdTest() {
        Employee employee = employeeRepository.findByEmployeeId(1);
        System.out.println(employee);
    }


}