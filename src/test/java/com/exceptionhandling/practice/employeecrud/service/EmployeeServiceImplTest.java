package com.exceptionhandling.practice.employeecrud.service;

import com.exceptionhandling.practice.employeecrud.entity.BaseClass;
import com.exceptionhandling.practice.employeecrud.entity.Employee;
import com.exceptionhandling.practice.employeecrud.exceptions.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.*;


import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EmployeeServiceImplTest {


    @Autowired
    private EmployeeService employeeService;

//    @BeforeEach
//    void setUp() {
//        employeeService = new EmployeeServiceImpl();
//    }

    @Test
    void findAllEmployeesTest() {
    //    fail();

        List<Employee> actualEmployeeList = employeeService.findAllEmployees();

    //    assertNotNull(employeeList, "EmployeeList is Empty");

        Employee expectedEmployee = new Employee(102, "Test Employee", "Test Dept",
                new BaseClass("99999999", new Date(), "99999999", new Date()));
        // comparing one object from a list of objects (equals and hashCode method comes into picture here)
        // if you have not implemented the equals and hashCode method properly in your Employee class then this test will fail
        assertThat(actualEmployeeList).contains(expectedEmployee);
    }

    @Test
    void findEmployeeTest() throws UserNotFoundException {
    //    fail();
        String expectedEmployeeName = "Test Employee";
        String expectedEmployeeDept = "Test Dept";
        // fetching employeee from db
        Employee actualEmployee = employeeService.findEmployee(102);
    //    assertEquals(expectedEmployeeName, employee.getEmployeeName());

        // using Junit5 assert methods
        assertAll(() -> {
            assertEquals(expectedEmployeeName, actualEmployee.getEmployeeName());
            assertEquals(expectedEmployeeDept, actualEmployee.getEmployeeDept());
        });

        // using AssertJ assertThat method - comparing simple fields
        assertThat(actualEmployee.getEmployeeName()).isEqualTo(expectedEmployeeName);
        assertThat(actualEmployee.getEmployeeDept()).isEqualTo(expectedEmployeeDept);

        // expected Employee object
        Employee expectedEmployee = new Employee(102, "Test Employee", "Test Dept",
                new BaseClass("99999999", new Date(), "99999999", new Date()));

        // using AssertJ assertThat methods - comparing the complete object ignoring few fields
        assertThat(actualEmployee).usingRecursiveComparison()
                .ignoringFields("baseClass.createTimestamp", "baseClass.lastUpdateTimestamp")
                .isEqualTo(expectedEmployee);

        assertThat(actualEmployee).usingRecursiveComparison()
                .comparingOnlyFields("employeeName", "employeeDept",
                        "baseClass.createUser", "baseClass.lastUpdateUser")
                .isEqualTo(expectedEmployee);


        // exception test using Junit5
        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> {
            employeeService.findEmployee(123432);
        });
        assertTrue(exception.getMessage().contentEquals("Employee with the employee id 123432 was not found"));

        // exception test using AssertJ
        assertThatThrownBy(() -> {
            employeeService.findEmployee(123432);
        }).isInstanceOf(UserNotFoundException.class)
                .hasMessage("Employee with the employee id 123432 was not found");

    }

    @Test
    void addEmployeeTest() {
    //    fail();
        Employee employeeToSave = new Employee(1000001,
                "Test Employee",
                "Test Dept",
                new BaseClass("99999999", new Date(), "99999999", new Date())
        );


        Employee employee = employeeService.addEmployee(employeeToSave);

        assertAll(() -> {
            assertEquals(employee.getEmployeeName(), "Test Employee");
            assertEquals(employee.getEmployeeDept(), "Test Dept");
            assertEquals(employee.getBaseClass().getCreateUser(), "99999999");
            assertEquals(employee.getBaseClass().getLastUpdateUser(), "99999999");
            assertNotNull(employee.getBaseClass().getCreateTimestamp());
            assertNotNull(employee.getBaseClass().getLastUpdateTimestamp());
            assertTrue(employee.getEmployeeName().contains("Test"));
        });

    }

    @Test
    void removeEmployeeTest() {
    //    fail();
        assertTrue(true);
    }

    @Test
    void updateEmployeeUsingPutMethodTest() {
    //    fail();
        assertTrue(true);
    }

    @Test
    void updateEmployeeUsingPatchMethodTest() {
    //    fail();
        assertTrue(true);
    }
}