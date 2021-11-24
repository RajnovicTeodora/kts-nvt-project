package com.ftn.restaurant.dto;

import com.ftn.restaurant.model.RestaurantTable;

public class RestaurantTableDTO {

    private long id;
    private int x;
    private int y;
    private long areaId;


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
        this.areaId = table.getArea().getId();
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

    public long getAreaId() {
        return this.areaId;
    }

    public void setAreaId(long areaId) {
        this.areaId = areaId;
    }
    
}
