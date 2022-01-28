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
public class OrderServiceIntegrationTest {

    @Autowired
    private OrderService orderService;

    @Test
    public void createOrderTest(){
        //Ok
        Assert.assertTrue(orderService.createOrder(ORDER_DTO_1) >= 1L);
        //"Order has to contain ordered items."
        Assertions.assertThrows(ForbiddenException.class, () -> {orderService.createOrder( ORDER_DTO_2);});
        //"Couldn't find menu item."
        Assertions.assertThrows(MenuItemNotFoundException.class, () -> {orderService.createOrder( ORDER_DTO_3);});
        //"Couldn't find ingredient."
        Assertions.assertThrows(IngredientNotFoundException.class, () -> {orderService.createOrder( ORDER_DTO_4);});
    }

    @Test
    public void updateOrderTest(){
        //"Couldn't find order."
        Assertions.assertThrows(NotFoundException.class, () -> {orderService.updateOrder( ORDER_DTO_6);});
        //"Can't change order that is already paid."
        Assertions.assertThrows(OrderAlreadyPaidException.class, () -> {orderService.updateOrder( ORDER_DTO_7);});
        //ok
        Assertions.assertEquals( "Successfully updated order with order number: 3", orderService.updateOrder( ORDER_DTO_10));
    }

    @Test
    public void setTotalPriceAndPayTest(){
        Assert.assertEquals("Successfully paid order with id: 7",orderService.setTotalPriceAndPay(7));
        Assertions.assertThrows(ForbiddenException.class, () -> {orderService.setTotalPriceAndPay(5);});
        Assertions.assertThrows(NotFoundException.class, () -> {orderService.setTotalPriceAndPay(1000);});
    }

    @Test
    public void checkIfOrderIsPaidTest(){
        Assert.assertTrue(orderService.checkIfOrderIsPaid(5));
        Assert.assertFalse(orderService.checkIfOrderIsPaid(4));
        Assertions.assertThrows(NotFoundException.class, () -> {orderService.checkIfOrderIsPaid(-1);});
    }

    @Test
    public void getActiveOrdersForTableTest(){
        Assert.assertFalse(orderService.getActiveOrdersForTable(2L, "waiter").isEmpty());
        Assertions.assertThrows(NotFoundException.class, () -> {orderService.getActiveOrdersForTable(-1, "waiter");});
    }

    @Test
    public void getOrderTest(){
        Assert.assertEquals("xxxx", orderService.getOrder(7).getNote());
        Assertions.assertThrows(NotFoundException.class, () -> {orderService.getOrder(-1);});
    }

}
