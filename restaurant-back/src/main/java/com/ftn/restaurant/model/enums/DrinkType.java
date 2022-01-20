package com.ftn.restaurant.model.enums;

import java.util.ArrayList;
import java.util.List;

public enum DrinkType {
    COFFEE, COLD_DRINK, HOT_DRINK, ALCOHOLIC;

    public static int isValid(String name) {
		for (DrinkType dt : DrinkType.values()) {
			if(dt.name().equalsIgnoreCase(name)) return dt.ordinal();
		}
		return -1;
	}

	public static List<String> getNames(){
		List<String> names = new ArrayList<String>();
		for (DrinkType type : DrinkType.values()){
			names.add(type.name());
		}
		return names;
	}
}
