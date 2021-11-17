package com.ftn.restaurant.model;

import static javax.persistence.InheritanceType.JOINED;
import javax.persistence.*;

import com.ftn.restaurant.dto.EmployeeDTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "employees")
@Inheritance(strategy=JOINED)
public abstract class Employee extends User {

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "image")
    private String image;

    @Column(name = "telephone")
    private String telephone;

    @OneToMany(fetch = FetchType.LAZY, cascade= CascadeType.ALL)
    private List<Paychecks> paychecksList;

    public Employee() {
    }

    public Employee(String name, String surname, String image, String telephone, List<Paychecks> paychecksList) {
        this.name = name;
        this.surname = surname;
        this.image = image;
        this.telephone = telephone;
        this.paychecksList = paychecksList;
    }

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

    public Employee(EmployeeDTO employeeDTO){
        super(employeeDTO.getUsername(), employeeDTO.getPassword(), false);
        this.name = employeeDTO.getName();
        this.surname = employeeDTO.getSurname();
        this.image = employeeDTO.getImage();
        this.telephone = employeeDTO.getTelephone();
    }
}
