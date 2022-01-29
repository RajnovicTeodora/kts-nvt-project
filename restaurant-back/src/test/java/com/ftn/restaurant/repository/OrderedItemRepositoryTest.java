package com.ftn.restaurant.repository;

import com.ftn.restaurant.model.MenuItemPrice;
import com.ftn.restaurant.model.Order;
import com.ftn.restaurant.model.OrderedItem;
import com.ftn.restaurant.model.enums.OrderedItemStatus;

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
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
public class OrderedItemRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Autowired
    private OrderedItemRepository orderedItemRepository;

    //TODO add menu item without any prices, replace with constants
    @Before
    public void setUp() {
        OrderedItem orderedItem = new OrderedItem(OrderedItemStatus.DELIVERED, 2, 5, null, null, new ArrayList<>(), false, null);
        ArrayList<OrderedItem> orderedItems = new ArrayList<>();
        orderedItems.add(orderedItem);
        Order order = new Order(true, 75, LocalDate.now(), "some note...", LocalTime.now(), orderedItems);
        orderedItem.setOrder(order);
        MenuItemPrice menuItemPrice = new MenuItemPrice(LocalDate.now(), null, 15, true, 5.5, null);

        orderedItem.setMenuItem(menuItemRepository.findByIdAndDeletedFalse(2).get());
        menuItemPrice.setItem(menuItemRepository.findByIdAndDeletedFalse(2).get());

        entityManager.persist(order);
        entityManager.persist(orderedItem);
        entityManager.persist(menuItemPrice);
    }

    //TODO T
    @Test
    public void testSumQuantityByOrderIsPaidAndOrderDateBetween() {
        int sum = orderedItemRepository.sumQuantityByOrderIsPaidAndOrderDateBetween(LocalDate.now().minusDays(1), LocalDate.now());
        assertEquals(5, sum);
    }


    //TODO T
    @Test
    public void tesSumPreparationCostsByOrderedItemStatusNotOrderedAndOrderDateBetween() {
        double sum = orderedItemRepository.sumPreparationCostsByOrderedItemStatusNotOrderedAndOrderDateBetween(LocalDate.now().minusDays(1), LocalDate.now());
        assertEquals(27.5, sum, 0);
    }
}
