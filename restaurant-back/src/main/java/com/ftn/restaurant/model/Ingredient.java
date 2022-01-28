package com.ftn.restaurant.model;

import com.ftn.restaurant.dto.IngredientDTO;

import javax.persistence.*;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;


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

    @ManyToMany(mappedBy = "activeIngredients")
    private List<OrderedItem> orderedItems;

    @ManyToMany(mappedBy = "menuItemIngredients")
    private List<MenuItem> ingredients;

    public Ingredient(IngredientDTO ingredient) {
        this.name = ingredient.getName();
        this.isAlergen = ingredient.isAlergen();
        this.orderedItems = new ArrayList<>();
    }

    public Ingredient() {
    }

    public Ingredient(String name, boolean b) {
        this.name = name;
        this.isAlergen = b;
        this.orderedItems = new ArrayList<>();
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

    public void setId(Long id){ this.id = id;}
}
