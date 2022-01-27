package com.ftn.restaurant.model;

import javax.persistence.*;

import com.ftn.restaurant.dto.RestaurantTableDTO;

import java.util.List;

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

    @Column(name = "tableNum", nullable=false)
    private int tableNum;

    @OneToMany(fetch = FetchType.LAZY,  cascade= CascadeType.ALL)
    private List<Order> orders;

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
    
    public RestaurantTable(int positionX, int positionY, Area area) {
		this.positionX = positionX;
		this.positionY = positionY;
		this.area = area;
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

    public int getTableNum() {
        return tableNum;
    }

    public void setTableNum(int tableNum) {
        this.tableNum = tableNum;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
