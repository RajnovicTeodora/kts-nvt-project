package com.ftn.restaurant.dto;

import com.ftn.restaurant.model.Drink;

public class DrinkDTO extends MenuItemDTO {

    private String drinkType;
    private String container;

    public DrinkDTO(Drink drink) {
        super(drink);
        this.drinkType = drink.getDrinkType().toString();
        this.container = drink.getContainerType().toString();
    }

    public DrinkDTO() {super();}

    public String getDrinkType() {
        return drinkType;
    }

    public void setDrinkType(String drinkType) {
        this.drinkType = drinkType;
    }

    public String getContainer() {
        return container;
    }

    public void setContainer(String container) {
        this.container = container;
    }
}
