package com.ftn.restaurant.dto;

import com.ftn.restaurant.model.enums.ContainerType;
import com.ftn.restaurant.model.enums.DishType;

import java.util.ArrayList;

public class NewDishDTO {

    private String name;
    private String image;
    private DishType type;
    private ArrayList<IngredientDTO> ingredients;

    public NewDishDTO() {
    }

    public NewDishDTO(String name, String image, DishType type) {
        this.name = name;
        this.image = image;
        this.type = type;
    }
    public NewDishDTO(String name, String image, DishType type, ArrayList<IngredientDTO> ingredients) {
        this.name = name;
        this.image = image;
        this.type = type;
        this.ingredients = ingredients;
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

    public ArrayList<IngredientDTO> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<IngredientDTO> ingredients) {
        this.ingredients = ingredients;
    }

}
