package com.ftn.restaurant.repository;

import com.ftn.restaurant.model.Paychecks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PaycheckRepository extends JpaRepository<Paychecks, Long> {

    @Query("SELECT distinct  p FROM Paychecks p WHERE (p.employee.username = :username AND p.employee.deleted = false)" +
            " AND p.dateTo IS NULL")
    Optional<Paychecks> findByEmployeeUsernameAndEmployeeDeletedAndDateToIsNull(
            @Param("username") String username);

}