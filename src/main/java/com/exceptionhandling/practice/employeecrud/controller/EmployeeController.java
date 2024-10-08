package com.exceptionhandling.practice.employeecrud.controller;

import com.exceptionhandling.practice.employeecrud.dto.EmployeeDto;
import com.exceptionhandling.practice.employeecrud.entity.BaseClass;
import com.exceptionhandling.practice.employeecrud.entity.Employee;
import com.exceptionhandling.practice.employeecrud.exceptions.UserNotFoundException;
import com.exceptionhandling.practice.employeecrud.service.EmployeeService;

import com.exceptionhandling.practice.employeecrud.util.EmployeeLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api")
public class EmployeeController extends BaseController {

    @Autowired
    private EmployeeService employeeService;

    // Using Path Variable/Parameter

    @GetMapping(value="/employees/{employeeId}")
    public ResponseEntity<Employee> retrieveEmployeeByPathVariable(@PathVariable("employeeId") int employeeId) throws UserNotFoundException {

        EmployeeLogger.logStart(this,"retrieveEmployeeByPathVariable");

        EmployeeLogger.logInfo(this,"Calling findEmployee method...");

        Employee employee = employeeService.findEmployee(employeeId);

        EmployeeLogger.logEnd(this, "retrieveEmployeeByPathVariable");

        return new ResponseEntity<Employee>(employee, HttpStatus.OK);

    }

    /*
    Get method using Request Parameter
    e.g. localhost:7777/api/employees?employeeId=602
     */
    @GetMapping("/employees/")
    public ResponseEntity<Employee> retrieveEmployeeByRequestParameter(@RequestParam("employeeId") int employeeId) throws UserNotFoundException {

        EmployeeLogger.logStart(this, "retrieveEmployeeByRequestParameter");

        EmployeeLogger.logInfo(this, "calling findEmployee method...");

        Employee employee = employeeService.findEmployee(employeeId);

        EmployeeLogger.logEnd(this, "retrieveEmployeeByRequestParameter");

        return new ResponseEntity<>(employee,HttpStatus.OK);
    }

    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        EmployeeLogger.logStart(this, "getAllEmployees");

        EmployeeLogger.logInfo(this, "calling findAllEmployees method...");

        List<Employee> employees = employeeService.findAllEmployees();

        EmployeeLogger.logEnd(this, "getAllEmployees");

        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @PostMapping(value="/employees", consumes="application/json", produces = "application/xml")
    public ResponseEntity<Employee> addNewEmployee(@RequestBody @Validated EmployeeDto employeeDto) {

        EmployeeLogger.logStart(this, "addNewEmployee");

        EmployeeLogger.logInfo(this,  "calling addEmployee method...");

        Employee employee = new Employee();
        employee.setEmployeeName(employeeDto.getEmployeeName());
        employee.setEmployeeDept(employeeDto.getEmployeeDept());
        employee.setBaseClass(new BaseClass("1111",new Date(), "1111", new Date()));

        Employee savedEmployee = employeeService.addEmployee(employee);

        EmployeeLogger.logEnd(this, "addNewEmployee");

        return new ResponseEntity<>(employee, HttpStatus.CREATED);
    }

    @PutMapping(value = "/employees/{employeeId}", consumes = "application/json")
    public ResponseEntity<Employee> updateEmployeeUsingPut(@PathVariable("employeeId") int employeeId,
                                                           @RequestBody @Validated EmployeeDto employeeDto) throws UserNotFoundException {
       EmployeeLogger.logStart(this, "updateEmployeeUsingPut");

        Employee employee = new Employee();
        employee.setEmployeeId(employeeId);
        employee.setEmployeeName(employeeDto.getEmployeeName());
        employee.setEmployeeDept(employeeDto.getEmployeeDept());

        EmployeeLogger.logInfo(this, "calling updateEmployeeUsingPutMethod...");
        employee = employeeService.updateEmployeeUsingPutMethod(employee);

        EmployeeLogger.logEnd(this, "updateEmployeeUsingPut");
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    @PatchMapping(value = "/employees/{employeeId}", consumes = "application/json")
    public ResponseEntity<Employee> updateEmployeeUsingPatch(@PathVariable("employeeId") int employeeId,
                                                             @RequestBody @Validated Map<Object, Object> fields) throws UserNotFoundException {

        EmployeeLogger.logStart(this, "updateEmployeeUsingPatch");
        Employee employee = null;

        EmployeeLogger.logInfo(this, "calling the updateEmployeeUsingPatchMethod... ");
        employee = employeeService.updateEmployeeUsingPatchMethod(employeeId, fields);

        EmployeeLogger.logEnd(this, "updateEmployeeUsingPatch");
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    @DeleteMapping("/employees/{employeeId}")
    public ResponseEntity<String> deleteEmployee(@PathVariable(name = "employeeId", required = true) int employeeId)
            throws UserNotFoundException {

        EmployeeLogger.logStart(this, "deleteEmployee");

        EmployeeLogger.logInfo(this, "calling deleteEmployee method...");

        employeeService.removeEmployee(employeeId);

        EmployeeLogger.logEnd(this, "deleteEmployee");

        return new ResponseEntity<String>("Successfully Deleted the Employee", HttpStatus.OK);
    }
}
