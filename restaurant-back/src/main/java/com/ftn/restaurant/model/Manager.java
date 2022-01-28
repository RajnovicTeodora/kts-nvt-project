package com.ftn.restaurant.model;

import javax.persistence.Entity;

import com.ftn.restaurant.dto.EmployeeDTO;

@Entity
public class Manager extends Employee{

    public Manager(EmployeeDTO employeeDTO){
        super(employeeDTO);
    }


    public Manager() {
    }

}
