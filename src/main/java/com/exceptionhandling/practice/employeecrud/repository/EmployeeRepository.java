package com.exceptionhandling.practice.employeecrud.repository;

import com.exceptionhandling.practice.employeecrud.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    Employee findByEmployeeId(int employeeId);
}
