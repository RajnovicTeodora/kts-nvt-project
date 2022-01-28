package com.ftn.restaurant.repository;

import static com.ftn.restaurant.constants.OrderConstants.*;
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
public class OrderRepositoryTest_SQL {

    @Autowired
    public OrderRepository orderRepository;

    //TODO T
    @Test
    public void testSumTotalPriceByIsPaidAndDateBetween(){
        double result = orderRepository.sumTotalPriceByIsPaidAndDateBetween(DB_DATE_FROM, DB_DATE_TO);
        assertEquals(DB_SUM_TOTAL_PRICE, result, 0);
    }

}
