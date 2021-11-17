package com.ftn.restaurant.repository;

import com.ftn.restaurant.model.MenuItem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {

    Optional<MenuItem> findById(@Param("id") long id);

    @Query("SELECT m FROM MenuItem m WHERE (lower(m.name) LIKE lower(concat('%',:name,'%')) AND m.deleted = false" +
            " AND m.approved = true)")
    List<MenuItem> findByName(@Param("name") String name);


}
