package com.ftn.restaurant.repository;

import com.ftn.restaurant.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

	@Query("select o from Order o where o.id = ?1")
    public Order findByOrderId(Long id);

    @Query("select ord from Order ord left join fetch ord.orderedItems e left join fetch e.menuItem m  where ord.id =?1")
    Order findOneWithOrderItems(long id);

    Optional<Order> findTopByIsPaidTrueOrderByDateAsc();

    Optional<Order> findTopByIsPaidTrueOrderByDateDesc();

    @Query("SELECT COALESCE(SUM(o.totalPrice), 0) FROM Order o WHERE  (o.isPaid = true AND o.date between :from AND :to)")
    double sumTotalPriceByIsPaidAndDateBetween(@Param("from") LocalDate from, @Param("to") LocalDate to);

    @Query("select ord.id from Order ord " +
           // "left join fetch ord.restaurantTable t  left join fetch ord.waiter w " +
            "where ord.waiter.username=?2 and ord.restaurantTable.tableNum=?1 ")//and not (e.status = 'DELIVERED')
    List<Long> getAllActiveOrdersForTable(int tableNum, String waiterUsername);


}

