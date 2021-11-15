package com.ftn.restaurant.model;

import com.ftn.restaurant.model.enums.OrderedItemStatus;

import javax.persistence.*;
import javax.persistence.Table;
import java.util.ArrayList;
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
    private MenuItem menuItem;

    @OneToMany(fetch = FetchType.LAZY,  cascade= CascadeType.ALL)
    private List<Ingredient> activeIngredients;

    @Column(name = "deleted")
    private boolean deleted;

    /*@ManyToOne(fetch = FetchType.EAGER)
	private Order order;*/

    public void addActiveIngredients(Ingredient ingredient) {
        if (ingredient == null)
            return;
        if (this.activeIngredients == null)
            this.activeIngredients = new ArrayList<>();
        if (!this.activeIngredients.contains(ingredient)) {
            this.activeIngredients.add(ingredient);
        }
    }

    public MenuItem getMenuItem() {
        return menuItem;
    }

    public List<Ingredient> getActiveIngredients() {
        return activeIngredients;
    }

    public void setActiveIngredients(List<Ingredient> activeIngredients) {
        this.activeIngredients = activeIngredients;
    }

    public void setMenuItem(MenuItem manuItem) {
        this.menuItem = manuItem;
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

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
