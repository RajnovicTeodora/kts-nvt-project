package com.ftn.restaurant.repository;

import com.ftn.restaurant.model.Order;
import com.ftn.restaurant.model.Paychecks;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.security.access.method.P;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.Assert.assertEquals;


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
        Paychecks paycheck = new Paychecks(LocalDate.now().minusMonths(1).withDayOfMonth(1),null, 55, null);
        paycheck.setEmployee(employeeRepository.getById(2L));
        entityManager.persist(paycheck);

    }

    @Test
    public void testSumTotalPaychecksAndDateBetween() {
        double sum = paycheckRepository.sumTotalPaychecksAndDateBetween(LocalDate.now().minusMonths(1).withDayOfMonth(1), LocalDate.now());
        assertEquals(55, sum, 0);
    }

    //TODO findByEmployeeUsernameAndEmployeeDeletedAndDateToIsNull

}
