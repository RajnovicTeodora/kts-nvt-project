package com.ftn.restaurant.constants;

import com.ftn.restaurant.dto.IngredientDTO;
import com.ftn.restaurant.dto.NewDishDTO;
import com.ftn.restaurant.model.Dish;
import com.ftn.restaurant.model.Ingredient;
import com.ftn.restaurant.model.MenuItemPrice;
import com.ftn.restaurant.model.enums.DishType;

import java.util.ArrayList;
import java.util.Arrays;

public class DishConstants {
    public static final Ingredient INGREDIENT_1 = new Ingredient("sladoled", true);
    public static final Ingredient INGREDIENT_2 = new Ingredient("plazma", false);
    public static final ArrayList<Ingredient> INGREDIENTS_1 = new ArrayList<>(
            Arrays.asList(INGREDIENT_1, INGREDIENT_2));

    public static final Dish DISH_1 = new Dish(
            "Pohovani sladoled",
            "a",
            true,
            true,
            new ArrayList<MenuItemPrice>(),
            INGREDIENTS_1,
            DishType.DESERT);
    public static final Dish DISH_2 = new Dish(
            "Sladoled",
            "a",
            true,
            false,
            new ArrayList<MenuItemPrice>(),
            INGREDIENTS_1,
            DishType.ENTREE);
    public static final ArrayList<Dish> LIST_DISHES = new ArrayList<>(
            Arrays.asList(DISH_1, DISH_2));
}
