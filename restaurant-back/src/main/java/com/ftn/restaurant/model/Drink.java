package com.ftn.restaurant.model;

import com.ftn.restaurant.model.enums.ContainerType;
import com.ftn.restaurant.model.enums.DrinkType;

import javax.persistence.*;
import javax.persistence.Table;

@Entity
public class Drink extends MenuItem{

    @Column(name = "drink_type", nullable=false)
    @Enumerated(EnumType.STRING)
    private DrinkType drinkType;

    @Column(name = "container_type", nullable=false)
    @Enumerated(EnumType.STRING)
    private ContainerType containerType;

    public DrinkType getDrinkType() {
        return drinkType;
    }

    public void setDrinkType(DrinkType drinkType) {
        this.drinkType = drinkType;
    }

    public ContainerType getContainerType() {
        return containerType;
    }

    public void setContainerType(ContainerType containerType) {
        this.containerType = containerType;
    }
}
