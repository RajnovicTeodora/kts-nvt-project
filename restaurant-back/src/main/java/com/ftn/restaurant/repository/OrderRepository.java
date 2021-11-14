package com.ftn.restaurant.repository;

import com.ftn.restaurant.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("select o from Order o where o.id = ?1")
    public Order findByOrderId(Long id);

    @Query("select ord from Order ord left join fetch ord.orderedItems e where ord.id =?1")
    public Order findOneWithOrderItems(Long id);
}
