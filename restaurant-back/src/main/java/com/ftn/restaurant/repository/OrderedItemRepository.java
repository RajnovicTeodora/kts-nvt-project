package com.ftn.restaurant.repository;

import com.ftn.restaurant.model.OrderedItem;
import com.ftn.restaurant.model.enums.OrderedItemStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface OrderedItemRepository extends JpaRepository<OrderedItem, Long> {

  Optional<OrderedItem> findById(@Param("id") long id);

  @Query("SELECT COALESCE(SUM(oi.quantity), 0) FROM OrderedItem oi WHERE  " +
      "(oi.order.isPaid = true AND oi.order.date between :from AND :to)")
  int sumQuantityByMenuItemIsPaidAndMenuItemDateBetween(@Param("from") LocalDate from, @Param("to") LocalDate to);

  @Query("SELECT COALESCE(SUM(mip.purchasePrice * oi.quantity), 0) FROM OrderedItem oi " +
      "INNER JOIN MenuItemPrice mip ON mip.item.id = oi.menuItem.id INNER JOIN Order o ON oi.order.id = o.id WHERE" +
      "(oi.status <> com.ftn.restaurant.model.enums.OrderedItemStatus.ORDERED) AND oi.deleted = false AND" +
      "(o.date between :dateFrom AND :dateTo) AND" +
      "((o.date between mip.dateFrom AND mip.dateTo) OR (o.date >= mip.dateFrom AND mip.dateTo IS NULL))")
  double sumPreparationCostsByOrderedItemStatusNotOrderedAndOrderDateBetween(@Param("dateFrom") LocalDate dateFrom,
      @Param("dateTo") LocalDate dateTo);

  @Query("SELECT i from OrderedItem i where i.id = :id")
  Optional<OrderedItem> findWithId(long id);
}
