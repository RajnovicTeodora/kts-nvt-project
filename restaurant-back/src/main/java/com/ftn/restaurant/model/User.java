package com.ftn.restaurant.model;

import com.ftn.restaurant.model.enums.UserRole;
import org.springframework.security.core.userdetails.UserDetails;

import static javax.persistence.InheritanceType.JOINED;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "system_user")
@Inheritance(strategy=JOINED)
public abstract class User implements UserDetails {

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

    @Column(name = "loggedFirstTime", unique=false, nullable=false)
    private boolean loggedFirstTime;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private List<UserRole> roles;

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

    public List<UserRole> getRoles() {
        return this.roles;
    }

    public void setRoles(List<UserRole> roles) {
        this.roles = roles;
    }

    public boolean isLoggedFirstTime() {
        return loggedFirstTime;
    }

    public void setLoggedFirstTime(boolean loggedFirstTime) {
        this.loggedFirstTime = loggedFirstTime;
    }
}

