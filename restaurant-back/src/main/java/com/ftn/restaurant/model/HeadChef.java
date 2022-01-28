package com.ftn.restaurant.model;

import javax.persistence.Entity;

import com.ftn.restaurant.dto.EmployeeDTO;

@Entity
public class HeadChef extends Chef{

    public HeadChef(EmployeeDTO employeeDTO){
        super(employeeDTO);
    }


    public HeadChef() {
        super();
    }

}
