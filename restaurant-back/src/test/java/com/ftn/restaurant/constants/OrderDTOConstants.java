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
    public static final IngredientDTO INGREDIENT_11 = new IngredientDTO(2L, "sladoled", true);
    public static final IngredientDTO INGREDIENT_22 = new IngredientDTO(1L, "plazma", false);
    public static final IngredientDTO INGREDIENT_33 = new IngredientDTO(3L,"mleko", false);
    public static final IngredientDTO INGREDIENT_44 = new IngredientDTO(4L,"cokolada", false);
    public static final IngredientDTO INGREDIENT_55 = new IngredientDTO(10000L,"non existant", false);
    public static final ArrayList<IngredientDTO> INGREDIENT_ARRAY_LIST_1 = new ArrayList<IngredientDTO>(
            Arrays.asList(INGREDIENT_11, INGREDIENT_22));
    public static final ArrayList<IngredientDTO> INGREDIENT_ARRAY_LIST_2 = new ArrayList<IngredientDTO>(
            Arrays.asList(INGREDIENT_33, INGREDIENT_44));

    public static final MenuItemDTO MENU_ITEM_DTO_1 = new MenuItemDTO("Pizza","todo");
    public static final MenuItemDTO MENU_ITEM_DTO_2 = new MenuItemDTO("Spaghetti","todo");

    public static final OrderItemDTO ORDER_ITEM_DTO_1 = new OrderItemDTO(5L, OrderedItemStatus.ORDERED.toString(), 1, 1, 1L, INGREDIENT_ARRAY_LIST_1);
    public static final OrderItemDTO ORDER_ITEM_DTO_2 = new OrderItemDTO(2L, OrderedItemStatus.ORDERED.toString(), 1,1 , 2L, INGREDIENT_ARRAY_LIST_2);
    public static final OrderItemDTO ORDER_ITEM_DTO_3 = new OrderItemDTO(3L, "PENDING", 1,1 , 2L, INGREDIENT_ARRAY_LIST_2);
    public static final OrderItemDTO ORDER_ITEM_DTO_4 = new OrderItemDTO(8L, "PENDING", 1,1 , 2L, INGREDIENT_ARRAY_LIST_2);
    public static final OrderItemDTO ORDER_ITEM_DTO_5 = new OrderItemDTO(5L, "PENDING", 1,1 , 1L, new ArrayList<>(Arrays.asList(INGREDIENT_55)));
    public static final OrderItemDTO ORDER_ITEM_DTO_6 = new OrderItemDTO(-1L, OrderedItemStatus.ORDERED.toString(), 1,1 , 2L, INGREDIENT_ARRAY_LIST_2);
    public static final OrderItemDTO ORDER_ITEM_DTO_7 = new OrderItemDTO(-1L, OrderedItemStatus.ORDERED.toString(), 1,1 , 20000L, INGREDIENT_ARRAY_LIST_2);
    public static final OrderItemDTO ORDER_ITEM_DTO_8 = new OrderItemDTO(2000L, "PENDING", 1,1 , 1L, INGREDIENT_ARRAY_LIST_2);
    public static final ArrayList<OrderItemDTO> ORDER_ITEM_DTOS = new ArrayList<OrderItemDTO>(
            Arrays.asList(ORDER_ITEM_DTO_1, ORDER_ITEM_DTO_2));

    public static final OrderDTO ORDER_DTO_1 = new OrderDTO(false, 0, "Some note",  ORDER_ITEM_DTOS, 1L, "waiter");
    public static final OrderDTO ORDER_DTO_2  = new OrderDTO(false, 0,  "note",  new ArrayList<>());
    public static final OrderDTO ORDER_DTO_3  = new OrderDTO(3L,false, 0,  "note",  new ArrayList<>(Arrays.asList(ORDER_ITEM_DTO_7)), 2L, "waiter");
    public static final OrderDTO ORDER_DTO_4  = new OrderDTO(3L,false, 0,  "note",  new ArrayList<>(Arrays.asList(ORDER_ITEM_DTO_5)), 2L, "waiter");
    public static final OrderDTO ORDER_DTO_5  = new OrderDTO(4L,false, 0,  "note",  new ArrayList<>(Arrays.asList(ORDER_ITEM_DTO_4)));
    public static final OrderDTO ORDER_DTO_6 = new OrderDTO(2000L,false, 0, "Some note",  new ArrayList<>());
    public static final OrderDTO ORDER_DTO_7 = new OrderDTO(5L,false, 0, "Some note",  new ArrayList<>());
    public static final OrderDTO ORDER_DTO_8 = new OrderDTO(3L,false, 0, "Some note",  new ArrayList<>(Arrays.asList(ORDER_ITEM_DTO_8)));
    public static final OrderDTO ORDER_DTO_9 = new OrderDTO(3L,false, 0, "Some note",  new ArrayList<>(Arrays.asList(ORDER_ITEM_DTO_3)), 2L, "");
    public static final OrderDTO ORDER_DTO_10 = new OrderDTO(3L,false, 0, "Some note",  new ArrayList<>(Arrays.asList(ORDER_ITEM_DTO_1)));
    public static final OrderDTO ORDER_DTO_11 = new OrderDTO(3L,false, 0, "Some note",  new ArrayList<>());
}
