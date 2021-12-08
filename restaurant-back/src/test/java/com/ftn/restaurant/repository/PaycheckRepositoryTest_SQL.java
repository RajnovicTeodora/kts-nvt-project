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

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class PaycheckRepositoryTest_SQL {

    @Autowired
    public PaycheckRepository paycheckRepository;

    //TODO T
    @Test
    public void testSumTotalPaychecksAndDateBetween(){
        double result = paycheckRepository.sumTotalPaychecksAndDateBetween(DB_DATE_FROM, DB_DATE_TO);
        assertEquals(SUM_PAYCHECKS1, result, 0);
    }

    //TODO T
    @Test
    public void testFindByEmployeeUsernameAndEmployeeDeletedFalseAndDateToIsNull(){
        Optional<Paychecks> found = paycheckRepository.findByEmployeeUsernameAndEmployeeDeletedFalseAndDateToIsNull(DB_EMPLOYEE_USERNAME);
        assertTrue(found.isPresent());
    }
}
