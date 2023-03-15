package com.exceptionhandling.practice.employeecrud.controller;

import com.exceptionhandling.practice.employeecrud.dto.EmployeeDto;
import com.exceptionhandling.practice.employeecrud.entity.Employee;
import com.exceptionhandling.practice.employeecrud.exceptions.UserNotFoundException;
import com.exceptionhandling.practice.employeecrud.service.EmployeeService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EmployeeController extends BaseController {

    Logger logger = LoggerFactory.getLogger(EmployeeController.class);
    @Autowired
    private EmployeeService employeeService;

    // Using Path Variable/Parameter
    @GetMapping(value="/employees/{employeeId}")
    public ResponseEntity<Employee> retrieveEmployeeByPathVariable(@PathVariable("employeeId") int employeeId) throws UserNotFoundException {

        logger.trace("retrieveEmployeeByPathVariable method called");
        return new ResponseEntity<Employee>(employeeService.findEmployee(employeeId), HttpStatus.OK);
    }

    /*
    Get method using Request Parameter
    e.g. localhost:7777/api/employees?employeeId=602
     */
    @GetMapping("/employees/")
    public ResponseEntity<Employee> retrieveEmployeeByRequestParameter(@RequestParam("employeeId") int employeeId) throws UserNotFoundException {
        return new ResponseEntity<>(employeeService.findEmployee(employeeId),HttpStatus.OK);
    }

    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return new ResponseEntity<>(employeeService.findAllEmployees(), HttpStatus.OK);
    }

    @PostMapping(value="/employees", consumes="application/json")
    public ResponseEntity<Employee> addNewEmployee(@RequestBody @Validated EmployeeDto employeeDto) {

        return new ResponseEntity<>(employeeService.addEmployee(employeeDto), HttpStatus.CREATED);
    }

    @DeleteMapping("/employees/{employeeId}")
    public ResponseEntity<String> deleteEmployee(@PathVariable("employeeId") int employeeId) throws UserNotFoundException {
        employeeService.removeEmployee(employeeId);
        return new ResponseEntity<String>("Successfully Deleted the Employee", HttpStatus.OK);

    }
}
