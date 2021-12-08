package com.ftn.restaurant.repository;

import java.util.Optional;

import com.ftn.restaurant.model.UserRole;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface UserRoleRepository extends JpaRepository<UserRole, Long>{

    Optional<UserRole> findById(@Param("id") long id);
    
}