package com.ftn.restaurant.repository;

import static com.ftn.restaurant.constants.DateTimeConstants.*;
import static com.ftn.restaurant.constants.PaycheckConstants.*;

import com.ftn.restaurant.model.Paychecks;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class PaycheckRepositoryTest_SQL {

    @Autowired
    public PaycheckRepository paycheckRepository;

    @Test
    public void testSumTotalPaychecksAndDateBetween(){
        double result = paycheckRepository.sumTotalPaychecksAndDateBetween(DB_DATE_FROM_NOV, DB_DATE_TO);
        assertEquals(SUM_PAYCHECKS2, result, 0);

    }

    @Test
    public void testFindByEmployeeUsernameAndEmployeeDeletedFalseAndDateToIsNull(){
        Optional<Paychecks> found = paycheckRepository.findByEmployeeUsernameAndEmployeeDeletedFalseAndDateToIsNull(DB_EMPLOYEE_USERNAME);
        assertTrue(found.isPresent());

        found = paycheckRepository.findByEmployeeUsernameAndEmployeeDeletedFalseAndDateToIsNull(DELETED_EMPLOYEE_USERNAME);
        assertFalse(found.isPresent());

    }
}
