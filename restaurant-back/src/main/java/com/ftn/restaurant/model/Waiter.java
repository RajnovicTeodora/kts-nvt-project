package com.ftn.restaurant.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Waiter extends User{

    @OneToMany(fetch = FetchType.LAZY, cascade= CascadeType.ALL)
    private List<RestaurantTable> tables;

    @OneToMany(fetch = FetchType.LAZY, cascade= CascadeType.ALL)
    private List<Notification> notifications;

    public List<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }

    public List<RestaurantTable> getTables() {
        return tables;
    }

    public void setTables(List<RestaurantTable> tables) {
        this.tables = tables;
    }
}
