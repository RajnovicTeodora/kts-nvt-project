package com.ftn.restaurant.model;

import javax.persistence.*;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "menu_item")
public abstract class MenuItem {

    @Column(name = "name", nullable=false)
    private String name;

    @Column(name = "image", nullable=false)
    private String image;

    @Column(name = "approved", nullable=false)
    private boolean approved;

    @Column(name = "deleted", nullable=false)
    private boolean deleted;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", unique=true, nullable=false)
    private Long id;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "menu_item", cascade= CascadeType.ALL)
    private List<MenuItemPrice> priceList;

    public void setId(Long id) {
        this.id = id;
    }

    public List<MenuItemPrice> getPriceList() {
        return priceList;
    }

    public void setPriceList(List<MenuItemPrice> priceList) {
        this.priceList = priceList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Long getId() {
        return id;
    }
}
