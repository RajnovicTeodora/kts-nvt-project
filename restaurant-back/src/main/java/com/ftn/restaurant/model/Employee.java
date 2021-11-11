package com.ftn.restaurant.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "employee")
public class Employee extends User {

    @Column(name = "name", nullable=false)
    private String name;

    @Column(name = "surname", nullable=false)
    private String surname;

    @Column(name = "image", nullable=false)
    private String image;

    @Column(name = "telephone", nullable=false)
    private String telephone;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "employee", cascade= CascadeType.ALL)
    private List<Paychecks> paychecksList;

    public List<Paychecks> getPaychecksList() {
        return paychecksList;
    }

    public void setPaychecksList(List<Paychecks> paychecksList) {
        this.paychecksList = paychecksList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}
