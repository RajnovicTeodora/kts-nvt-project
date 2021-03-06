package com.ftn.restaurant.dto;

import java.util.ArrayList;
import java.util.List;

import com.ftn.restaurant.model.Area;
import com.ftn.restaurant.model.RestaurantTable;

public class AreaDTO {
    
    private Long id;
    private String name;
    private List<RestaurantTableDTO> tables;


    public AreaDTO() {
        this.tables = new ArrayList<RestaurantTableDTO>();
    }

    public AreaDTO(Long id, String name, List<RestaurantTableDTO> tables) {
        this.id = id;
        this.name = name;
        this.tables = tables;
    }

    public AreaDTO(Area area){
        this.id = area.getId();
        this.name = area.getName();
        this.tables = new ArrayList<RestaurantTableDTO>();
        for (RestaurantTable table : area.getTables()) {
            this.tables.add(new RestaurantTableDTO(table));
        }
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<RestaurantTableDTO> getTables() {
        return this.tables;
    }

    public void setTables(List<RestaurantTableDTO> tables) {
        this.tables = tables;
    }

}
