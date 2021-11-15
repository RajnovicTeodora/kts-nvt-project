package com.ftn.restaurant.repository;


import com.ftn.restaurant.model.OrderedItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;


public interface OrderedItemRepository extends JpaRepository<OrderedItem, Long> {

    @Query("SELECT COALESCE(SUM(oi.quantity), 0) FROM OrderedItem oi WHERE  " +
            "(oi.order.isPaid = true AND oi.order.date between :from AND :to)")
    int sumQuantityByMenuItemIsPaidAndMenuItemDateBetween(@Param("from") LocalDate from, @Param("to") LocalDate to);

}