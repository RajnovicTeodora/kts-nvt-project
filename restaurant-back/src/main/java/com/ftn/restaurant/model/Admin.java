package com.ftn.restaurant.model;


import javax.persistence.Entity;
import com.ftn.restaurant.dto.UserDTO;


@Entity
public class Admin extends User{

    public Admin() {
        super();
        this.setRole(new UserRole("ADMIN"));
    }

    public Admin(UserDTO userDTO){
        super(userDTO.getUsername(), userDTO.getPassword(), false);
        this.setRole(new UserRole("ADMIN"));
    }

}
