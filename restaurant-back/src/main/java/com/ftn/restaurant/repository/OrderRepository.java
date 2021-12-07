package com.ftn.restaurant.repository;

import com.ftn.restaurant.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

	@Query("select o from Order o where o.id = ?1")
    public Order findByOrderId(Long id);

    @Query("select ord from Order ord left join fetch ord.orderedItems e where ord.id =?1")
    public Order findOneWithOrderItems(Long id);

    Optional<Order> findTopByIsPaidTrueOrderByDateAsc();

    Optional<Order> findTopByIsPaidTrueOrderByDateDesc();

    @Query("SELECT COALESCE(SUM(o.totalPrice), 0) FROM Order o WHERE  (o.isPaid = true AND o.date between :from AND :to)")
    double sumTotalPriceByIsPaidAndDateBetween(@Param("from") LocalDate from, @Param("to") LocalDate to);
}

