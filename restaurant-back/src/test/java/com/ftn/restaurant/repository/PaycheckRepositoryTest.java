package com.ftn.restaurant.repository;

import static com.ftn.restaurant.constants.DateTimeConstants.*;
import static com.ftn.restaurant.constants.PaycheckConstants.*;

import com.ftn.restaurant.dto.EmployeeDTO;
import com.ftn.restaurant.model.Chef;
import com.ftn.restaurant.model.Employee;
import com.ftn.restaurant.model.Manager;
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
import static org.junit.Assert.assertFalse;

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

        EmployeeDTO employeeDTO1 = new EmployeeDTO(21L, "Test1", "Test1", "Test1", "Test1", "Test1", "Test1", "CHEF");
        EmployeeDTO employeeDTO2 = new EmployeeDTO(22L, "Test2", "Test2", "Test2", "Test2", "Test2", "Test2", "CHEF");
        Employee employee1 = new Chef(employeeDTO1);
        Employee employee2 = new Chef(employeeDTO2);

        Paychecks paycheck = new Paychecks(FIRST_DAY_OF_LAST_MONTH,null, 55, null);
        // DB_EMPLOYEE_ID
        paycheck.setEmployee(employee1);

        entityManager.persist(employee1);
        entityManager.persist(employee2);
        entityManager.persist(paycheck);
    }

    @Test
    public void testSumTotalPaychecksAndDateBetween() {
        double sum = paycheckRepository.sumTotalPaychecksAndDateBetween(FIRST_DAY_OF_LAST_MONTH, TODAY);
        assertEquals(SUM_PAYCHECKS1, sum, 0);
    }

    @Test
    public void testFindByEmployeeUsernameAndEmployeeDeletedAndDateToIsNull(){
        //DB_EMPLOYEE_USERNAME
        Optional<Paychecks> found = paycheckRepository.findByEmployeeUsernameAndEmployeeDeletedFalseAndDateToIsNull("Test1");
        assertTrue(found.isPresent());

        found = paycheckRepository.findByEmployeeUsernameAndEmployeeDeletedFalseAndDateToIsNull("Test2");
        assertFalse(found.isPresent());
    }

}
