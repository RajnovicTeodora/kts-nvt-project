package com.ftn.restaurant.repository;

import com.ftn.restaurant.model.MenuItemPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
@Repository
public interface MenuItemPriceRepository extends JpaRepository<MenuItemPrice, Long> {

    @Query("SELECT mip FROM MenuItemPrice mip WHERE (mip.item.id = :id AND mip.item.deleted = false" +
            " AND mip.item.approved = true AND ((:date BETWEEN mip.dateFrom AND mip.dateTo) OR (:date >= mip.dateFrom AND mip.dateTo IS NULL)))")
    Optional<MenuItemPrice> findByMenuItemIdAndDeletedNotAndApprovedAndHasPrice(
            @Param("id") long id, @Param("date") LocalDate date);

    List<MenuItemPrice> findByItemIdNotIn(@Param("itemIds") List<Long> itemIds);

    Optional<MenuItemPrice> findByItemIdAndItemDeletedFalseAndItemApprovedTrueAndDateToIsNull(@Param("id") long id);
}