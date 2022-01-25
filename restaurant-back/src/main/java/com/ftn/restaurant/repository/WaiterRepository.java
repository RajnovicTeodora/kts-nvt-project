package com.ftn.restaurant.repository;

import com.ftn.restaurant.model.Order;
import com.ftn.restaurant.model.User;
import com.ftn.restaurant.model.Waiter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WaiterRepository extends JpaRepository<Waiter, Long> {

    @Query("select w from Waiter w where w.username =?1")
    Waiter findByUsername(String username);

    @Query("select w from Waiter w where w.id =?1")
    Optional<Waiter> findById(long id);
}
