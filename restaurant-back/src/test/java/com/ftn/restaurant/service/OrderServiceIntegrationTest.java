package com.ftn.restaurant.service;

import com.ftn.restaurant.exception.ForbiddenException;
import com.ftn.restaurant.exception.NotFoundException;
import com.ftn.restaurant.model.Order;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static com.ftn.restaurant.constants.OrderDTOConstants.ORDER_DTO_1;
import static com.ftn.restaurant.constants.OrderDTOConstants.ORDER_DTO_2;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class OrderServiceIntegrationTest {

    @Autowired
    private OrderService orderService;

    @Test
    public void createOrderTest(){
        Order order = orderService.createOrder(ORDER_DTO_1);
        //Ok
        assertEquals(ORDER_DTO_1.getDate(), order.getDate().toString());
        assertEquals(ORDER_DTO_1.getNote(), order.getNote());
        //"Order has to contain ordered items."
        Assertions.assertThrows(ForbiddenException.class, () -> {orderService.createOrder( ORDER_DTO_2);});
    }

    @Test
    public void updateOrderTest(){
        Order order = orderService.updateOrder(4, ORDER_DTO_1);
        //Ok
        assertEquals(ORDER_DTO_1.getDate(), order.getDate().toString());
        assertEquals(ORDER_DTO_1.getNote(), order.getNote());
        //"Can't change order that is already paid."
        Assertions.assertThrows(ForbiddenException.class, () -> {orderService.updateOrder(5, ORDER_DTO_1);});
    }

    @Test
    public void setTotalPriceAndPayTest(){
        Assert.assertEquals("Successfully paid order with id: 3",orderService.setTotalPriceAndPay(3));
        Assertions.assertThrows(ForbiddenException.class, () -> {orderService.setTotalPriceAndPay(5);});
        Assertions.assertThrows(NotFoundException.class, () -> {orderService.setTotalPriceAndPay(1000);});
    }
}
