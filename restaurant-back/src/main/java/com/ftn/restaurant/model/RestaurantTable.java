package com.ftn.restaurant.model;

import javax.persistence.*;

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
    @Column( nullable=false)
    private Waiter waiter;

    public Long getId() {
        return id;
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
}
