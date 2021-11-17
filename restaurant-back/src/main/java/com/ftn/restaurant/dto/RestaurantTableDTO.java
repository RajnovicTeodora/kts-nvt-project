package com.ftn.restaurant.dto;

import com.ftn.restaurant.model.RestaurantTable;

public class RestaurantTableDTO {

    private long id;
    private int x;
    private int y;


    public RestaurantTableDTO() {
    }

    public RestaurantTableDTO(long id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }

    public RestaurantTableDTO(RestaurantTable table){
        this.id = table.getId();
        this.x = table.getPositionX();
        this.y = table.getPositionY();
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
    }

    
}
