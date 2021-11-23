package com.ftn.restaurant.model;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

import com.ftn.restaurant.dto.EmployeeDTO;

import java.util.Collection;
import java.util.List;

@Entity
public class Chef extends Employee {

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<OrderedItem> orderedItems;

    public List<OrderedItem> getOrderedItems() {
        return orderedItems;
    }

    public void setOrderedItems(List<OrderedItem> orderedItems) {
        this.orderedItems = orderedItems;
    }

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

    public Chef(EmployeeDTO employeeDTO){
        super(employeeDTO);
    }


    public Chef() {
    }

}
