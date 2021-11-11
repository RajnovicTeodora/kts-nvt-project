package com.ftn.restaurant.model;

import javax.persistence.*;
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
}
