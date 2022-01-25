package com.ftn.restaurant.dto;

import com.ftn.restaurant.model.Ingredient;
import com.ftn.restaurant.model.MenuItem;
import com.ftn.restaurant.model.OrderedItem;
import com.ftn.restaurant.model.enums.OrderedItemStatus;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class OrderItemDTO {

    private Long id;
    private String status;
    private int priority;
    private int quantity;
    private long menuItemId;
    private List<IngredientDTO> activeIngredients;
    private  String menuItemName;
    private double price;

    public OrderItemDTO() {
    }

    public OrderItemDTO(OrderedItem oi){
        this.id = oi.getId();
        this.status = oi.getStatus().name().toUpperCase(Locale.ROOT);
        this.priority = oi.getPriority();
        this.quantity = oi.getQuantity();
        this.menuItemId = oi.getMenuItem().getId();
        this.menuItemName = oi.getMenuItem().getName();
        fillActiveIngredients(oi.getActiveIngredients());

    }

    public OrderItemDTO(OrderedItem oi, String s){ //todo ako budes brisala ovo ne zaboravi i name da stavis
        this.id = oi.getId();
        this.status = oi.getStatus().toString();
        this.priority = oi.getPriority();
        this.quantity = oi.getQuantity();
        this.menuItemName = oi.getMenuItem().getName();
    }

    public OrderItemDTO(String status, int priority, int quantity, long menuItemId, List<IngredientDTO> activeIngredients) {
        this.status = status;
        this.priority = priority;
        this.quantity = quantity;
        this.menuItemId = menuItemId;
        this.activeIngredients = activeIngredients;
    }

    public OrderItemDTO(OrderedItemStatus status, int priority, int quantity, long menuItemId, List<IngredientDTO> activeIngredients) {
        this.status = status.toString();
        this.priority = priority;
        this.quantity = quantity;
        this.menuItemId = menuItemId;
        this.activeIngredients = activeIngredients;
    }

    private void fillActiveIngredients(List<Ingredient> ingredients) {
        if (this.activeIngredients == null)
            this.activeIngredients = new ArrayList<IngredientDTO>();
        for (Ingredient i : ingredients)
            this.activeIngredients.add(new IngredientDTO(i));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OrderedItemStatus getStatus() {
        return OrderedItemStatus.valueOf(status);
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public long getMenuItemId() {
        return menuItemId;
    }

    public void setMenuItemId(long menuItem) {
        this.menuItemId = menuItem;
    }

    public List<IngredientDTO> getActiveIngredients() {
        return activeIngredients;
    }

    public void setActiveIngredients(List<IngredientDTO> activeIngredients) {
        this.activeIngredients = activeIngredients;
    }


    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getMenuItemName() {
        return menuItemName;
    }

    public void setMenuItemName(String menuItemName) {
        this.menuItemName = menuItemName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

}
