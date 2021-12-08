package com.ftn.restaurant.repository;

import com.ftn.restaurant.model.Paychecks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;
@Repository
public interface PaycheckRepository extends JpaRepository<Paychecks, Long> {

    @Query("SELECT distinct  p FROM Paychecks p WHERE (p.employee.username = :username AND p.employee.deleted = false)" +
            " AND p.dateTo IS NULL")
    Optional<Paychecks> findByEmployeeUsernameAndEmployeeDeletedFalseAndDateToIsNull(
            @Param("username") String username);

    @Query("SELECT COALESCE(SUM(p.paycheck), 0) FROM Paychecks p WHERE " +
            "(p.dateFrom <= :from AND p.dateTo >= :to) OR (p.dateFrom <= :from AND p.dateTo IS NULL)")
    double sumTotalPaychecksAndDateBetween(@Param("from") LocalDate from, @Param("to") LocalDate to);

    Optional<Paychecks> findTopByOrderByDateFromAsc();

}