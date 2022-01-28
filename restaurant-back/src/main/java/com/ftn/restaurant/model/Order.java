package com.ftn.restaurant.model;

import javax.persistence.*;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalTime;
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

    public List<OrderedItem> getOrderedItems() {
        return orderedItems;
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
}
