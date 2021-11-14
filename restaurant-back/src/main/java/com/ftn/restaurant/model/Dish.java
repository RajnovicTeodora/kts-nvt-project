package com.ftn.restaurant.model;

import com.ftn.restaurant.model.enums.DishType;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
public class Dish extends MenuItem{

    @Column(name = "dish_type")
    @Enumerated(EnumType.STRING)
    private DishType dishType;

    public Dish(String name, String image, boolean b, boolean b1, ArrayList<MenuItemPrice> menuItemPrices, DishType type) {
        super(name, image, b, b1, menuItemPrices);
        this.dishType = type;
    }

    public Dish(){}

    public DishType getDishType() {
        return dishType;
    }

    public void setDishType(DishType dishType) {
        this.dishType = dishType;
    }
}
