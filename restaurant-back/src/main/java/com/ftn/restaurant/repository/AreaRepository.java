package com.ftn.restaurant.repository;

import java.util.Optional;

import com.ftn.restaurant.model.Area;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AreaRepository extends JpaRepository<Area, Long> {
    
    Optional<Area> findById(@Param("id") Long id);

    Optional<Area> findByName(@Param("name") String name);

}
