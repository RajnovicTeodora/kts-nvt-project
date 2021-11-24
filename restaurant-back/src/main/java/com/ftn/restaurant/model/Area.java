package com.ftn.restaurant.model;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "area")
public class Area {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", unique=true, nullable=false)
    private Long id;

    @Column(name = "name", nullable=false)
    private String name;

    @OneToMany(fetch = FetchType.LAZY, cascade= CascadeType.ALL)
    private List<RestaurantTable> tables;


    public Area() {
    }

    public Area(String name) {
        this.name = name;
        this.tables = new ArrayList<RestaurantTable>();
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<RestaurantTable> getTables() {
        return tables;
    }

    public void setTables(List<RestaurantTable> tables) {
        this.tables = tables;
    }

    public void addTable(RestaurantTable table){
        this.tables.add(table);
    }
}
