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
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "menu_item_id", nullable = false)
    private MenuItem manuItem;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Ingredient> activeIngredients;

    @ManyToOne(fetch=FetchType.EAGER)
    private Order order;

    @ManyToOne(fetch=FetchType.EAGER)
    private Employee employee;

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

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
}
