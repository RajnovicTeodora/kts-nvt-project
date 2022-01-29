package com.ftn.restaurant.constants;

import com.ftn.restaurant.model.Bartender;
import com.ftn.restaurant.model.Chef;
import com.ftn.restaurant.model.User;

import java.util.ArrayList;

public class UserConstants {

    public static User BARTENDER_1 = new Bartender("Pera", "Pera", false, new ArrayList<>());
    public static User CHEF_1 = new Chef("Sima", "Pera", false, new ArrayList<>());
}
