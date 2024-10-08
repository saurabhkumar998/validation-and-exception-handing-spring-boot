package com.exceptionhandling.practice.employeecrud.service;

import com.exceptionhandling.practice.employeecrud.entity.BaseClass;
import com.exceptionhandling.practice.employeecrud.entity.Employee;
import com.exceptionhandling.practice.employeecrud.exceptions.UserNotFoundException;
import com.exceptionhandling.practice.employeecrud.repository.EmployeeRepository;
import jakarta.inject.Inject;
import org.assertj.core.util.Streams;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.*;


import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Tag("unit")
class EmployeeServiceImplTest {

//    @Mock
//    private EmployeeRepository employeeRepository;
//    @InjectMocks
//    private EmployeeService employeeService;

    private EmployeeRepository employeeRepository;
    private EmployeeService employeeService;

    @BeforeEach
    void setUp() {
        employeeRepository = Mockito.mock(EmployeeRepository.class);
        employeeService = new EmployeeServiceImpl(employeeRepository);
    }

    @Test
    @DisplayName("findAllEmployeesTest -> Should return all the employees present in the db")
    @EnabledOnOs(OS.WINDOWS)
    @EnabledOnJre(JRE.JAVA_19)
    @Timeout(value = 3, unit = TimeUnit.SECONDS)
    void findAllEmployeesTest() {

//        fail();                                // Junit5 fail method
//        Assertions.fail("failure message");   // AssertJ fail method

        List<Employee> actualEmployeeList = employeeService.findAllEmployees();

        Employee expectedEmployee = new Employee(102, "Test Employee", "Test Dept",
                new BaseClass("99999999", new Date(), "99999999", new Date()));
        // comparing one object from a list of objects (equals and hashCode method comes into picture here)
        // if you have not implemented the equals and hashCode method properly in your Employee class then this test will fail
        // this method completely  relies on the equals and hashCode methods
        assertThat(actualEmployeeList).contains(expectedEmployee);

        // comparing one object from a list of objects only using few fields
        assertThat(expectedEmployee)
                .usingRecursiveComparison()
                .comparingOnlyFields("employeeId", "employeeName")
                .isIn(actualEmployeeList);


        assertThat(actualEmployeeList).extracting(Employee :: getEmployeeName)
                .doesNotContain("DUMMY NAME1", "DUMMY NAME2");

        assertThat(actualEmployeeList).extracting("employeeName", "employeeDept")
                .contains(tuple("Saumya", "Software Engineering"),
                          tuple("Gaurav Kumar", "Chartered Accountant"));
    }

    @Test
    @DisplayName("findEmployeeTest -> should return an employee object based on the employeeId provided")
    @EnabledOnOs(OS.WINDOWS)
    @EnabledOnJre(JRE.JAVA_19)
    @DisabledOnJre(JRE.JAVA_8)
    @DisabledOnOs(OS.LINUX)
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
        assertThat(actualEmployee.getEmployeeDept())
                .as("CHECK " + actualEmployee.getEmployeeName() + "'S DEPARTMENT")
                .isEqualToIgnoringCase(expectedEmployeeDept);

        assertThat(actualEmployee.getEmployeeName())
                .startsWithIgnoringCase("test")
                .contains("Employee")
                .doesNotContain("dummy");

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
    @DisplayName("addEmployeeTest -> should add an employee to the db")
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
    @DisplayName("removeEmployeeTest -> should remove an employee based on the employeeId provided")
    void removeEmployeeTest() {
    //    fail();
//        assertTrue(true);

        assertThatThrownBy(() -> {
            employeeService.removeEmployee(0);
        }).isInstanceOf(UserNotFoundException.class)
                .hasMessage("Employee with the employee id 0 was not found");

        assertThatThrownBy( () -> {
            employeeService.removeEmployee(null);
        }).isInstanceOf(UserNotFoundException.class)
                .hasMessage("Employee Id is null");

    }

    @Test
    @DisplayName("updateEmployeeUsingPutMethodTest -> should update an employee's details")
    void updateEmployeeUsingPutMethodTest() {
    //    fail();
        assertTrue(true);
    }

    @Test
    @DisplayName("updateEmployeeUsingPatchMethodTest -> should update an employee's details")
    void updateEmployeeUsingPatchMethodTest() {
    //    fail();
        assertTrue(true);
    }

    @Test
    @Disabled
    void disabledTest() {

    }

    @ParameterizedTest
    @MethodSource("employeeObjectsProvider")
    void addEmployeeWithObjectInputParameterizedTest(Employee employee) {

        Employee savedEmployee = employeeService.addEmployee(employee);

        assertThat(savedEmployee).isNotNull();
        assertThat(employee.getEmployeeName()).isEqualTo(savedEmployee.getEmployeeName());
        assertThat(employee.getEmployeeDept()).isEqualTo(savedEmployee.getEmployeeDept());
    }

    static Stream<Arguments> employeeObjectsProvider() {
        return Stream.of(
                Arguments.arguments(new Employee(1000001, "Test Employee1",
                                "Test Dept1", new BaseClass("99999999", new Date(),
                                "99999999", new Date()))),
                 Arguments.arguments(new Employee(1000002, "Test Employee2",
                                 "Test Dept2", new BaseClass("99999999", new Date(),
                                 "99999999", new Date()))),
                 Arguments.arguments(new Employee(1000003, "Test Employee3",
                         "Test Dept3", new BaseClass("99999999", new Date(),
                         "99999999", new Date())))
        );
    }

    @ParameterizedTest
    @CsvSource({
            "CSV User1, CSV Dept1",
            "CSV User3, CSV Dept3",
    })
    public void addEmployeeUsingCsvInputParameterizedTest(String employeeName, String employeeDept) {
        Employee employee1 = new Employee(1, employeeName, employeeDept, new BaseClass("5555", new Date(), "5555", new Date()));

        Employee savedEmployee = employeeService.addEmployee(employee1);

        assertThat(savedEmployee.getEmployeeName()).isEqualTo(employeeName);
        assertThat(savedEmployee.getEmployeeDept()).isEqualTo(employeeDept);

    }

    @ParameterizedTest
    @MethodSource("PersonPropsProvider")
    public void addEmployeeUsingPropertyInputMethodSourceParameterizedTest(String employeeName, String employeeDept) {
        Employee employee1 = new Employee(1, employeeName, employeeDept, new BaseClass("5555", new Date(), "5555", new Date()));

        Employee savedEmployee = employeeService.addEmployee(employee1);

        assertThat(savedEmployee.getEmployeeName()).isEqualTo(employeeName);
        assertThat(savedEmployee.getEmployeeDept()).isEqualTo(employeeDept);

    }

    static Stream<Arguments> PersonPropsProvider() {
        return Stream.of(
                Arguments.arguments("Demo User1", "Demo Dept1"),
                Arguments.arguments("Demo User2", "Demo Dept2")
        );
    }
}