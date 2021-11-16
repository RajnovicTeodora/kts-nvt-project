package com.ftn.restaurant.model.enums;

public enum DishType {
    DESERT, ENTREE, MAIN_DISH, SALAD, SOUP;

    public static int isValid(String name) {
		for (DishType dt : DishType.values()) {
			if(dt.name().equalsIgnoreCase(name)) return dt.ordinal();
		}
		return -1;
	}
}
