package com.ftn.restaurant.service;

import com.ftn.restaurant.exception.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static com.ftn.restaurant.constants.OrderDTOConstants.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class OrderedItemIntegrationTest {

    @Autowired
    private OrderedItemService orderedItemService;

    @Test
    public void acceptOrderedItemTest(){
//        Assert.assertEquals("You accepted order with id: 7",orderedItemService.acceptOrderedItem(7, username));
//        Assert.assertEquals("Order doesn't exists",orderedItemService.acceptOrderedItem(10000000, username));
//        Assert.assertEquals("Order doesn't exists",orderedItemService.acceptOrderedItem(-10, username));
//        Assert.assertEquals("You can't accept order if it is not in status ordered.",
//                orderedItemService.acceptOrderedItem(2, username));
//        Assert.assertEquals("You can't accept order if it is not in status ordered.",
//                orderedItemService.acceptOrderedItem(3, username));
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

    @Test
    public void confirmPickupTest(){
        Assert.assertEquals("Successfully delivered ordered item with id: 2",orderedItemService.confirmPickup(2));
        //"Couldn't find ordered item."
        Assertions.assertThrows(NotFoundException.class, () -> {orderedItemService.confirmPickup(-1);});
        //"Can't deliver ordered item when status is not READY."
        Assertions.assertThrows(ForbiddenException.class, () -> {orderedItemService.confirmPickup(3);});
        //"Can't deliver DELETED ordered item."
        Assertions.assertThrows(BadRequestException.class, () -> {orderedItemService.confirmPickup(6);});
    }

    @Test
    public void updateOrderedItemTest(){
        //ok
        Assert.assertNotNull(orderedItemService.updateOrderedItem(ORDER_ITEM_DTO_1));
        //"Can't update deleted ordered item with id: 8"
        Assertions.assertThrows(BadRequestException.class, () -> {orderedItemService.updateOrderedItem( ORDER_ITEM_DTO_4);});
        //"Can't change ordered item in preparation."
        Assertions.assertThrows(ForbiddenException.class, () -> {orderedItemService.updateOrderedItem( ORDER_ITEM_DTO_3);});
        //"Couldn't find ingredient with id: 10000"
        Assertions.assertThrows(IngredientNotFoundException.class, () -> {orderedItemService.updateOrderedItem( ORDER_ITEM_DTO_5);});
        //"Couldn't find ordered item."
        Assertions.assertThrows(NotFoundException.class, () -> {orderedItemService.updateOrderedItem( ORDER_ITEM_DTO_6);});
    }

    @Test
    public void deleteOrderedItemTest(){
        Assert.assertEquals("Successfully deleted ordered item with id: 7",orderedItemService.deleteOrderedItem(7));
        //"Couldn't find ordered item."
        Assertions.assertThrows(NotFoundException.class, () -> {orderedItemService.deleteOrderedItem(-1);});
        //"Already deleted ordered item with id: 6"
        Assertions.assertThrows(BadRequestException.class, () -> {orderedItemService.deleteOrderedItem(6);});
        //"Can't delete ordered item with id: 4"
        Assertions.assertThrows(ForbiddenException.class, () -> {orderedItemService.deleteOrderedItem(4);});
    }

    @Test
    public void addOrderItemToOrderTest(){
        //ok
        Assert.assertEquals("Successfully added new ordered item to order id: 4",orderedItemService.addOrderItemToOrder(4,ORDER_ITEM_DTO_1));
        //"Couldn't find order."
        Assertions.assertThrows(NotFoundException.class, () -> {orderedItemService.addOrderItemToOrder(-1, ORDER_ITEM_DTO_1);});
        //"Can't add order items to order that is already paid."
        Assertions.assertThrows(OrderAlreadyPaidException.class, () -> {orderedItemService.addOrderItemToOrder(5, ORDER_ITEM_DTO_1);});
        //"Couldn't find ingredient with id: -1"
        Assertions.assertThrows(IngredientNotFoundException.class, () -> {orderedItemService.addOrderItemToOrder( 4, ORDER_ITEM_DTO_5);});
    }

    @Test
    public void getOrderedItemsForOrderIdTest(){
        //"Couldn't find order."
        Assertions.assertThrows(NotFoundException.class, () -> {orderedItemService.getOrderedItemsForOrderId(-1);});
        //ok
        Assert.assertEquals(1,orderedItemService.getOrderedItemsForOrderId(6).size());

    }

}
