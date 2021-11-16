package com.ftn.restaurant.dto;

import com.ftn.restaurant.model.Dish;
import com.ftn.restaurant.model.Drink;

public class DishDTO extends MenuItemDTO {

    private String dishType;

    public DishDTO(Dish dish) {
        super(dish);
        this.dishType = dish.getDishType().toString();
    }

    public String getDishType() {
        return dishType;
    }

    public void setDishType(String dishType) {
        this.dishType = dishType;
    }
}
