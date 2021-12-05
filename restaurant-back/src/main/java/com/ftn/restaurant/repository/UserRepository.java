package com.ftn.restaurant.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ftn.restaurant.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    public Page<User> findAll(Pageable pageable);

    // Metoda za autentifikaciju
    public User findByUsername(String username);
}
