package com.ftn.restaurant.repository;

import com.ftn.restaurant.model.Dish;
import com.ftn.restaurant.model.enums.DishType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


public interface DishRepository extends JpaRepository<Dish, Long> {

    @Query("SELECT d from Dish d where d.name = :name AND d.dishType = :type AND d.deleted = false")
    Optional<Dish> findByNameAndDishType(String name, DishType type);
}
