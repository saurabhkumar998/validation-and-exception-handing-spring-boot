package com.exceptionhandling.practice.employeecrud.service;

import com.exceptionhandling.practice.employeecrud.dto.EmployeeDto;
import com.exceptionhandling.practice.employeecrud.entity.Employee;
import com.exceptionhandling.practice.employeecrud.exceptions.UserNotFoundException;

import java.util.List;

public interface EmployeeService {

    public List<Employee> findAllEmployees();
    public Employee findEmployee(int employeeId) throws UserNotFoundException;

    public Employee addEmployee(EmployeeDto employeeDto);
    public void removeEmployee(int employeeId) throws UserNotFoundException;
}
