package com.exceptionhandling.practice.employeecrud.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="T_EMPLOYEE_DETAILS")
public class Employee{
    @Id
    @GeneratedValue
    @Column(length = 50, name = "EMPLOYEE_ID")
    private int employeeId;
    @Column(length = 50, name = "EMPLOYEE_NAME")
    private String employeeName;
    @Column(length = 50, name = "EMPLOYEE_DEPT")
    private String employeeDept;
    @Embedded
    private BaseClass baseClass;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return employeeId == employee.employeeId && employeeName.equals(employee.employeeName) && employeeDept.equals(employee.employeeDept);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeId, employeeName, employeeDept);
    }
}
