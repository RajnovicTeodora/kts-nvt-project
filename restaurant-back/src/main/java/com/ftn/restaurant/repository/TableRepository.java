package com.ftn.restaurant.repository;

import com.ftn.restaurant.model.Order;
import com.ftn.restaurant.model.RestaurantTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TableRepository extends JpaRepository<RestaurantTable, Long> {

    @Query("select o from RestaurantTable o where o.id = ?1")
    public RestaurantTable findByTableId(Long id);

    @Query("select res from RestaurantTable res left join fetch res.waiter w where res.id =?1 and w.username =?2")
    public RestaurantTable findOneWithWaiter(Long id, String waiterUsername);

    @Query("select res from RestaurantTable res left join fetch res.waiter w where res.id =?1 ")
    public RestaurantTable findOneWithoutWaiter(Long id);

}
