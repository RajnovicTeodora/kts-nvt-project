package com.ftn.restaurant.dto;

import com.ftn.restaurant.model.Order;
import com.ftn.restaurant.model.OrderedItem;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class OrderDTO {

    private Long id;
    private boolean isPaid;
    private double totalPrice;
    private String date;
    private String note;
    private LocalTime time;
    private boolean deleted;
    private List<OrderItemDTO> orderItems;

    public OrderDTO() {
        super();
    }

    public OrderDTO(Order o){
        this.id = o.getId();
        this.isPaid = o.isPaid();
        this.totalPrice = o.getTotalPrice();
        this.date = (o.getDate() == null) ? null : o.getDate().toString();
        this.note = o.getNote();
        this.time = o.getTime();
        this.deleted = o.isDeleted();
        fillOrderItems(o.getOrderedItems());
    }

    private void fillOrderItems(List<OrderedItem> orderItems) {
        if (this.orderItems == null)
            this.orderItems = new ArrayList<OrderItemDTO>();
        for (OrderedItem oItem : orderItems)
            this.orderItems.add(new OrderItemDTO(oItem));

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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
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

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public List<OrderItemDTO> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItemDTO> orderItems) {
        this.orderItems = orderItems;
    }
}
