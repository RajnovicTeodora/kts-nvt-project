package com.ftn.restaurant.dto;

import com.ftn.restaurant.model.MenuItem;

public class MenuItemDTO {

    private String name;
    private String image;

    public MenuItemDTO() {
    }

    public MenuItemDTO(MenuItem menuItem) {
        this.name = menuItem.getName();
        this.image = menuItem.getImage();
    }

    public MenuItemDTO(String name, String image) {
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
