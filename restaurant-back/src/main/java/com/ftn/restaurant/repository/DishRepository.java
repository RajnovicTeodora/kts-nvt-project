package com.ftn.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

import com.ftn.restaurant.model.Dish;
import com.ftn.restaurant.model.MenuItem;

public interface DishRepository  extends JpaRepository<Dish, Long>{
    
    Optional<Dish> findById(@Param("id") long id);

    @Query("SELECT d from Dish d where" +
            " lower(d.name) LIKE lower(concat('%',:name,'%')) AND (:type = -1 or :type = d.dishType ) AND d.deleted = false")
    List<MenuItem> findByNameAndType(@Param("name") String name, @Param("type") int type);
}
