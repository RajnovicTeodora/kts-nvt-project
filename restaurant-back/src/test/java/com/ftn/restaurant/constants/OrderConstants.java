package com.ftn.restaurant.constants;

import com.ftn.restaurant.dto.IngredientDTO;
import com.ftn.restaurant.dto.MenuItemDTO;
import com.ftn.restaurant.dto.OrderDTO;
import com.ftn.restaurant.dto.OrderItemDTO;
import com.ftn.restaurant.model.*;
import com.ftn.restaurant.model.enums.DishType;
import com.ftn.restaurant.model.enums.OrderedItemStatus;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;

public class OrderConstants {
    public static final Ingredient INGREDIENT_55 = new Ingredient("sladoled", true);
    public static final Ingredient INGREDIENT_66 = new Ingredient("plazma", false);
    public static final Ingredient INGREDIENT_77 = new Ingredient("mleko", false);
    public static final Ingredient INGREDIENT_88 = new Ingredient("cokolada", false);
    public static final ArrayList<Ingredient> INGREDIENT_ARRAY_LIST_3 = new ArrayList<Ingredient>(
            Arrays.asList(INGREDIENT_55, INGREDIENT_66));
    public static final ArrayList<Ingredient> INGREDIENT_ARRAY_LIST_4 = new ArrayList<Ingredient>(
            Arrays.asList(INGREDIENT_77, INGREDIENT_88));

    public static final MenuItem MENU_ITEM_1 = new Dish("Pizza","todo",true,true, new ArrayList<MenuItemPrice>(), INGREDIENT_ARRAY_LIST_3, DishType.MAIN_DISH);
    public static final MenuItem MENU_ITEM_2 = new Dish("Spaghetti","todo",true,true, new ArrayList<MenuItemPrice>(), INGREDIENT_ARRAY_LIST_4, DishType.MAIN_DISH);

    public static final OrderedItem ORDER_ITEM_1 = new OrderedItem(OrderedItemStatus.ORDERED, 1, 1, MENU_ITEM_1, INGREDIENT_ARRAY_LIST_3, false);
    public static final OrderedItem ORDER_ITEM_2 = new OrderedItem(OrderedItemStatus.ORDERED, 1,1 , MENU_ITEM_2, INGREDIENT_ARRAY_LIST_4, false);
    public static final ArrayList<OrderedItem> ORDER_ITEM_LIST = new ArrayList<OrderedItem>(
            Arrays.asList(ORDER_ITEM_1, ORDER_ITEM_2));

    public static final Order ORDER_1 = new Order(false, 0, LocalDate.now(), "Some note", LocalTime.now(), ORDER_ITEM_LIST);

    public static final Order ORDER_2  = new Order(false, 0, LocalDate.now(), "note", LocalTime.now(), new ArrayList<>());

}
