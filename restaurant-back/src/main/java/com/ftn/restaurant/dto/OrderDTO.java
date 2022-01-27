package com.ftn.restaurant.dto;

import com.ftn.restaurant.model.Order;
import com.ftn.restaurant.model.OrderedItem;

import java.util.ArrayList;
import java.util.List;

public class OrderDTO {

    private Long id;
    private boolean isPaid;
    private double totalPrice;
    private String note;
    private List<OrderItemDTO> orderItems;
    private String waiterUsername;
    private long tableId;

    public OrderDTO() {
        super();
    }

    public OrderDTO(Order o) {
        this.id = o.getId();
        this.isPaid = o.isPaid();
        this.totalPrice = o.getTotalPrice();
        this.note = o.getNote();
        fillOrderItems(o.getOrderedItems());
    }

    public OrderDTO(Order o, String without_items) {
        this.id = o.getId();
        this.isPaid = o.isPaid();
        this.totalPrice = o.getTotalPrice();
        this.note = o.getNote();
        this.tableId = o.getRestaurantTable().getId();
    }

    private void fillOrderItems(List<OrderedItem> orderItems) {
        if (this.orderItems == null)
            this.orderItems = new ArrayList<OrderItemDTO>();
        for (OrderedItem oItem : orderItems)
            this.orderItems.add(new OrderItemDTO(oItem));

    }

    public OrderDTO(boolean isPaid, double totalPrice, String note, List<OrderItemDTO> orderItems) {
        this.isPaid = isPaid;
        this.totalPrice = totalPrice;
        this.note = note;
        this.orderItems = orderItems;
    }

    public OrderDTO(Long id, boolean isPaid, double totalPrice, String note, List<OrderItemDTO> orderItems) {
        this.id = id;
        this.isPaid = isPaid;
        this.totalPrice = totalPrice;
        this.note = note;
        this.orderItems = orderItems;
    }

    public OrderDTO(Long id, boolean isPaid, double totalPrice, String note, List<OrderItemDTO> orderItems, long tableId, String waiterUsername) {
        this.id = id;
        this.isPaid = isPaid;
        this.totalPrice = totalPrice;
        this.note = note;
        this.orderItems = orderItems;
        this.tableId = tableId;
        this.waiterUsername = waiterUsername;
    }

    public OrderDTO(boolean isPaid, double totalPrice, String note, List<OrderItemDTO> orderItems, long tableId, String waiterUsername) {
        this.isPaid = isPaid;
        this.totalPrice = totalPrice;
        this.note = note;
        this.orderItems = orderItems;
        this.tableId = tableId;
        this.waiterUsername = waiterUsername;
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public List<OrderItemDTO> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItemDTO> orderItems) {
        this.orderItems = orderItems;
    }

    public String getWaiterUsername() {
        return waiterUsername;
    }

    public void setWaiterUsername(String waiterUsername) {
        this.waiterUsername = waiterUsername;
    }

    public long getTableId() {
        return tableId;
    }

    public void setTableId(long tableId) {
        this.tableId = tableId;
    }
}
