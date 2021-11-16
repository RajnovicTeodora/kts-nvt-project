package com.ftn.restaurant.dto;

import com.ftn.restaurant.model.Dish;

public class DishDTO extends MenuItemDTO {

    private String dishType;

    public DishDTO(Dish dish) {
        super(dish);
        this.dishType = dish.getDishType().toString();
    }


    public DishDTO() {
    }

    public String getDishType() {
        return this.dishType;
    }

    public void setDishType(String dishType) {
        this.dishType = dishType;
    }


}
