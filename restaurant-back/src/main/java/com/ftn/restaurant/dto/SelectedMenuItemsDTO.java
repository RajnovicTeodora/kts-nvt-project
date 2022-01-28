package com.ftn.restaurant.dto;

import java.util.List;

public class SelectedMenuItemsDTO {
    protected List<Long> itemIds;

    public SelectedMenuItemsDTO() {
    }

    public SelectedMenuItemsDTO(List<Long> itemIds) {
        this.itemIds = itemIds;
    }

    public List<Long> getItemIds() {
        return itemIds;
    }

    public void setItemIds(List<Long> itemIds) {
        this.itemIds = itemIds;
    }
}
