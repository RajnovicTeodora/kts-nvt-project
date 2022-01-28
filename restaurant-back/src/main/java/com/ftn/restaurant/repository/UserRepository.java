package com.ftn.restaurant.repository;


import com.ftn.restaurant.model.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ftn.restaurant.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    public Page<User> findAll(Pageable pageable);

    Optional<User> findByUsername(String username);

    @Query("select u from User u where " + 
        "u.username = :username and u.deleted = false")
    Optional<User> findByUsernameAndNotDeleted(@Param("username") String username);

    @Query("select user.loggedFirstTime from User user where user.username =?1")
    boolean findIsLoggedInFirstTimeByUsername(String username);

    @Query("select user.id from User user where user.username =?1")
    long findUserIdByUsername(String username);
}
