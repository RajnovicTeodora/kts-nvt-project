package com.ftn.restaurant.service;

import com.ftn.restaurant.dto.UpdatePaycheckDTO;
import com.ftn.restaurant.exception.EmployeeNotFoundException;
import com.ftn.restaurant.exception.ForbiddenException;
import com.ftn.restaurant.model.Employee;
import com.ftn.restaurant.model.Manager;
import com.ftn.restaurant.model.Paychecks;
import com.ftn.restaurant.repository.PaycheckRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.ftn.restaurant.constants.PaycheckConstants.*;
import static com.ftn.restaurant.constants.DateTimeConstants.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class PaycheckServiceUnitTest {

    @Autowired
    private PaycheckService paycheckService;

    @MockBean
    private PaycheckRepository paycheckRepository;

    @Before
    public void setup() {

        // Non existent paycheck
        given(paycheckRepository
                .findByEmployeeUsernameAndEmployeeDeletedFalseAndDateToIsNull(NON_EXISTENT_EMPLOYEE_USERNAME)).willReturn(Optional.empty());

        // Existent employee
        Employee employee = new Manager();

        Paychecks paycheck = new Paychecks(FIRST_DAY_OF_THE_MONTH, null, OLD_PAYCHECK, employee);
        Paychecks savedPaycheck = new Paychecks(FIRST_DAY_OF_THE_MONTH, null, NEW_PAYCHECK, employee);

        List<Paychecks> paychecksList = new ArrayList<Paychecks>()
        {
            {
                add(paycheck);
            }
        };
        employee.setPaychecksList(paychecksList);

        when(paycheckRepository.findByEmployeeUsernameAndEmployeeDeletedFalseAndDateToIsNull(DB_EMPLOYEE_USERNAME)).thenReturn(Optional.of(paycheck));
        when(paycheckRepository.save(any())).thenReturn(savedPaycheck);
    }

    @Test(expected = ForbiddenException.class)
    public void testUpdatePaycheckShouldReturnForbiddenExceptionWhenPaycheckIsLessThanOne(){
        UpdatePaycheckDTO updatePaycheckDTO = new UpdatePaycheckDTO(DB_EMPLOYEE_USERNAME, INVALID_PAYCHECK);
        Paychecks paychecks = paycheckService.updatePaycheck(updatePaycheckDTO);

    }

    @Test
    public void testUpdatePaycheck(){
        UpdatePaycheckDTO updatePaycheckDTO = new UpdatePaycheckDTO(DB_EMPLOYEE_USERNAME, NEW_PAYCHECK);
        Paychecks paychecks = paycheckService.updatePaycheck(updatePaycheckDTO);

        assertEquals(NEW_PAYCHECK, paychecks.getPaycheck(), 0);
    }

    @Test(expected = EmployeeNotFoundException.class)
    public void testUpdatePaycheckAndExpectForbiddenException(){
        UpdatePaycheckDTO updatePaycheckDTO = new UpdatePaycheckDTO(NON_EXISTENT_EMPLOYEE_USERNAME, NEW_PAYCHECK);

        paycheckService.updatePaycheck(updatePaycheckDTO);
    }
}
