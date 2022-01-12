package com.ftn.restaurant.dto;

public class NotificationDTO {

    private String content;
    private boolean isActive;
    private long orderedItemId;

    public NotificationDTO(String content, boolean isActive, long orderedItemId) {
        this.content = content;
        this.isActive = isActive;
        this.orderedItemId = orderedItemId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public long getOrderedItemId() {
        return orderedItemId;
    }

    public void setOrderedItemId(long orderedItemId) {
        this.orderedItemId = orderedItemId;
    }
}
