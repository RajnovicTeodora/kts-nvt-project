package com.ftn.restaurant.model;

import javax.persistence.*;

@Entity
@Table(name = "notification")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", unique=true, nullable=false)
    private Long id;

    @Column(name = "is_active", nullable=false)
    private boolean isActive; //Todo kada konobar pogleda ovu notifi. neka se setuje na false, do tada ce biti true

    @Column(name = "text", nullable=false)
    private String text;

   /* @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "item_id")
    private OrderedItem item;  //neka za sada bude item, ako bude potrebe promenicemo u order, ali obavestite natasu u tom slucaju
*/
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    private Employee recipient;

    public Notification( String message) {
        this.text = message;
        this.isActive = true;
    }

    public Notification(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
/*
    public OrderedItem getItem() {
        return item;
    }

    public void setItem(OrderedItem item) {
        this.item = item;
    }*/

    public Employee getRecipient() {
        return recipient;
    }

    public void setRecipient(Employee recipient) {
        this.recipient = recipient;
    }
}
