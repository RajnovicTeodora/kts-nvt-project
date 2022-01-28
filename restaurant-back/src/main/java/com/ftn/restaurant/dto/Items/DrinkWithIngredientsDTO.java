package com.ftn.restaurant.dto.Items;

import com.ftn.restaurant.dto.DrinkDTO;
import com.ftn.restaurant.dto.IngredientDTO;
import com.ftn.restaurant.model.Drink;
import com.ftn.restaurant.model.Ingredient;

import java.util.ArrayList;
import java.util.List;

public class DrinkWithIngredientsDTO extends DrinkDTO {

    private List<IngredientDTO> ingredients = new ArrayList<>();

    public DrinkWithIngredientsDTO(Drink drink) {
        super(drink);
        for (Ingredient ingredient : drink.getIngredients()) {
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
