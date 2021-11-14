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
    private OrderedItemStatus status;
    private int priority;
    private MenuItemDTO menuItem;
    private List<IngredientDTO> activeIngredients;

    public OrderItemDTO() {
    }

    public OrderItemDTO(OrderedItem oi){
        this.id = oi.getId();
        this.status = oi.getStatus();
        this.priority = oi.getPriority();
        this.menuItem = new MenuItemDTO(oi.getMenuItem());
        fillActiveIngredients(oi.getActiveIngredients());
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
        return status;
    }

    public void setStatus(OrderedItemStatus status) {
        this.status = status;
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
}
