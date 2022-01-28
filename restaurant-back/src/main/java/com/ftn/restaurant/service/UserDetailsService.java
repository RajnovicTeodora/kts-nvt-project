package com.ftn.restaurant.service;

import com.ftn.restaurant.exception.NotFoundException;
import com.ftn.restaurant.model.User;
import com.ftn.restaurant.model.UserRole;
import com.ftn.restaurant.repository.UserRepository;
import com.ftn.restaurant.repository.UserRoleRepository;
import com.ftn.restaurant.security.UserFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = this.userRepository.findByUsernameAndNotDeleted(username)
                .orElseThrow(() -> new NotFoundException(String.format("User with username '%s' is not found!", username)));

        return UserFactory.create(user);
    }

    public String findRoleByUsername(String username){
        Optional<UserRole> ret = userRoleRepository.findByUsername(username);
        if(ret.isPresent()){
            return ret.get().getName();
        }
        return "";
    }


}
