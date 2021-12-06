package com.ftn.restaurant.service;

import com.ftn.restaurant.repository.OrderedItemRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
//@TestPropertySource("classpath:application-test.properties")
public class OrderedeItemIntegrationTest {
    @Autowired
    private OrderedItemService orderedItemService;

    @Test
    public void acceptOrderedItemTest(){
        Assert.assertEquals("You accepted order with id: 7",orderedItemService.acceptOrderedItem(7));
        Assert.assertEquals("Order doesn't exists",orderedItemService.acceptOrderedItem(10000000));
        Assert.assertEquals("Order doesn't exists",orderedItemService.acceptOrderedItem(-10));
        Assert.assertEquals("You can't accept order if it is not in status ordered.",
                orderedItemService.acceptOrderedItem(2));
        Assert.assertEquals("You can't accept order if it is not in status ordered.",
                orderedItemService.acceptOrderedItem(3));
    }

    @Test
    public void finishOrderedItemTest(){
        Assert.assertEquals("You finished order with id: 1",orderedItemService.finishOrderedItem(1));
        Assert.assertEquals("Order doesn't exists",orderedItemService.finishOrderedItem(10000000));
        Assert.assertEquals("Order doesn't exists",orderedItemService.finishOrderedItem(-10));
        Assert.assertEquals("You can't finish order if it is not in status in progres.",
                orderedItemService.finishOrderedItem(2));
        Assert.assertEquals("You can't finish order if it is not in status in progres.",
                orderedItemService.finishOrderedItem(3));
    }
}
