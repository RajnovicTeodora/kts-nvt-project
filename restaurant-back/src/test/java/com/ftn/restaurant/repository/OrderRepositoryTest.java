package com.ftn.restaurant.repository;

import com.ftn.restaurant.model.Order;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.Assert.assertEquals;


@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
public class OrderRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private MenuItemPriceRepository menuItemPriceRepository;

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Before
    public void setUp() {
        entityManager.persist(new Order(true, 5.5, LocalDate.now().minusDays(5), "some note...", LocalTime.now(), null, null));
        entityManager.persist(new Order(true, 75, LocalDate.now(), "some note...", LocalTime.now(), null, null));

    }

    //TODO T
    @Test
    public void testSumTotalPriceByIsPaidAndDateBetween() {
        double sum = orderRepository.sumTotalPriceByIsPaidAndDateBetween(LocalDate.now().minusDays(6), LocalDate.now());
        assertEquals(80.5, sum, 0);
    }

}
