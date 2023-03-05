package com.exceptionhandling.practice.employeecrud.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity(name="T_EMPLOYEE_DETAILS")
public class Employee {
    @Id
    @GeneratedValue
    private int employeeId;
    private String employeeName;
    private String employeeDept;

    public Employee(){

    }
    public Employee(int employeeId, String employeeName, String employeeDept) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.employeeDept =  employeeDept;

    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

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

    @Override
    public String toString() {
        return "Employee{" +
                "employeeId=" + employeeId +
                ", employeeName='" + employeeName + '\'' +
                ", employeeDept='" + employeeDept + '\'' +
                '}';
    }
}
