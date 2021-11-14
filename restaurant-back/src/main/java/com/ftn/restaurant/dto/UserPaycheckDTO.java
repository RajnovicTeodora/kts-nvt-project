package com.ftn.restaurant.dto;

import com.ftn.restaurant.model.Employee;

public class UserPaycheckDTO {

    private String username;
    private String name;
    private String surname;
    // TODO private String role?;
    private double paycheck;

    public UserPaycheckDTO() {
    }

    public UserPaycheckDTO(Employee employee, double paycheck) {
        this.username = employee.getUsername();
        this.name = employee.getName();
        this.surname = employee.getSurname();
        this.paycheck = paycheck;
    }

    public UserPaycheckDTO(String username, String name, String surname, double paycheck) {
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.paycheck = paycheck;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public double getPaycheck() {
        return paycheck;
    }

    public void setPaycheck(double paycheck) {
        this.paycheck = paycheck;
    }
}
