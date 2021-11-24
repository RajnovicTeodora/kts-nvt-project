package com.ftn.restaurant.model.enums;

public enum DrinkType {
    COFFEE, COLD_DRINK, HOT_DRINK, ALCOHOLIC;

    public static int isValid(String name) {
		for (DrinkType dt : DrinkType.values()) {
			if(dt.name().equalsIgnoreCase(name)) return dt.ordinal();
		}
		return -1;
	}
}
