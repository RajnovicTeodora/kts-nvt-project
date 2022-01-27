package com.ftn.restaurant.repository;

import com.ftn.restaurant.model.Paychecks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
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

    List<Paychecks> findByEmployeeUsername(@Param("username") String username);

    @Query("SELECT p FROM Paychecks p WHERE (p.employee.username != :username AND p.employee.deleted = false)" +
            "AND (( lower(p.employee.name) like lower(concat('%', :searchName,'%')))" +
            "OR ( lower(p.employee.username) like lower(concat('%', :searchName,'%')))" +
            "OR ( lower(p.employee.surname) like lower(concat('%', :searchName,'%'))))" +
            "AND (p.employee.role.name = :filterName OR :filterName = '') AND (p.dateTo IS NULL)")
    List<Paychecks> findAllByEmployeeUsernameNotAndDateToIsNullAndEmployeeTypeAndSearchCriteria(String username, String searchName, String filterName);
}