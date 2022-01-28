package com.ftn.restaurant.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.restaurant.model.User;
import com.ftn.restaurant.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User findByUsername(String username) {
        // TODO Auto-generated method stub
        return userRepository.findByUsername(username);
    }

    public void save(User user) {
        userRepository.save(user);
    }
}
