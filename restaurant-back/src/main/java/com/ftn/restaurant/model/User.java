package com.ftn.restaurant.model;

import static javax.persistence.InheritanceType.JOINED;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.Table;

@Entity
@Table(name = "system_user")
public abstract class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", unique=true, nullable=false)
    private Long id;

    @Column(name = "username", unique=true, nullable=false)
    private String username;

    @Column(name = "password", unique=false, nullable=false)
    private String password;

    @Column(name = "deleted", unique=false, nullable=false)
    private boolean deleted;

    public User() { }

    public User(String username, String password, boolean deleted) {
        super();
        this.username = username;
        this.password = password;
        this.deleted = deleted;
    }

    public User(Long id, String username, String password, boolean deleted) {
        super();
        this.id = id;
        this.username = username;
        this.password = password;
        this.deleted = deleted;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean isDeleted() {
        return deleted;
    }
}

