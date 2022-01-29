package com.ftn.restaurant.constants;

import com.ftn.restaurant.model.*;
import com.ftn.restaurant.model.enums.DishType;
import com.ftn.restaurant.model.enums.OrderedItemStatus;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class OrderedItemConstants {

    //TODO REPORT CONSTANTS
    public static LocalDate DB_DATE_FROM = LocalDate.of(2021, 10, 20);
    public static LocalDate DB_DATE_TO = LocalDate.now().minusDays(2);
    public static int SUM_QUANTITY = 10;
    public static double SUM_PREPARATION_COSTS = 100;

    //for accepting item constants
    public static RestaurantTable TABLE = new RestaurantTable(1,2,new Area(),4);
    public static Order ORDER_1 = new Order(false, 100, LocalDate.now(), "note1", LocalTime.now(), new ArrayList<>(), TABLE);
    public static MenuItem MENU_ITEM_1 = new Dish("pizza", "img", true, false, new ArrayList<MenuItemPrice>(), DishType.MAIN_DISH);
    public static OrderedItem ITEM_1 =  new OrderedItem(OrderedItemStatus.ORDERED, 1, 2, MENU_ITEM_1,
            new ArrayList<Ingredient>(), false, MENU_ITEM_1, ORDER_1);

    public static OrderedItem ITEM_2 =  new OrderedItem(OrderedItemStatus.READY, 1, 2, MENU_ITEM_1,
            new ArrayList<Ingredient>(), false, MENU_ITEM_1, ORDER_1);

    public static OrderedItem ITEM_3 =  new OrderedItem(OrderedItemStatus.IN_PROGRESS, 1, 2, MENU_ITEM_1,
            new ArrayList<Ingredient>(), false, MENU_ITEM_1, ORDER_1);
    public static OrderedItem ITEM_4 =  new OrderedItem(OrderedItemStatus.ORDERED, 1, 2, MENU_ITEM_1,
            new ArrayList<Ingredient>(), false, MENU_ITEM_1, ORDER_1);

}
