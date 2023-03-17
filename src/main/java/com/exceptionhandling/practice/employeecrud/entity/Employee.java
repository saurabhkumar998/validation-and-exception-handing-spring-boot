package com.exceptionhandling.practice.employeecrud.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity(name="T_EMPLOYEE_DETAILS")
@Data
@NoArgsConstructor
@AllArgsConstructor
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

}
