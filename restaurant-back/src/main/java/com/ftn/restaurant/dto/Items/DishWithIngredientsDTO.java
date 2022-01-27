package com.ftn.restaurant.dto.Items;

import com.ftn.restaurant.dto.DishDTO;
import com.ftn.restaurant.dto.IngredientDTO;
import com.ftn.restaurant.model.Dish;
import com.ftn.restaurant.model.Drink;
import com.ftn.restaurant.model.Ingredient;

import java.util.ArrayList;
import java.util.List;

public class DishWithIngredientsDTO extends DishDTO {

    private List<IngredientDTO> ingredients = new ArrayList<>();

    public DishWithIngredientsDTO(Dish dish) {
        super(dish);

        for (Ingredient ingredient : dish.getIngredients()) {
            this.ingredients.add(new IngredientDTO(ingredient));
        }
    }

    public List<IngredientDTO> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<IngredientDTO> ingredients) {
        this.ingredients = ingredients;
    }
}
