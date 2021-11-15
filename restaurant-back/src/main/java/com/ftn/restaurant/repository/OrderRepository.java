package com.ftn.restaurant.repository;

import com.ftn.restaurant.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findTopByIsPaidTrueOrderByDateAsc();

    Optional<Order> findTopByIsPaidTrueOrderByDateDesc();

    List<Order> findAllByIsPaidTrue();

    @Query("SELECT COALESCE(SUM(o.totalPrice), 0) FROM Order o WHERE  (o.isPaid = true AND o.date between :from AND :to)")
    double sumTotalPriceByIsPaidAndDateBetween(@Param("from") LocalDate from, @Param("to") LocalDate to);
}