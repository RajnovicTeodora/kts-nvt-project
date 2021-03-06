package com.ftn.restaurant.model;

import javax.persistence.*;

import com.ftn.restaurant.dto.EmployeeDTO;

import java.util.List;

@Entity
public class Waiter extends Employee{

    @OneToMany(fetch = FetchType.LAZY, cascade= CascadeType.ALL)
    @Column(name = "is_active", nullable=false)
    private List<RestaurantTable> tables;

    public List<RestaurantTable> getTables() {
        return tables;
    }

    public void setTables(List<RestaurantTable> tables) {
        this.tables = tables;
    }

    public Waiter(EmployeeDTO employeeDTO){
        super(employeeDTO);
    }

    public Waiter() {
        super();
    }

    public Waiter(String username, String password, boolean deleted) {
        super(username, password, deleted);
    }
}
