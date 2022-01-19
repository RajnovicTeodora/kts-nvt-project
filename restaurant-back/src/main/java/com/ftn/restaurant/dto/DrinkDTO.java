package com.ftn.restaurant.dto;

import com.ftn.restaurant.model.Drink;
import com.ftn.restaurant.model.MenuItemPrice;

public class DrinkDTO extends MenuItemDTO {

    private String drinkType;
    private String containerType;
    private double price;

    public DrinkDTO(Drink drink) {
        super(drink);
        this.drinkType = drink.getDrinkType().toString();
        this.containerType = drink.getContainerType().toString();
    }
    public DrinkDTO(Drink drink, String withprice) {
        super(drink);
        this.drinkType = drink.getDrinkType().toString();
        this.containerType = drink.getContainerType().toString();
//        if(drink.getPriceList() == null){
//            this.currentlyPrice = 0;
//        }else {
//            for (MenuItemPrice price : drink.getPriceList()) {
//                if (price.getDateTo() == null) {
//                    this.currentlyPrice = price.getPrice();
//                    break;
//                }
//            }
//        }
        this.price =100;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public DrinkDTO() {super();}

    public String getDrinkType() {
        return drinkType;
    }

    public void setDrinkType(String drinkType) {
        this.drinkType = drinkType;
    }

    public String getContainerType() {
        return containerType;
    }

    public void setContainerType(String container) {
        this.containerType = container;
    }
}
