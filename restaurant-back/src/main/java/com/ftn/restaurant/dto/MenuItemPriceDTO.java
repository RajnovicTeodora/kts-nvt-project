package com.ftn.restaurant.dto;

import com.ftn.restaurant.model.MenuItemPrice;

//DTO for manager item and active price preview
public class MenuItemPriceDTO {
    private MenuItemDTO menuItem;
    private double purchasePrice;
    private double price;
    private boolean active;
    private boolean approved;

    public MenuItemPriceDTO() {
    }

    public MenuItemPriceDTO(MenuItemPrice menuItemPrice) {
        this.menuItem = new MenuItemDTO(menuItemPrice.getItem());
        this.purchasePrice = menuItemPrice.getPurchasePrice();
        this.price = menuItemPrice.getPrice();
        this.active = menuItemPrice.isActive();
        this.approved = menuItemPrice.getItem().isApproved();
    }

    public MenuItemDTO getMenuItem() {
        return menuItem;
    }

    public void setMenuItem(MenuItemDTO menuItem) {
        this.menuItem = menuItem;
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }
}
