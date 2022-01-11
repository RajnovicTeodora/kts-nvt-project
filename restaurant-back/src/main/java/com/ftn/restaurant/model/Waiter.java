package com.ftn.restaurant.model;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

import com.ftn.restaurant.dto.EmployeeDTO;

import java.util.List;

@Entity
public class Waiter extends Employee{

    @OneToMany(fetch = FetchType.LAZY, cascade= CascadeType.ALL)
    @Column(name = "is_active", nullable=false)
    private List<RestaurantTable> tables;

    @OneToMany(mappedBy = "waiter",fetch = FetchType.LAZY, cascade= CascadeType.ALL)//@OneToMany(fetch = FetchType.EAGER, cascade= CascadeType.ALL)
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

    public Waiter(String username, String password, boolean deleted) {
        super(username, password, deleted);
    }
}
