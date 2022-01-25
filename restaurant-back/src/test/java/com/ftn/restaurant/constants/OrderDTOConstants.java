package com.ftn.restaurant.constants;

import com.ftn.restaurant.dto.IngredientDTO;
import com.ftn.restaurant.dto.MenuItemDTO;
import com.ftn.restaurant.dto.OrderDTO;
import com.ftn.restaurant.dto.OrderItemDTO;
import com.ftn.restaurant.model.Ingredient;
import com.ftn.restaurant.model.enums.OrderedItemStatus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OrderDTOConstants {
    public static final IngredientDTO INGREDIENT_11 = new IngredientDTO("sladoled", true);
    public static final IngredientDTO INGREDIENT_22 = new IngredientDTO("plazma", false);
    public static final IngredientDTO INGREDIENT_33 = new IngredientDTO("mleko", false);
    public static final IngredientDTO INGREDIENT_44 = new IngredientDTO("cokolada", false);
    public static final ArrayList<IngredientDTO> INGREDIENT_ARRAY_LIST_1 = new ArrayList<IngredientDTO>(
            Arrays.asList(INGREDIENT_11, INGREDIENT_22));
    public static final ArrayList<IngredientDTO> INGREDIENT_ARRAY_LIST_2 = new ArrayList<IngredientDTO>(
            Arrays.asList(INGREDIENT_33, INGREDIENT_44));

    public static final MenuItemDTO MENU_ITEM_DTO_1 = new MenuItemDTO("Pizza","todo");
    public static final MenuItemDTO MENU_ITEM_DTO_2 = new MenuItemDTO("Spaghetti","todo");

    public static final OrderItemDTO ORDER_ITEM_DTO_1 = new OrderItemDTO(OrderedItemStatus.ORDERED.toString(), 1, 1, 1L, INGREDIENT_ARRAY_LIST_1);
    public static final OrderItemDTO ORDER_ITEM_DTO_2 = new OrderItemDTO(OrderedItemStatus.ORDERED.toString(), 1,1 , 2L, INGREDIENT_ARRAY_LIST_2);
    public static final ArrayList<OrderItemDTO> ORDER_ITEM_DTOS = new ArrayList<OrderItemDTO>(
            Arrays.asList(ORDER_ITEM_DTO_1, ORDER_ITEM_DTO_2));

    public static final OrderDTO ORDER_DTO_1 = new OrderDTO(false, 0, "Some note",  ORDER_ITEM_DTOS);

    public static final OrderDTO ORDER_DTO_2  = new OrderDTO(false, 0,  "note",  new ArrayList<>());
}
