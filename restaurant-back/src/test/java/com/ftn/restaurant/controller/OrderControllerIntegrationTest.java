package com.ftn.restaurant.controller;

import com.ftn.restaurant.dto.OrderDTO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static com.ftn.restaurant.constants.OrderDTOConstants.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class OrderControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void createOrderTest(){
        ResponseEntity<OrderDTO> responseEntity = restTemplate
                .postForEntity("/api/order/createOrder", ORDER_DTO_1, OrderDTO.class);

        OrderDTO order = responseEntity.getBody();

        Assert.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        Assert.assertEquals(ORDER_DTO_1.getDate(), order.getDate());

        ////////////////////////////////////////"Order has to contain ordered items."
        responseEntity = restTemplate
                .postForEntity("/api/order/createOrder", ORDER_DTO_2, OrderDTO.class);

        Assert.assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
        Assert.assertEquals(ORDER_DTO_2.getOrderItems().size(), 0);
    }

    @Test
    public void updateOrderTest(){
        ResponseEntity<OrderDTO> responseEntity = restTemplate
                .postForEntity("/api/order/updateOrder/7", ORDER_DTO_1, OrderDTO.class);

        OrderDTO order = responseEntity.getBody();

        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertEquals(ORDER_DTO_1.getDate(), order.getDate());

        /////////////////////////////////////////"Can't change order that is already paid."
        responseEntity = restTemplate
                .postForEntity("/api/order/updateOrder/6", ORDER_DTO_1, OrderDTO.class);

        Assert.assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
    }

    @Test
    public void setTotalPriceAndPayTest(){
        ResponseEntity<String> responseEntity = restTemplate
                .getForEntity("/api/order/payOrder/7", String.class);

        String message = responseEntity.getBody();

        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertEquals("Successfully paid order with id: 7", message);

        ////////////////////////////////////////
        responseEntity = restTemplate
                .getForEntity("/api/order/payOrder/6", String.class);

        message = responseEntity.getBody();

        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertEquals("Order with id 6 is already paid.", message);
    }

}
