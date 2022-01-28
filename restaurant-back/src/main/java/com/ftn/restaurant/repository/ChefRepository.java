package com.ftn.restaurant.repository;

import com.ftn.restaurant.model.Bartender;
import com.ftn.restaurant.model.Chef;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChefRepository extends JpaRepository<Chef, Long> {
}
