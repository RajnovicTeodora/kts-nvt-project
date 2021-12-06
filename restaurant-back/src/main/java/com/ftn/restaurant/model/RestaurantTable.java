package com.ftn.restaurant.model;

import javax.persistence.*;

import com.ftn.restaurant.dto.RestaurantTableDTO;

@Entity
@Table(name = "restaurant_table")
public class RestaurantTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", unique=true, nullable=false)
    private Long id;

    @Column(name = "positionX", nullable=false)
    private int positionX;

    @Column(name = "positionY", nullable=false)
    private int positionY;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "waiter_id")
    private Waiter waiter;

    @Column(name = "occupied", nullable=false)
    private boolean occupied;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "area_id", nullable = false)
    private Area area;

    public Long getId() {
        return id;
    }


    public RestaurantTable() {
    }


    public RestaurantTable(RestaurantTableDTO table){
        this.positionX = table.getX();
        this.positionY = table.getY();
        this.occupied = false;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getPositionX() {
        return positionX;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    public Waiter getWaiter() {
        return waiter;
    }

    public void setWaiter(Waiter waiter) {
        this.waiter = waiter;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    public Area getArea() {
        return this.area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

}
