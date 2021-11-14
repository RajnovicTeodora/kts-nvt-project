package com.ftn.restaurant.service;

import com.ftn.restaurant.dto.UpdatePaycheckDTO;
import com.ftn.restaurant.exception.EmployeeNotFoundException;
import com.ftn.restaurant.exception.ForbiddenException;
import com.ftn.restaurant.model.Employee;

import com.ftn.restaurant.model.Paychecks;
import com.ftn.restaurant.repository.PaycheckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Optional;


@Service
public class PaycheckService {

    private final PaycheckRepository paycheckRepository;

    @Autowired
    public PaycheckService(PaycheckRepository paycheckRepository) {
        this.paycheckRepository = paycheckRepository;
    }

    public Employee updatePaycheck(UpdatePaycheckDTO updatePaycheckDTO) {
        Optional<Paychecks> maybeEmployeePaycheck = paycheckRepository
                .findByEmployeeUsernameAndEmployeeDeletedAndDateToIsNull(updatePaycheckDTO.getUsername());

        if (updatePaycheckDTO.getNewSalary() <= 0)
            throw new ForbiddenException("New salary must be a positive value");
        if (!maybeEmployeePaycheck.isPresent())
            throw new EmployeeNotFoundException("Employee " + updatePaycheckDTO.getUsername() + " not found");

        Employee employee = maybeEmployeePaycheck.get().getEmployee();
        LocalDate dateFrom = maybeEmployeePaycheck.get().getDateFrom();

        // paycheck has lasted more than 1 month
        if (!dateFrom.getMonth().equals(LocalDate.now().getMonth())) {

            LocalDate lastDayPreviousMonth = YearMonth.now().minusMonths(1).atEndOfMonth();
            LocalDate firstDayThisMonth = YearMonth.now().atDay(1);

            maybeEmployeePaycheck.get().setDateTo(lastDayPreviousMonth);
            Paychecks newPaycheck = new Paychecks(firstDayThisMonth, null, updatePaycheckDTO.getNewSalary(), employee);
            employee.getPaychecksList().add(newPaycheck);
            paycheckRepository.save(newPaycheck);
        } else { // paycheck hasn't lasted for a month
            maybeEmployeePaycheck.get().setPaycheck(updatePaycheckDTO.getNewSalary());
        }
        paycheckRepository.save(maybeEmployeePaycheck.get());


        return employee;
    }
}
