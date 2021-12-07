package com.ftn.restaurant.repository;

import java.util.Optional;

import com.ftn.restaurant.model.Employee;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    
    // Page<Employee> findAll(Pageable pageable);

    Optional<Employee> findByUsername(@Param("username") String username);
}
