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
import java.util.List;
import java.util.Optional;


@Service
public class PaycheckService {

    private final PaycheckRepository paycheckRepository;

    @Autowired
    public PaycheckService(PaycheckRepository paycheckRepository) {
        this.paycheckRepository = paycheckRepository;
    }

    public Paychecks updatePaycheck(UpdatePaycheckDTO updatePaycheckDTO) {
        if (updatePaycheckDTO.getNewSalary() <= 0)
            throw new ForbiddenException("New salary must be a positive value");

        Optional<Paychecks> maybeEmployeePaycheck = paycheckRepository
                .findByEmployeeUsernameAndEmployeeDeletedFalseAndDateToIsNull(updatePaycheckDTO.getUsername());

        if (!maybeEmployeePaycheck.isPresent())
            throw new EmployeeNotFoundException("Employee " + updatePaycheckDTO.getUsername() + " not found");

        Paychecks employeePaychecks = maybeEmployeePaycheck.get();
        LocalDate dateFrom = employeePaychecks.getDateFrom();
        Employee employee = employeePaychecks.getEmployee();
        // paycheck has lasted more than 1 month
        if (!dateFrom.getMonth().equals(LocalDate.now().getMonth())) {

            employee.setPaychecksList(paycheckRepository.findByEmployeeUsername(employee.getUsername()));
            LocalDate lastDayPreviousMonth = YearMonth.now().minusMonths(1).atEndOfMonth();
            LocalDate firstDayThisMonth = YearMonth.now().atDay(1);

            employeePaychecks.setDateTo(lastDayPreviousMonth);
            Paychecks newPaycheck = new Paychecks(firstDayThisMonth, null, updatePaycheckDTO.getNewSalary(), employee);
            employee.getPaychecksList().add(newPaycheck);
            paycheckRepository.save(employeePaychecks);
            return paycheckRepository.save(newPaycheck);
        }

        // paycheck hasn't lasted for a month
        employeePaychecks.setPaycheck(updatePaycheckDTO.getNewSalary());
        return paycheckRepository.save(employeePaychecks);
    }

    public List<Paychecks> getCurrentPaychecks(String username, String search, String filter) {
        return paycheckRepository.findAllByEmployeeUsernameNotAndDateToIsNullAndEmployeeTypeAndSearchCriteria(username, search, filter);
    }
}
