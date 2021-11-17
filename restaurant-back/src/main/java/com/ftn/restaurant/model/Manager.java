package com.ftn.restaurant.model;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.ftn.restaurant.dto.EmployeeDTO;

import java.util.Collection;

@Entity
public class Manager extends Employee{
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    public Manager(EmployeeDTO employeeDTO){
        super(employeeDTO);
    }
}
