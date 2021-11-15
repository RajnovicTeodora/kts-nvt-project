package com.ftn.restaurant.repository;

import com.ftn.restaurant.model.Employee;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    
    public Page<Employee> findAll(Pageable pageable);

    public Employee findByUsername(String username);
}
