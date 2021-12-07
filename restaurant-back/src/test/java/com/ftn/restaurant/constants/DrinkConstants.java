package com.ftn.restaurant.constants;

import com.ftn.restaurant.model.Dish;
import com.ftn.restaurant.model.Drink;
import com.ftn.restaurant.model.Ingredient;
import com.ftn.restaurant.model.MenuItemPrice;
import com.ftn.restaurant.model.enums.ContainerType;
import com.ftn.restaurant.model.enums.DishType;
import com.ftn.restaurant.model.enums.DrinkType;

import java.util.ArrayList;
import java.util.Arrays;

public class DrinkConstants {
    public static final Ingredient INGREDIENT_1 = new Ingredient("banana", true);
    public static final Ingredient INGREDIENT_2 = new Ingredient("plazma", false);
    public static final ArrayList<Ingredient> INGREDIENTS_1 = new ArrayList<>(
            Arrays.asList(INGREDIENT_1, INGREDIENT_2));

    public static final Drink DRINK_1 = new Drink(
            "milk sejk",
            "a",
            true,
            true,
            new ArrayList<MenuItemPrice>(),
            DrinkType.COLD_DRINK,
            ContainerType.GLASS,
            INGREDIENTS_1);

    public static final String NEW_DRINK_NAME = "Hot dark chocolate";

}
