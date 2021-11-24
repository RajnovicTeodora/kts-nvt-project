package com.ftn.restaurant.repository;

import java.util.List;
import java.util.Optional;

import com.ftn.restaurant.model.Order;
import com.ftn.restaurant.model.RestaurantTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TableRepository extends JpaRepository<RestaurantTable, Long> {

    @Query("select o from RestaurantTable o where o.id = ?1")
    public RestaurantTable findByTableId(Long id);

    @Query("select res from RestaurantTable res left join fetch res.waiter w where res.id =?1 and w.username =?2")
    public RestaurantTable findOneWithWaiter(Long id, String waiterUsername);

    @Query("select res from RestaurantTable res left join fetch res.waiter w where res.id =?1 ")
    public RestaurantTable findOneWithoutWaiter(Long id);

    // todo dodati queri ako treba
    Optional<RestaurantTable> findByPositionXAndPositionY(@Param("positionX") int positionX, @Param("positionY") int positionY);

    @Query("select res from RestaurantTable res where res.area.id = :area_id ")
    List<RestaurantTable> findByAreaId(@Param("area_id") Long areaId);

}
