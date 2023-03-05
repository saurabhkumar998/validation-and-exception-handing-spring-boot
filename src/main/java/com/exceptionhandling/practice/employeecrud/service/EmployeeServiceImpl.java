package com.exceptionhandling.practice.employeecrud.service;

import com.exceptionhandling.practice.employeecrud.dto.EmployeeDto;
import com.exceptionhandling.practice.employeecrud.entity.Employee;
import com.exceptionhandling.practice.employeecrud.exceptions.UserNotFoundException;
import com.exceptionhandling.practice.employeecrud.repository.EmployeeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository repository;

    @Override
    public List<Employee> findAllEmployees() {
        return repository.findAll();
    }

    @Override
    public Employee findEmployee(int employeeId) throws UserNotFoundException {
        Employee employee = repository.findByEmployeeId(employeeId);

        if(employee == null) {
            throw new UserNotFoundException("Employee with the employee id " + employeeId + " was not found");
        }else {
            return employee;
        }

    }

    @Override
    public Employee addEmployee(EmployeeDto employeeDto) {
        Employee employee = new Employee(0, employeeDto.getEmployeeName(), employeeDto.getEmployeeDept());
        return repository.save(employee);

    }

    @Override
    public void removeEmployee(int employeeId) throws UserNotFoundException {

        Employee employee = this.findEmployee(employeeId);

        if(employee == null) {
             throw new RuntimeException("Employee not Found");
        }else {
            repository.deleteById(employeeId);
        }
    }
}
