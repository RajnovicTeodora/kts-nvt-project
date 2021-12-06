package com.ftn.restaurant.repository;

import com.ftn.restaurant.model.MenuItem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {

    Optional<MenuItem> findById(@Param("id") long id);

    Optional<MenuItem> findByIdAndDeletedFalse(@Param("id") long id);

}
