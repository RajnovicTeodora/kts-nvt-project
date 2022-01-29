package com.ftn.restaurant.model;

import javax.persistence.*;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "restaurant_order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", unique=true, nullable=false)
    private Long id;

    @Column(name = "is_paid", nullable=false)
    private boolean isPaid;

    @Column(name = "total_price", nullable=false)
    private double totalPrice;

    @Column(name = "date", nullable=false)
    private LocalDate date;

    @Column(name = "note", nullable=false)
    private String note;

    @Column(name = "time", nullable=false)
    private LocalTime time;

    @OneToMany(fetch = FetchType.LAZY,  cascade= CascadeType.ALL)
    private List<OrderedItem> orderedItems;

    @ManyToOne(fetch=FetchType.EAGER, optional = true)
    private Waiter waiter;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "restaurant_table_id")
    private RestaurantTable restaurantTable;

    @Column(name="order_number", nullable=false)
    private int orderNumber;

    public Order() {
    }

    public Order(boolean isPaid, double totalPrice, LocalDate date, String note, LocalTime time, List<OrderedItem> orderedItems, Waiter waiter) {
        this.isPaid = isPaid;
        this.totalPrice = totalPrice;
        this.date = date;
        this.note = note;
        this.time = time;
        this.orderedItems = orderedItems;
        this.waiter = waiter;
    }

    public Order(boolean isPaid, double totalPrice, LocalDate date, String note, LocalTime time, List<OrderedItem> orderedItems) {
        this.isPaid = isPaid;
        this.totalPrice = totalPrice;
        this.date = date;
        this.note = note;
        this.time = time;
        this.orderedItems = orderedItems;
    }
    public Order(boolean isPaid, double totalPrice, LocalDate date, String note, LocalTime time, List<OrderedItem> orderedItems, RestaurantTable table) {
        this.isPaid = isPaid;
        this.totalPrice = totalPrice;
        this.date = date;
        this.note = note;
        this.time = time;
        this.orderedItems = orderedItems;
        this.restaurantTable = table;
    }

    public Order(boolean isPaid, double totalPrice, LocalDate date, String note, LocalTime time, List<OrderedItem> orderedItems, int orderNumber) {
        this.isPaid = isPaid;
        this.totalPrice = totalPrice;
        this.date = date;
        this.note = note;
        this.time = time;
        this.orderedItems = orderedItems;
        this.orderNumber = orderNumber;
    }

    public void addOrderedItem(OrderedItem orderItem) {
        if (orderItem == null)
            return;
        if (this.orderedItems == null)
            this.orderedItems = new ArrayList<>();
        if (!this.orderedItems.contains(orderItem)) {
            this.orderedItems.add(orderItem);
        }
    }
    
    public List<OrderedItem> getOrderedItems() {
        return orderedItems;
    }

    public Waiter getWaiter() {
        return waiter;
    }

    public void setWaiter(Waiter waiter) {
        this.waiter = waiter;
    }

    public void setOrderedItems(List<OrderedItem> orderedItems) {
        this.orderedItems = orderedItems;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public RestaurantTable getRestaurantTable() {
        return restaurantTable;
    }

    public void setRestaurantTable(RestaurantTable restaurantTable) {
        this.restaurantTable = restaurantTable;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }
}
