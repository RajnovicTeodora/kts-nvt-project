package com.ftn.restaurant.model;

import javax.persistence.*;
import javax.persistence.Table;


@Entity
@Table(name = "ingredient")
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", unique=true, nullable=false)
    private Long id;

    @Column(name = "name", nullable=false)
    private String name;

    @Column(name = "alergen", nullable=false)
    private boolean isAlergen;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAlergen() {
        return isAlergen;
    }

    public void setAlergen(boolean alergen) {
        isAlergen = alergen;
    }


}
