package com.ftn.restaurant.model;

import com.ftn.restaurant.dto.IngredientDTO;

import javax.persistence.*;
import javax.persistence.Table;


@Entity
@Table(name = "ingredient")
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", unique=true, nullable=false)
    private Long id;

    @Column(name = "name", nullable=false)
    private String name;

    @Column(name = "alergen", nullable=false)
    private boolean isAlergen;

    public Ingredient(IngredientDTO ingredient) {
        this.name = ingredient.getName();
        this.isAlergen = ingredient.isAlergen();
    }

    public Ingredient() {
    }

    public Ingredient(String name, boolean b) {
        this.name = name;
        this.isAlergen = b;
    }

    public Long getId() {
        return id;
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
