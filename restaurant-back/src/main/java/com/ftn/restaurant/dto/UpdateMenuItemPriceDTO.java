package com.ftn.restaurant.dto;

public class UpdateMenuItemPriceDTO {
    private long menuItemId;
    private double newPrice;
    private double newPurchasePrice;

    public UpdateMenuItemPriceDTO() {
    }

    public UpdateMenuItemPriceDTO(long menuItemId, double newPrice, double newPurchasePrice) {
        this.menuItemId = menuItemId;
        this.newPrice = newPrice;
        this.newPurchasePrice = newPurchasePrice;
    }

    public long getMenuItemId() {
        return menuItemId;
    }

    public void setMenuItemId(long menuItemId) {
        this.menuItemId = menuItemId;
    }

    public double getNewPrice() {
        return newPrice;
    }

    public void setNewPrice(double newPrice) {
        this.newPrice = newPrice;
    }

    public double getNewPurchasePrice() {
        return newPurchasePrice;
    }

    public void setNewPurchasePrice(double newPurchasePrice) {
        this.newPurchasePrice = newPurchasePrice;
    }
}
