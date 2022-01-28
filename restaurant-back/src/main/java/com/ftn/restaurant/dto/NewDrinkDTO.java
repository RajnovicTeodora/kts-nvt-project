package com.ftn.restaurant.dto;

import com.ftn.restaurant.model.enums.ContainerType;
import com.ftn.restaurant.model.enums.DrinkType;

import java.util.ArrayList;

public class NewDrinkDTO {
    private String name;
    private String image;
    private DrinkType drinkType;
    private ContainerType containerType;
    private ArrayList<IngredientDTO> ingredients;

    public NewDrinkDTO() {
    }

    public NewDrinkDTO(String name, String image, DrinkType type, ContainerType containerType) {
        this.name = name;
        this.image = image;
        this.drinkType = type;
        this.containerType = containerType;
    }

    public NewDrinkDTO(String name, String image, DrinkType type, ContainerType containerType, ArrayList<IngredientDTO> ingredients) {
        this.name = name;
        this.image = image;
        this.ingredients = ingredients;
        this.drinkType = type;
        this.containerType = containerType;
    }

    public NewDrinkDTO(String name, String image, String type, String containerType, String price) {
        this.name = name;
        this.image = image;
        if(type.equals("alcoholic")){
            this.drinkType = DrinkType.ALCOHOLIC;
        }else if(type.equals("coffee")){
            this.drinkType = DrinkType.COFFEE;
        }else if(type.equals("cold drink")){
            this.drinkType = DrinkType.COLD_DRINK;
        }else{
            this.drinkType = DrinkType.HOT_DRINK;
        }
        if(containerType.equals("glass")){
            this.containerType = ContainerType.GLASS;
        }else if(type.equals("pitcher")){
            this.containerType = ContainerType.PITCHER;
        }else{
            this.containerType = ContainerType.BOTTLE;
        }

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

    public DrinkType getDrinkType() {
        return drinkType;
    }

    public void setDrinkType(DrinkType type) {
        this.drinkType = type;
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
