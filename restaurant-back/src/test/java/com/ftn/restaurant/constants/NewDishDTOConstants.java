package com.ftn.restaurant.constants;

import com.ftn.restaurant.dto.IngredientDTO;
import com.ftn.restaurant.dto.NewDishDTO;
import com.ftn.restaurant.model.enums.DishType;

import java.util.ArrayList;
import java.util.Arrays;

public class NewDishDTOConstants {
    public static final IngredientDTO INGREDIENT_DTO_1 = new IngredientDTO("mayonnaise", true);
    public static final IngredientDTO INGREDIENT_DTO_2 = new IngredientDTO("carrot", false);
    public static final ArrayList<IngredientDTO> INGREDIENT_DTOS = new ArrayList<IngredientDTO>(
            Arrays.asList(INGREDIENT_DTO_1, INGREDIENT_DTO_2));

    public static final NewDishDTO NEW_DISH_DTO_1 = new NewDishDTO(
            "Russian salad",
            "a",
            DishType.SALAD,
            INGREDIENT_DTOS);
    public static final NewDishDTO NEW_DISH_DTO_2 = new NewDishDTO(
            "",
            "",
            DishType.SALAD,
            INGREDIENT_DTOS);
    public static final NewDishDTO NEW_DISH_DTO_3 = null;
}
