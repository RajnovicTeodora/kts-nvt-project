package com.ftn.restaurant.repository;

import static com.ftn.restaurant.constants.OrderedItemConstants.*;
import com.ftn.restaurant.model.OrderedItem;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class OrderedItemRepositoryTest_SQL {

    @Autowired
    private OrderedItemRepository orderedItemRepository;

    @Test
    public void findWithIdTest(){
        Optional<OrderedItem> maybeItem = orderedItemRepository.findWithId(1);
        Assert.assertTrue(maybeItem.isPresent());
        maybeItem = orderedItemRepository.findWithId(-1);
        Assert.assertFalse(maybeItem.isPresent());
    }

    //TODO T
    @Test
    public void testSumQuantityByOrderIsPaidAndOrderDateBetween(){
        int result = orderedItemRepository.sumQuantityByOrderIsPaidAndOrderDateBetween(DB_DATE_FROM, DB_DATE_TO);
        assertEquals(SUM_QUANTITY, result);
    }

    //TODO T
    @Test
    public void testSumPreparationCostsByOrderedItemStatusNotOrderedAndOrderDateBetween(){
        double result = orderedItemRepository.sumPreparationCostsByOrderedItemStatusNotOrderedAndOrderDateBetween(DB_DATE_FROM, DB_DATE_TO);
        assertEquals(SUM_PREPARATION_COSTS, result, 0);
    }
}
