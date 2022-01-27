package com.ftn.restaurant.dto;

import com.ftn.restaurant.model.RestaurantTable;

public class RestaurantTableDTO {

    private long id;
    private int tableNum;
    private int x;
    private int y;
    private long areaId;
    private String waiterUsername;
    private boolean occupied;

    public RestaurantTableDTO() {
    }


    public RestaurantTableDTO(int tableNum, int x, int y) {
        this.tableNum = tableNum;
        this.x = x;
        this.y = y;
    }

    public RestaurantTableDTO(RestaurantTable table){
        this.id = table.getId();
        this.tableNum = table.getTableNum();
        this.x = table.getPositionX();
        this.y = table.getPositionY();
        this.areaId = table.getArea().getId();
    }


    public RestaurantTableDTO(int x, int y, long areaId) {
        this.x = x;
        this.y = y;
        this.areaId = areaId;
    }


    public int getTableNum() {
        return this.tableNum;
    }

    public void setTableNum(int tableNum) {
        this.tableNum = tableNum;
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

    public String getWaiterUsername(){ return this.waiterUsername;}

    public void setWaiterUsername(String username){ this.waiterUsername = username;}

    public void setOccupied(boolean occupied){ this.occupied = occupied;}

    public boolean isOccupied(){ return  this.occupied;}

    public long getId() { return this.id; }

    public void setId(long id ) {  this.id = id;  }
    
}
