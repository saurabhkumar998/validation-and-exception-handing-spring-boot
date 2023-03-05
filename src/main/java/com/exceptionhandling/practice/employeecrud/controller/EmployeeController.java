package com.exceptionhandling.practice.employeecrud.controller;

import com.exceptionhandling.practice.employeecrud.dto.EmployeeDto;
import com.exceptionhandling.practice.employeecrud.entity.Employee;
import com.exceptionhandling.practice.employeecrud.exceptions.UserNotFoundException;
import com.exceptionhandling.practice.employeecrud.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EmployeeController extends BaseController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping(value="/employees/{employeeId}")
    public ResponseEntity<Employee> retrieveEmployee(@PathVariable("employeeId") int employeeId) throws UserNotFoundException {

        return new ResponseEntity<Employee>(employeeService.findEmployee(employeeId), HttpStatus.OK);
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
