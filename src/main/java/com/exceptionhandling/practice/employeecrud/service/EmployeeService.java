package com.exceptionhandling.practice.employeecrud.service;

import com.exceptionhandling.practice.employeecrud.dto.EmployeeDto;
import com.exceptionhandling.practice.employeecrud.entity.Employee;
import com.exceptionhandling.practice.employeecrud.exceptions.UserNotFoundException;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public interface EmployeeService {

    public List<Employee> findAllEmployees();
    public Employee findEmployee(int employeeId) throws UserNotFoundException;

    public Employee addEmployee(Employee employee);
    public void removeEmployee(int employeeId) throws UserNotFoundException;

    public Employee updateEmployeeUsingPutMethod(Employee employee) throws UserNotFoundException;

    Employee updateEmployeeUsingPatchMethod(int employeeId, Map<Object, Object> fields) throws UserNotFoundException;
}
