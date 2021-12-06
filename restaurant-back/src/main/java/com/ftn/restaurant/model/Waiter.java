package com.ftn.restaurant.model;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import com.ftn.restaurant.dto.EmployeeDTO;

import java.util.List;

@Entity
public class Waiter extends Employee{

    @OneToMany(fetch = FetchType.LAZY, cascade= CascadeType.ALL)
    private List<RestaurantTable> tables;

    @OneToMany(fetch = FetchType.EAGER, cascade= CascadeType.ALL)
    private List<Notification> notifications;

    public List<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }

    public List<RestaurantTable> getTables() {
        return tables;
    }

    public void setTables(List<RestaurantTable> tables) {
        this.tables = tables;
    }

    public Waiter(EmployeeDTO employeeDTO){
        super(employeeDTO);
        this.setRole(new UserRole("WAITER"));
    }

    public Waiter() {
        this.setRole(new UserRole("WAITER"));
    }


}
