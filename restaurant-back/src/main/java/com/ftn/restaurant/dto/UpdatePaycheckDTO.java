package com.ftn.restaurant.dto;

public class UpdatePaycheckDTO {
    private String username;
    private double newSalary;

    public UpdatePaycheckDTO() {
    }

    public UpdatePaycheckDTO(String username, double newSalary) {
        this.username = username;
        this.newSalary = newSalary;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public double getNewSalary() {
        return newSalary;
    }

    public void setNewSalary(double newSalary) {
        this.newSalary = newSalary;
    }
}
