package com.ftn.restaurant.dto;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MenuItemWithIngredientsDTO {
    private long id;
    private String name;
    private double price;
    public String type;
    public int priority;
    public String container;
    public List<IngredientDTO> ingredients;

    public MenuItemWithIngredientsDTO(){
        this.ingredients = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getContainer() {
        return container;
    }

    public void setContainer(String container) {
        this.container = container;
    }

    public List<IngredientDTO> getIngredientDTOArray() {
        return ingredients;
    }

    public void setIngredientDTOArray(List<IngredientDTO> ingredientDTOArray) {
        this.ingredients = ingredientDTOArray;
    }
}
