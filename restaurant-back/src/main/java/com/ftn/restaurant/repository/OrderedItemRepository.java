package com.ftn.restaurant.repository;


import com.ftn.restaurant.model.OrderedItem;
import com.ftn.restaurant.model.enums.OrderedItemStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;


public interface OrderedItemRepository extends JpaRepository<OrderedItem, Long> {

    @Query("SELECT COALESCE(SUM(oi.quantity), 0) FROM OrderedItem oi WHERE  " +
            "(oi.order.isPaid = true AND oi.order.date between :from AND :to)")
    int sumQuantityByMenuItemIsPaidAndMenuItemDateBetween(@Param("from") LocalDate from, @Param("to") LocalDate to);

    @Query("SELECT COALESCE(SUM(mip.purchasePrice * oi.quantity), 0) FROM Order o " +
            "LEFT JOIN MenuItemPrice mip ON  mip.item.id = o.id LEFT JOIN OrderedItem oi ON oi.manuItem.id = o.id WHERE" +
            "(oi.status <> com.ftn.restaurant.model.enums.OrderedItemStatus.ORDERED) AND" +
            "(o.date between :dateFrom AND :dateTo) AND" +
            "(o.date between mip.dateFrom AND mip.dateTo) OR (o.date >= mip.dateFrom AND mip.dateTo IS NULL)")
    double sumPreparationCostsByOrderedItemStatusNotOrderedAndOrderDateBetween(@Param("dateFrom") LocalDate dateFrom, @Param("dateTo") LocalDate dateTo);
}