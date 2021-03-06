package com.ftn.restaurant.service;

import static com.ftn.restaurant.constants.PaycheckConstants.*;
import static com.ftn.restaurant.constants.DateTimeConstants.*;
import com.ftn.restaurant.dto.UpdatePaycheckDTO;
import com.ftn.restaurant.exception.EmployeeNotFoundException;
import com.ftn.restaurant.exception.ForbiddenException;
import com.ftn.restaurant.model.Paychecks;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class PaycheckServiceIntegrationTest {

    @Autowired
    private PaycheckService paycheckService;

    @Test
    public void testUpdatePaycheck(){
        UpdatePaycheckDTO updatePaycheckDTO = new UpdatePaycheckDTO(DB_EMPLOYEE_USERNAME, NEW_PAYCHECK);
        Paychecks paychecks = paycheckService.updatePaycheck(updatePaycheckDTO);

        assertEquals(NEW_PAYCHECK, paychecks.getPaycheck(), 0);
        assertEquals(3, paychecks.getEmployee().getPaychecksList().size());
        assertEquals(FIRST_DAY_OF_THE_MONTH, paychecks.getDateFrom());

        updatePaycheckDTO = new UpdatePaycheckDTO(DB_EMPLOYEE_USERNAME, NEW_PAYCHECK1);
        paychecks = paycheckService.updatePaycheck(updatePaycheckDTO);

        assertEquals(NEW_PAYCHECK1, paychecks.getPaycheck(), 0);
        assertEquals(FIRST_DAY_OF_THE_MONTH, paychecks.getDateFrom());
    }

    @Test(expected = EmployeeNotFoundException.class)
    public void testUpdatePaycheckAndExpectEmployeeNotFoundException(){
        UpdatePaycheckDTO updatePaycheckDTO = new UpdatePaycheckDTO(NON_EXISTENT_EMPLOYEE_USERNAME, NEW_PAYCHECK);

        paycheckService.updatePaycheck(updatePaycheckDTO);
    }

    @Test(expected = ForbiddenException.class)
    public void testUpdatePaycheckAndExpectForbiddenException(){
        UpdatePaycheckDTO updatePaycheckDTO = new UpdatePaycheckDTO(DB_EMPLOYEE_USERNAME, INVALID_PAYCHECK);

        paycheckService.updatePaycheck(updatePaycheckDTO);
    }
}
