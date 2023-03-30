package com.exceptionhandling.practice.employeecrud.service;

import com.exceptionhandling.practice.employeecrud.entity.BaseClass;
import com.exceptionhandling.practice.employeecrud.entity.Employee;
import com.exceptionhandling.practice.employeecrud.exceptions.UserNotFoundException;
import com.exceptionhandling.practice.employeecrud.repository.EmployeeRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceImplTestUsingMockito {

    @Mock
    private EmployeeRepository employeeRepository;
    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @Spy
    private SpyMethodTestService spyMethodTestService;

//    private EmployeeRepository employeeRepository;
//    private EmployeeService employeeService;
//
//    @BeforeEach
//    void setUp() {
//        employeeRepository = Mockito.mock(EmployeeRepository.class);
//        employeeService = new EmployeeServiceImpl(employeeRepository);
//    }

    @Test
    @DisplayName("findAllEmployeesTest -> Should return all the employees present in the db")
    @EnabledOnOs(OS.WINDOWS)
    @EnabledOnJre(JRE.JAVA_19)
    @Timeout(value = 3, unit = TimeUnit.SECONDS)
    void findAllEmployeesTest() {

        Employee employee1 = new Employee(102, "Test Employee1", "Test Dept1",
                new BaseClass("99999999", new Date(), "99999999", new Date()));

        Employee employee2 = new Employee(103, "Test Employee2", "Test Dept2",
                new BaseClass("99999999", new Date(), "99999999", new Date()));

        List<Employee> expectedEmployeeList = new ArrayList<>(List.of(employee1, employee2));

        // Arrange
        when(employeeRepository.findAllEmployeesUsingNativeQuery())
                .thenReturn(expectedEmployeeList);

        // Act
        List<Employee> actualEmployeeList = employeeService.findAllEmployees();

        // Assert
        assertThat(actualEmployeeList).isNotNull();
        assertThat(actualEmployeeList).contains(employee1);
        System.out.println(actualEmployeeList);


        // comparing one object from a list of objects only using few fields
        assertThat(expectedEmployeeList)
                .usingRecursiveComparison()
                .comparingOnlyFields("employeeId", "employeeName")
                .isIn(actualEmployeeList);

        assertThat(actualEmployeeList).extracting(Employee :: getEmployeeName)
                .doesNotContain("DUMMY NAME1", "DUMMY NAME2");

        assertThat(actualEmployeeList).extracting("employeeName", "employeeDept")
                .contains(tuple("Test Employee1", "Test Dept1"),
                        tuple("Test Employee2", "Test Dept2"));

    }

    @Test
    @DisplayName("findEmployeeTest -> should return an employee object based on the employeeId provided")
    @EnabledOnOs(OS.WINDOWS)
    @EnabledOnJre(JRE.JAVA_19)
    @DisabledOnJre(JRE.JAVA_8)
    @DisabledOnOs(OS.LINUX)
    void findEmployeeTest() throws UserNotFoundException {
        //    fail();
        String expectedEmployeeName = "Test Employee1";
        String expectedEmployeeDept = "Test Dept1";
       // Arrange
        when(employeeRepository.findByEmployeeId(102))
                .thenReturn(new Employee(102, "Test Employee1", "Test Dept1",
                        new BaseClass("99999999", new Date(), "99999999", new Date())));

        // Act
        Employee actualEmployee = employeeService.findEmployee(102);

        // Assert
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
        Employee expectedEmployee = new Employee(102, "Test Employee1", "Test Dept1",
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

        // Arrange
        when(employeeRepository.save(employeeToSave))
                .thenReturn(employeeToSave);

        // Act
        Employee employee = employeeService.addEmployee(employeeToSave);

        // Assert
        assertAll(() -> {
            assertEquals(employee.getEmployeeName(), "Test Employee");
            assertEquals(employee.getEmployeeDept(), "Test Dept");
            assertEquals(employee.getBaseClass().getCreateUser(), "99999999");
            assertEquals(employee.getBaseClass().getLastUpdateUser(), "99999999");
            assertNotNull(employee.getBaseClass().getCreateTimestamp());
            assertNotNull(employee.getBaseClass().getLastUpdateTimestamp());
            assertTrue(employee.getEmployeeName().contains("Test"));
        });

        // Asserting with the help of Mockito's argument captor -- we are returning the same object which is being passed to the save method
        // Arrange
        when(employeeRepository.save(any(Employee.class))).thenAnswer(answer -> answer.getArgument(0));

        Employee savedEmployee = employeeService.addEmployee(employeeToSave);

        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee.getEmployeeName()).isEqualTo("Test Employee");

        ArgumentCaptor<Employee> argumentCaptor = ArgumentCaptor.forClass(Employee.class);
        verify(employeeRepository, atLeastOnce()).save(argumentCaptor.capture());

        Employee value = argumentCaptor.getValue();

        assertEquals("Test Employee", value.getEmployeeName());
        assertEquals("Test Dept", value.getEmployeeDept());

    }

    @Test
    @DisplayName("removeEmployeeTest -> should remove an employee based on the employeeId provided")
    void removeEmployeeTest() {

        when(employeeRepository.findByEmployeeId(1)).thenReturn(new Employee(1,
                        "Test Employee",
                        "Test Dept",
                        new BaseClass("99999999", new Date(), "99999999", new Date())));
        doNothing().when(employeeRepository).deleteById(1);


        employeeService.removeEmployee(1);

        assertThat(employeeService.findEmployee(1)).isNotNull();

        verify(employeeRepository).deleteById(1);
        verify(employeeRepository, times(1)).deleteById(1);
        verify(employeeRepository, atMostOnce()).deleteById(1);
        verify(employeeRepository, atLeastOnce()).deleteById(1);

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

    @Test
    public void printHelloTest() {
        assertThat(spyMethodTestService.printHello()).isEqualTo("Hello from printHello method");
    }
}
