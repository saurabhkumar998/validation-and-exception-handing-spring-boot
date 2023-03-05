package com.exceptionhandling.practice.employeecrud.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class EmployeeDto {

    @NotNull(message = "Employee Name shouldn't be null")
    @NotEmpty(message = "Employee Name shouldn't be empty")
    private String employeeName;
    @NotNull(message = "Employee Department should not be null")
    @NotEmpty(message = "Employee Department shouldn't be empty")
    private String employeeDept;

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeDept() {
        return employeeDept;
    }

    public void setEmployeeDept(String employeeDept) {
        this.employeeDept = employeeDept;
    }
}
