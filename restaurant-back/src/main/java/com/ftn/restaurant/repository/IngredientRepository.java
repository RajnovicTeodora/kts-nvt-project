package com.ftn.restaurant.repository;

import com.ftn.restaurant.model.Ingredient;
import com.ftn.restaurant.model.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    Optional<Ingredient> findById(@Param("id") long id);

    @Query("select m from Ingredient m where m.name = :name and m.isAlergen = :b")
    Optional<Ingredient> findByIngredientNameAndIsAlergen(String name, boolean b);

    @Query("select m from Ingredient m left join fetch m.orderedItems ord where ord.id = ?1")
    List<Ingredient> findByOrderedItemId(long id);

    @Query("select m from Ingredient m where m.id = ?1")
    Optional<Ingredient> findByIngredientId(long id);
}
