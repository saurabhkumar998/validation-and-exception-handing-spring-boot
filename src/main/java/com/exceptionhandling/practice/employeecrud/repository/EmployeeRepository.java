package com.exceptionhandling.practice.employeecrud.repository;

import com.exceptionhandling.practice.employeecrud.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    public Employee findByEmployeeId(int employeeId);

    // using jpql
    @Query(value = "select e from Employee e where e.employeeName= ?1", nativeQuery = false)
    public List<Employee> findEmployeeByName(String employeeName);
    // Using native query
    @Query(value = "select * from t_employee_details", nativeQuery = true)
    public List<Employee> findAllEmployeesUsingNativeQuery();

}
