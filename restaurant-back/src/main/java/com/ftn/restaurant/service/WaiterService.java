package com.ftn.restaurant.service;

import com.ftn.restaurant.model.User;
import com.ftn.restaurant.model.Waiter;
import com.ftn.restaurant.repository.WaiterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WaiterService {

    @Autowired
    private WaiterRepository waiterRepository;

    public Waiter findByUsername(String username) {
        return waiterRepository.findByUsername(username);
    }

    public void save(Waiter user) {
        waiterRepository.save(user);
    }
}
