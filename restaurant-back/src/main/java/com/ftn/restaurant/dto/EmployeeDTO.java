package com.ftn.restaurant.dto;

import com.ftn.restaurant.model.Employee;

public class EmployeeDTO {
    
    private Long id;
    private String username;
    private String password;
    private String name;
    private String surname;
    private String image;
    private String telephone;
    private int role;


    public EmployeeDTO() {
    }

    public EmployeeDTO(Long id, String username, String password, String name, String surname, String image, String telephone, int role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.image = image;
        this.telephone = telephone;
        this.role = role; // 0-manager, 1-head chef, 2-chef, 3-bartender, 4-waiter
    }

    public EmployeeDTO(Employee employee){
        this.id = employee.getId();
        this.username = employee.getUsername();
        this.password = employee.getPassword();
        this.name = employee.getName();
        this.surname = employee.getSurname();
        this.image = employee.getImage();
        this.telephone = employee.getTelephone();
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return this.surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getImage() {
        return this.image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTelephone() {
        return this.telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public int getRole() {
        return this.role;
    }

    public void setRole(int role) {
        this.role = role;
    }

}
