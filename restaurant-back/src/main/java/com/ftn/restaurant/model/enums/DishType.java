package com.ftn.restaurant.model.enums;

import java.util.ArrayList;
import java.util.List;

public enum DishType {
    DESERT, ENTREE, MAIN_DISH, SALAD, SOUP;

    public static int isValid(String name) {
		for (DishType dt : DishType.values()) {
			if(dt.name().equalsIgnoreCase(name)) return dt.ordinal();
		}
		return -1;
	}

	public static List<String> getNames(){
		List<String> names = new ArrayList<String>();
		for (DishType type : DishType.values()){
			names.add(type.name());
		}
		return names;
	}
}
