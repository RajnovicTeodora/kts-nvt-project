package com.ftn.restaurant.repository;

import com.ftn.restaurant.model.Order;
import com.ftn.restaurant.model.User;
import com.ftn.restaurant.model.Waiter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WaiterRepository extends JpaRepository<Waiter, Long> {

    public Waiter findByUsername(String username);
}
