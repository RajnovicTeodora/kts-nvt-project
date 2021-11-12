package com.ftn.restaurant.repository;

import com.ftn.restaurant.model.Drink;

import com.ftn.restaurant.model.enums.ContainerType;
import com.ftn.restaurant.model.enums.DrinkType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface DrinkRepository extends JpaRepository<Drink, Long> {

    Optional<Drink> findById(@Param("id") long id);

    @Query("SELECT d from Drink d where" +
            " d.name = :name AND d.drinkType = :drinkType AND d.containerType = :containerType AND d.deleted = false")
    Optional<Drink> findByNameAndDrinkTypeAndContainerType(String name, DrinkType drinkType, ContainerType containerType);
}