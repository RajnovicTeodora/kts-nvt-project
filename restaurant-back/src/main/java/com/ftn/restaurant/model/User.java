package com.ftn.restaurant.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.ftn.restaurant.model.enums.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import static javax.persistence.InheritanceType.JOINED;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
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


    @ManyToOne
    @JoinColumn(name = "role_id")
    // @JoinTable(name = "user_role",
    //         joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
    //         inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private UserRole role;

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

    public UserRole getRole() {
        return this.role;
    }

    public void setRole(UserRole roles) {
        this.role = roles;
    }

    public boolean isLoggedFirstTime() {
        return loggedFirstTime;
    }

    public void setLoggedFirstTime(boolean loggedFirstTime) {
        this.loggedFirstTime = loggedFirstTime;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        ArrayList<Authority> authorities = new ArrayList<Authority>();
        authorities.add(new Authority("ROLE_USER"));

        if(!this.loggedFirstTime){
            authorities.add(new Authority("ROLE_" + this.role.getName()));
        }
        return authorities;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

}

