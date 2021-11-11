package com.ftn.restaurant.model;

import javax.persistence.*;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "paychecks")
public class Paychecks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", unique=true, nullable=false)
    private Long id;

    @Column(name = "date_from", nullable=false)
    private LocalDate dateFrom;

    @Column(name = "date_to", nullable=false)
    private LocalDate dateTo;

    @Column(name = "paycheck", nullable=false)
    private double paychack;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(LocalDate dateFrom) {
        this.dateFrom = dateFrom;
    }

    public LocalDate getDateTo() {
        return dateTo;
    }

    public void setDateTo(LocalDate dateTo) {
        this.dateTo = dateTo;
    }

    public double getPaychack() {
        return paychack;
    }

    public void setPaychack(double paychack) {
        this.paychack = paychack;
    }
}
