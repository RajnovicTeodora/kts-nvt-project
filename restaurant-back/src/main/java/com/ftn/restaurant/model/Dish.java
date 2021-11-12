package com.ftn.restaurant.model;

import com.ftn.restaurant.model.enums.DishType;

import javax.persistence.*;

@Entity
public class Dish extends MenuItem{

    @Column(name = "dish_type")
    @Enumerated(EnumType.STRING)
    private DishType dishType;

    public DishType getDishType() {
        return dishType;
    }

    public void setDishType(DishType dishType) {
        this.dishType = dishType;
    }
}
