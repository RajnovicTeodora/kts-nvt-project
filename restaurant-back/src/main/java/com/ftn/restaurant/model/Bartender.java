package com.ftn.restaurant.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import com.ftn.restaurant.dto.EmployeeDTO;

import java.util.List;

@Entity
public class Bartender extends Employee{

    @OneToMany(fetch = FetchType.EAGER, cascade= CascadeType.ALL)
    private List<OrderedItem> orderedItems;

    public List<OrderedItem> getOrderedItems() {
        return orderedItems;
    }

    public Bartender() {
        super();
    }

    public void setOrderedItems(List<OrderedItem> orderedItems) {
        this.orderedItems = orderedItems;
    }

    public Bartender(EmployeeDTO employeeDTO){
        super(employeeDTO);
    }

    public Bartender(String username, String password, boolean deleted, List<OrderedItem> orderedItems) {
        super(username, password, deleted);
        this.orderedItems = orderedItems;
    }
}
