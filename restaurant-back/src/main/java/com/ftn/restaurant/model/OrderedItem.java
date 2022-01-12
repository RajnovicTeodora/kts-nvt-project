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
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderedItemStatus status;

    @Column(name = "priority", nullable = false)
    private int priority;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "menu_item_id")
    private MenuItem menuItem;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "activeIngredients",
            joinColumns = @JoinColumn(name = "ordered_item_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id", referencedColumnName = "id"))
    private List<Ingredient> activeIngredients;

    @Column(name = "deleted")
    private boolean deleted;

    @ManyToOne(fetch = FetchType.EAGER)
    //@JoinColumn(name = "employ_id", nullable = false) todo proveri, i dodaj u servis
    private Employee whoPreapiring;

    @ManyToOne(fetch=FetchType.EAGER)
    private Employee employee;

    @OneToMany(fetch = FetchType.LAZY,  cascade= CascadeType.ALL)
    private List<Notification> notificationList;

    public void addActiveIngredients(Ingredient ingredient) {
        if (ingredient == null)
            return;
        if (this.activeIngredients == null)
            this.activeIngredients = new ArrayList<>();
        if (!this.activeIngredients.contains(ingredient)) {
            this.activeIngredients.add(ingredient);
        }
    }

    public OrderedItem() {
    }

    public OrderedItem(OrderedItemStatus status, int priority, int quantity, MenuItem menuItem, List<Ingredient> activeIngredients, boolean deleted) {
        this.status = status;
        this.priority = priority;
        this.quantity = quantity;
        this.menuItem = menuItem;
        this.activeIngredients = activeIngredients;
        this.deleted = deleted;
    }

    public OrderedItem(OrderedItemStatus status, int priority, int quantity, Order order, MenuItem menuItem, List<Ingredient> activeIngredients, boolean deleted, Employee whoPreapiring, Employee employee) {
        this.status = status;
        this.priority = priority;
        this.quantity = quantity;
        this.order = order;
        this.menuItem = menuItem;
        this.activeIngredients = activeIngredients;
        this.deleted = deleted;
        this.whoPreapiring = whoPreapiring;
        this.employee = employee;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
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

    public void setMenuItem(MenuItem menuItem) {
        this.menuItem = menuItem;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Employee getWhoPreapiring() {
        return whoPreapiring;
    }

    public void setWhoPreapiring(Employee whoPreapiring) {
        this.whoPreapiring = whoPreapiring;
    }
}
