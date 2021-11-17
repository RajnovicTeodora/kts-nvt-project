package com.ftn.restaurant.dto;

import com.ftn.restaurant.model.enums.ContainerType;
import com.ftn.restaurant.model.enums.DishType;

public class NewDishDTO extends MenuItemDTO{

    private String name;
    private String image;
    private DishType type;

    public NewDishDTO() {
    }

    public NewDishDTO(String name, String image, DishType type) {
        this.name = name;
        this.image = image;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public DishType getType() {
        return type;
    }

    public void setType(DishType type) {
        this.type = type;
    }

}
