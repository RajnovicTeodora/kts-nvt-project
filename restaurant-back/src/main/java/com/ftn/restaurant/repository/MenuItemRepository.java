package com.ftn.restaurant.repository;

import com.ftn.restaurant.model.MenuItem;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {

    @Query("select m from MenuItem m left join fetch m.menuItemIngredients where m.id = :id and m.deleted = false")
    Optional<MenuItem> findById(@Param("id") long id);

    Optional<MenuItem> findByIdAndDeletedFalse(@Param("id") long id);

    @Query("select m from MenuItem m where m.deleted = false and m.approved = false and ( lower(m.name) like lower(concat('%', :searchName,'%')))")
    List<MenuItem> findAll(String searchName);

    @Query("select m from MenuItem m where m.deleted = false and m.approved = true and ( lower(m.name) like lower(concat('%', :searchName,'%')))")
    List<MenuItem> findByDeletedFalseAndApprovedTrueAndBySearchCriteria(String searchName);
}
