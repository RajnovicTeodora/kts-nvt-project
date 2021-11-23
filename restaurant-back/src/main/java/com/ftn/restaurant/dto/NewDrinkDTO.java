package com.ftn.restaurant.dto;

import com.ftn.restaurant.model.enums.ContainerType;
import com.ftn.restaurant.model.enums.DrinkType;

import java.util.ArrayList;

public class NewDrinkDTO {
    private String name;
    private String image;
    private DrinkType type;
    private ContainerType containerType;
    private ArrayList<IngredientDTO> ingredients;

    public NewDrinkDTO() {
    }

    public NewDrinkDTO(String name, String image, DrinkType type, ContainerType containerType) {
        this.name = name;
        this.image = image;
        this.type = type;
        this.containerType = containerType;
    }

    public NewDrinkDTO(String name, String image, ArrayList<IngredientDTO> ingredients) {
        this.name = name;
        this.image = image;
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

    public DrinkType getType() {
        return type;
    }

    public void setType(DrinkType type) {
        this.type = type;
    }

    public ContainerType getContainerType() {
        return containerType;
    }

    public void setContainerType(ContainerType containerType) {
        this.containerType = containerType;
    }

    public ArrayList<IngredientDTO> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<IngredientDTO> ingredients) {
        this.ingredients = ingredients;
    }
}
