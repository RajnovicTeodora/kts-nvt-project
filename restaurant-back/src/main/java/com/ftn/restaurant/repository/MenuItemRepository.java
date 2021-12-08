package com.ftn.restaurant.repository;

import com.ftn.restaurant.model.MenuItem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {

    Optional<MenuItem> findById(@Param("id") long id);

    Optional<MenuItem> findByIdAndDeletedFalse(@Param("id") long id);

    @Query("select m from MenuItem m where m.id = :id")
    Optional<MenuItem> findByMenuItemId(long id);

    @Query("select m from MenuItem m where m.name = :name and m.image = :img")
    Optional<MenuItem> findByMenuItemNameAndImage(String name, String img);

}
