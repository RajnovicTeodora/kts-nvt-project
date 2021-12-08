package com.ftn.restaurant.repository;

import static com.ftn.restaurant.constants.DateTimeConstants.*;
import static com.ftn.restaurant.constants.PaycheckConstants.*;

import com.ftn.restaurant.model.Paychecks;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
public class PaycheckRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PaycheckRepository paycheckRepository;

    @Before
    public void setUp() {
        Paychecks paycheck = new Paychecks(FIRST_DAY_OF_THE_MONTH,null, 55, null);
        paycheck.setEmployee(employeeRepository.getById(DB_EMPLOYEE_ID));
        entityManager.persist(paycheck);

    }

    //TODO T
    @Test
    public void testSumTotalPaychecksAndDateBetween() {
        double sum = paycheckRepository.sumTotalPaychecksAndDateBetween(FIRST_DAY_OF_THE_MONTH, TODAY);
        assertEquals(SUM_PAYCHECKS1, sum, 0);
    }

    //TODO T - username of
    @Test
    public void testFindByEmployeeUsernameAndEmployeeDeletedAndDateToIsNull(){
        Optional<Paychecks> found = paycheckRepository.findByEmployeeUsernameAndEmployeeDeletedFalseAndDateToIsNull(DB_EMPLOYEE_USERNAME);
        assertTrue(found.isPresent());

    }

}
