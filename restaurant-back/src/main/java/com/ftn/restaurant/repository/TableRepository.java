package com.ftn.restaurant.repository;

import java.util.List;
import java.util.Optional;

import com.ftn.restaurant.model.RestaurantTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TableRepository extends JpaRepository<RestaurantTable, Long> {

    @Query("select o from RestaurantTable o where o.id = ?1")
    RestaurantTable findByTableId(Long id);

    @Query("select res from RestaurantTable res left join fetch res.waiter w where res.id =?1 and w.username =?2")
    RestaurantTable findOneWithWaiter(long tableId, String waiterUsername);

    // todo dodati queri ako treba
    Optional<RestaurantTable> findByPositionXAndPositionY(@Param("positionX") int positionX, @Param("positionY") int positionY);

    @Query("select res from RestaurantTable res where res.area.id = :area_id ")
    List<RestaurantTable> findByAreaId(@Param("area_id") Long areaId);

    @Query("select res from RestaurantTable res where res.tableNum = ?1 ")
    Optional<RestaurantTable> getTableByTableNumber(int tableNum);

    @Query("select o from RestaurantTable o where o.id = ?1")
    Optional<RestaurantTable> findById(Long id);
}
