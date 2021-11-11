package com.ftn.restaurant.model;

import com.ftn.restaurant.model.enums.OrderedItemStatus;

import javax.persistence.*;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "ordered_item")
public class OrderedItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", unique=true, nullable=false)
    private Long id;

    @Column(name = "status", nullable=false)
    @Enumerated(EnumType.STRING)
    private OrderedItemStatus status;

    @Column(name = "priority", nullable=false)
    private int priority;

    @OneToOne(cascade = CascadeType.ALL)
    private MenuItem manuItem;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "ordered_item", cascade= CascadeType.ALL)
    private List<Ingredient> activeIngredients;

    public MenuItem getManuItem() {
        return manuItem;
    }

    public List<Ingredient> getActiveIngredients() {
        return activeIngredients;
    }

    public void setActiveIngredients(List<Ingredient> activeIngredients) {
        this.activeIngredients = activeIngredients;
    }

    public void setManuItem(MenuItem manuItem) {
        this.manuItem = manuItem;
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
}
