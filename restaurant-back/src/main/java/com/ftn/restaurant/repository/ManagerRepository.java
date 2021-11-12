package com.ftn.restaurant.repository;

import com.ftn.restaurant.model.Manager;
import com.ftn.restaurant.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ManagerRepository extends JpaRepository<Manager, Long> {

    Optional<Manager> findById(@Param("id") long id);
}
