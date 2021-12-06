package com.ftn.restaurant.dto;

import com.ftn.restaurant.model.Ingredient;

import javax.persistence.Column;

public class IngredientDTO {

    private Long id;
    private String name;
    private boolean isAlergen;

    public IngredientDTO() {
    }

    public IngredientDTO(Ingredient i){
        this.id = i.getId();
        this.name= i.getName();
        this.isAlergen = i.isAlergen();
    }

    public IngredientDTO(String name, boolean b) {
        this.name = name;
        this.isAlergen = b;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAlergen() {
        return isAlergen;
    }

    public void setAlergen(boolean alergen) {
        isAlergen = alergen;
    }
}
