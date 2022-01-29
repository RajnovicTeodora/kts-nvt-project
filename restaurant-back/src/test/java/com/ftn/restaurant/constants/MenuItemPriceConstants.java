package com.ftn.restaurant.constants;

import com.ftn.restaurant.model.enums.DishType;

import java.time.LocalDate;

public class MenuItemPriceConstants {

    //TODO change to menu item without a price
    public static final long DB_MENU_ITEM_ID = 2;
    public static final long DB_MENU_ITEM_WITHOUT_PRICE = 5;

    //TODO menu item with active price ( date: now, null ), and potentially not approved
    public static final long DB_MENU_ITEM_ID1 = 1;
    public static final LocalDate DB_MENU_ITEM_DATE1 = LocalDate.of(2021, 8, 1);

    //TODO menu item that is approved and has an active price
    public static final long DB_MENU_ITEM_ID2 = 5;

    public static final long UPDATE_ID = 1;
    public static final double OLD_PRICE = 20;
    public static final double OLD_PURCHASE_PRICE = 7;
    public static final double UPDATE_PRICE = 10;
    public static final double UPDATE_PURCHASE_PRICE = 5;

    public static final long NEW_DISH_ID = 15;
    public static final String NEW_DISH_NAME = "Fish and chips";
    public static final DishType NEW_DISH_TYPE = DishType.MAIN_DISH;

    public static final long NEW_DISH_ID1 = 16;
    public static final String NEW_DISH_NAME1 = "French fries";
    public static final DishType NEW_DISH_TYPE1 = DishType.ENTREE;

    public static final long NEW_DISH_ID2 = 17;
    public static final String NEW_DISH_NAME2 = "Eggs on toast";
    public static final DishType NEW_DISH_TYPE2 = DishType.ENTREE;

    public static final long NON_EXISTENT_MENU_ITEM_ID = 22;
}
