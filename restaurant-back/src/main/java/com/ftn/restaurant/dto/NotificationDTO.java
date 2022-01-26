package com.ftn.restaurant.dto;

public class NotificationDTO {

    private Long id;
    private String content;
    private boolean isActive;

    public NotificationDTO(Long id, String content, boolean isActive) {
        this.id = id;
        this.content = content;
        this.isActive = isActive;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
