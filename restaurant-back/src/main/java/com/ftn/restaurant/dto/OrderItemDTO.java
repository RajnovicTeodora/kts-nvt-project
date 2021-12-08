package com.ftn.restaurant.dto;

import com.ftn.restaurant.model.Ingredient;
import com.ftn.restaurant.model.MenuItem;
import com.ftn.restaurant.model.OrderedItem;
import com.ftn.restaurant.model.enums.OrderedItemStatus;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

public class OrderItemDTO {

    private Long id;
    private String status;
    private int priority;
    private int quantity;
    private boolean deleted;
    private MenuItemDTO menuItem;
    private List<IngredientDTO> activeIngredients;

    public OrderItemDTO() {
    }

    public OrderItemDTO(OrderedItem oi){
        this.id = oi.getId();
        this.status = oi.getStatus().toString();
        this.priority = oi.getPriority();
        this.quantity = oi.getQuantity();
        this.deleted = oi.isDeleted();
        this.menuItem = new MenuItemDTO(oi.getMenuItem());
        fillActiveIngredients(oi.getActiveIngredients());
    }

    public OrderItemDTO(String status, int priority, int quantity, boolean deleted, MenuItemDTO menuItem, List<IngredientDTO> activeIngredients) {
        this.status = status;
        this.priority = priority;
        this.quantity = quantity;
        this.deleted = deleted;
        this.menuItem = menuItem;
        this.activeIngredients = activeIngredients;
    }

    public OrderItemDTO(OrderedItemStatus status, int priority, int quantity, boolean deleted, MenuItemDTO menuItem, List<IngredientDTO> activeIngredients) {
        this.status = status.toString();
        this.priority = priority;
        this.quantity = quantity;
        this.deleted = deleted;
        this.menuItem = menuItem;
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

    public void setStatus(OrderedItemStatus status) {
        this.status = status.toString();
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public MenuItemDTO getMenuItem() {
        return menuItem;
    }

    public void setMenuItem(MenuItemDTO menuItem) {
        this.menuItem = menuItem;
    }

    public List<IngredientDTO> getActiveIngredients() {
        return activeIngredients;
    }

    public void setActiveIngredients(List<IngredientDTO> activeIngredients) {
        this.activeIngredients = activeIngredients;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
