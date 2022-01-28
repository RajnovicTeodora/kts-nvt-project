package com.ftn.restaurant.dto;

import com.ftn.restaurant.model.MenuItemPrice;

public class CurrentMenuItemPriceDTO extends MenuItemPriceDTO{

    private double currentPrice;
    private double currentPurchasePrice;

    public CurrentMenuItemPriceDTO(){}

    public CurrentMenuItemPriceDTO(double currentPrice, double currentPurchasePrice) {
        this.currentPrice = currentPrice;
        this.currentPurchasePrice = currentPurchasePrice;
    }

    public CurrentMenuItemPriceDTO(MenuItemPrice menuItemPrice, double currentPrice, double currentPurchasePrice) {
        super(menuItemPrice);
        this.currentPrice = currentPrice;
        this.currentPurchasePrice = currentPurchasePrice;
    }

    public CurrentMenuItemPriceDTO(MenuItemDTO menuItem, double purchasePrice, double price, boolean active, boolean approved, double currentPrice, double currentPurchasePrice) {
        super(menuItem, purchasePrice, price, active, approved);
        this.currentPrice = currentPrice;
        this.currentPurchasePrice = currentPurchasePrice;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public double getCurrentPurchasePrice() {
        return currentPurchasePrice;
    }

    public void setCurrentPurchasePrice(double currentPurchasePrice) {
        this.currentPurchasePrice = currentPurchasePrice;
    }
}
