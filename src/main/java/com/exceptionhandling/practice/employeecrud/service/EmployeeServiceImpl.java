package com.exceptionhandling.practice.employeecrud.service;

import com.exceptionhandling.practice.employeecrud.dto.EmployeeDto;
import com.exceptionhandling.practice.employeecrud.entity.BaseClass;
import com.exceptionhandling.practice.employeecrud.entity.Employee;
import com.exceptionhandling.practice.employeecrud.exceptions.UserNotFoundException;
import com.exceptionhandling.practice.employeecrud.exceptions.ValidationFailedException;
import com.exceptionhandling.practice.employeecrud.repository.EmployeeRepository;
import com.exceptionhandling.practice.employeecrud.util.EmployeeLogger;
import jakarta.transaction.Transactional;
import org.apache.el.util.ReflectionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@Transactional
@Primary
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository repository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.repository = employeeRepository;
    }

    @Override
    public List<Employee> findAllEmployees() {
        return repository.findAllEmployeesUsingNativeQuery();
    }

    @Override
    public Employee findEmployee(int employeeId) throws UserNotFoundException {
        EmployeeLogger.logStart(this, "findEmployee");

        EmployeeLogger.logInfo(this, "calling the repository findByEmployeeId...");
        Employee employee = repository.findByEmployeeId(employeeId);

        if(employee == null) {
            throw new UserNotFoundException("Employee with the employee id " + employeeId + " was not found");
        }

        EmployeeLogger.logEnd(this, "findEmployee");

        return employee;

    }

    @Override
    public Employee addEmployee(Employee employee) {
        EmployeeLogger.logStart(this, "addEmployee");



        EmployeeLogger.logInfo(this, "calling the repository save method...");
        Employee  employeeAfterSaving =repository.save(employee);

        EmployeeLogger.logEnd(this, "addEmployee");
        return employeeAfterSaving;

    }

    @Override
    public void removeEmployee(Integer employeeId) throws UserNotFoundException {

        EmployeeLogger.logStart(this, "removeEmployee");

        EmployeeLogger.logInfo(this, "calling findEmployee...");

        Employee employee = null;
        try {
            employee = this.findEmployee(employeeId);
        } catch (NullPointerException e) {
            throw new UserNotFoundException("Employee Id is null");
        } catch (UserNotFoundException e) {
            throw new UserNotFoundException("Employee with the employee id " + employeeId + " was not found");
        }

        if(employee == null) {
             throw new UserNotFoundException("Employee not Found");
        }else {
            EmployeeLogger.logInfo(this, "calling deleteById...");
            try {
                repository.deleteById(employeeId);
            } catch (NullPointerException e) {
                throw new UserNotFoundException("Employee Id is null");
            }

        }

        EmployeeLogger.logEnd(this, "removeEmployee");

    }

    @Override
    public Employee updateEmployeeUsingPutMethod(Employee employee) throws UserNotFoundException {

        EmployeeLogger.logStart(this, "updateEmployeeUsingPutMethod");

        Employee fetchedEmployee = findEmployee(employee.getEmployeeId());
        if(fetchedEmployee == null) {
            throw new RuntimeException("Employee not Found");
        }else {
            employee.setBaseClass(new BaseClass(fetchedEmployee.getBaseClass().getCreateUser(), fetchedEmployee.getBaseClass().getCreateTimestamp(), "2222", new Date()));

        }
        EmployeeLogger.logInfo(this, "calling save method...");
        Employee returnedEmployee = repository.save(employee);

        EmployeeLogger.logEnd(this, "updateEmployeeUsingPutMethod");
        return returnedEmployee;
    }

    @Override
    public Employee updateEmployeeUsingPatchMethod(int employeeId, Map<Object, Object> fields) throws UserNotFoundException, ValidationFailedException {
       EmployeeLogger.logStart(this, "updateEmployeeUsingPatchMethod");

       EmployeeLogger.logInfo(this, "calling findEmployee...");
        Employee fetchedEmployee = findEmployee(employeeId);

        if(fetchedEmployee == null) {
            throw new UserNotFoundException("Employee not Found");
        }else{
            fields.forEach((key, value) -> {
                Field field = ReflectionUtils.findField(Employee.class, (String) key);
                field.setAccessible(true);
                if(value == null || value  == "") {
                    throw new ValidationFailedException("Value of "+ key +" shouldn't be empty or null");
                } else {
                    ReflectionUtils.setField(field, fetchedEmployee, value);
                }
            });

            fetchedEmployee.setBaseClass(new BaseClass(fetchedEmployee.getBaseClass().getCreateUser(), fetchedEmployee.getBaseClass().getCreateTimestamp(), "2222", new Date()));
        }

        EmployeeLogger.logInfo(this, "calling fetchedEmployee...");
        Employee returnedEmployee = repository.save(fetchedEmployee);

        EmployeeLogger.logEnd(this, "updateEmployeeUsingPatchMethod");
        return returnedEmployee;
    }

}
