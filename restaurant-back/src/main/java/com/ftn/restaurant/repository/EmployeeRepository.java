package com.ftn.restaurant.repository;

import java.util.List;
import java.util.Optional;

import com.ftn.restaurant.model.Employee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    
    // Page<Employee> findAll(Pageable pageable);

    Optional<Employee> findByUsername(@Param("username") String username);

    @Query("select e from Employee e where " + 
        "((lower(e.name) like lower(concat('%', :search,'%'))) or " +
        "(lower(e.username) like lower(concat('%', :search,'%'))) or" +
        "(lower(e.surname) like lower(concat('%', :search,'%'))))  and"+
        "(e.role.name = :filter OR :filter = '') and e.deleted = false")
    List<Employee> findBySearchCriteriaAndUserRoleAndNotDeleted(@Param("search") String search, @Param("filter") String filter);
}
