package com.ftn.restaurant.repository;

import com.ftn.restaurant.model.OrderedItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface OrderedItemRepository extends JpaRepository<OrderedItem, Long> {

    Optional<OrderedItem> findById(@Param("id") long id);


}
