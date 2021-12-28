package com.ftn.restaurant.service;

import com.ftn.restaurant.dto.OrderDTO;
import com.ftn.restaurant.model.Order;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static com.ftn.restaurant.constants.OrderDTOConstants.ORDER_DTO_1;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class OrderServiceIntegrationTest {

    @Autowired
    private OrderService orderService;

    @Test
    public void createOrderTest(){
        OrderDTO orderDTO = ORDER_DTO_1;
        Order order = orderService.createOrder(orderDTO);

        assertEquals(orderDTO.getDate(), order.getDate().toString());
        assertEquals(orderDTO.getNote(), order.getNote());
    }

    @Test
    public void updateOrderTest(){
        OrderDTO orderDTO = ORDER_DTO_1;
        Order order = orderService.updateOrder(4, orderDTO);

        assertEquals(orderDTO.getDate(), order.getDate().toString());
        assertEquals(orderDTO.getNote(), order.getNote());
    }

    @Test
    public void setTotalPriceAndPayTest(){
        Assert.assertEquals("Successfully paid order with id: 3",orderService.setTotalPriceAndPay(3));
        Assert.assertEquals("Order with id 5 is already paid.",orderService.setTotalPriceAndPay(5));
        Assert.assertEquals("Couldn't find order with id: 1000",orderService.setTotalPriceAndPay(1000));
    }
}
