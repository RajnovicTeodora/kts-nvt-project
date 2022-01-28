package com.ftn.restaurant.constants;

import com.ftn.restaurant.dto.IngredientDTO;
import com.ftn.restaurant.dto.NewDrinkDTO;
import com.ftn.restaurant.model.enums.ContainerType;
import com.ftn.restaurant.model.enums.DrinkType;

import java.util.ArrayList;
import java.util.Arrays;

public class NewDrinkDTOConstants {
    public static final IngredientDTO INGREDIENT_DTO_1 = new IngredientDTO("vodka", false);
    public static final IngredientDTO INGREDIENT_DTO_2 = new IngredientDTO("salt", false);
    public static final ArrayList<IngredientDTO> INGREDIENT_DTOS = new ArrayList<>(
            Arrays.asList(INGREDIENT_DTO_1, INGREDIENT_DTO_2));

    public static final NewDrinkDTO NEW_DRINK_DTO_1 = new NewDrinkDTO(
            "Bloody Mary",
            "a",
            DrinkType.ALCOHOLIC,
            ContainerType.GLASS,
            INGREDIENT_DTOS);
    public static final NewDrinkDTO NEW_DRINK_DTO_2 = new NewDrinkDTO(
            "",
            "",
            DrinkType.ALCOHOLIC,
            ContainerType.GLASS,
            INGREDIENT_DTOS);
    public static final NewDrinkDTO NEW_DRINK_DTO_3 = null;
}
