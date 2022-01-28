package com.ftn.restaurant.model;

import com.ftn.restaurant.model.enums.DishType;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
@DiscriminatorValue("dish")
public class Dish extends MenuItem{

    @Column(name = "dish_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private DishType dishType; //fale ingrediants

    public Dish(String name, String image, boolean b, boolean b1, ArrayList<MenuItemPrice> menuItemPrices, DishType type) {
        super(name, image, b, b1, menuItemPrices);
        this.dishType = type;
    }

    public Dish(){}

    public Dish(String name, String image, boolean approved, boolean deleted, ArrayList<MenuItemPrice> menuItemPrices, ArrayList<Ingredient> ingredients, DishType type) {
        super(name, image, approved, deleted, menuItemPrices, ingredients);
        this.dishType = type;
    }

    public DishType getDishType() {
        return dishType;
    }

    public void setDishType(DishType dishType) {
        this.dishType = dishType;
    }
}
