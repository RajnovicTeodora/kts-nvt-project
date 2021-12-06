package com.ftn.restaurant.repository;

import com.ftn.restaurant.model.Dish;
import com.ftn.restaurant.model.MenuItem;
import com.ftn.restaurant.model.enums.DishType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DishRepository extends JpaRepository<Dish, Long> {

    @Query("SELECT d from Dish d where lower(d.name) = lower(:name) AND d.dishType = :type AND d.deleted = false")
    Optional<Dish> findByNameAndDishType(String name, DishType type);

    Optional<Dish> findById(@Param("id") long id);

    @Query("SELECT d from Dish d where" +
            " (lower(d.name) LIKE lower(concat('%',:name,'%')) OR :name = '...') AND (:type = null or :type = d.dishType ) AND d.deleted = false")
    List<MenuItem> findByNameAndType(@Param("name") String name, @Param("type") DishType type);
}
