package com.ftn.restaurant.controller;

import com.ftn.restaurant.dto.OrderDTO;
import com.ftn.restaurant.dto.OrderItemDTO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static com.ftn.restaurant.constants.OrderDTOConstants.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
//@TestPropertySource("classpath:application-test.properties")
public class OrderedItemControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void acceptOrderedItemTest(){
        ResponseEntity<String> responseEntity = restTemplate
                .postForEntity("/api/orderedItem/acceptOrderedItem/1", 1,String.class);

        String message = responseEntity.getBody();

        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertEquals("You can't accept order if it is not in status ordered.", message);


        //////////////////
        responseEntity = restTemplate
                .postForEntity("/api/orderedItem/acceptOrderedItem/-1", -1,String.class);

         message = responseEntity.getBody();

        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertEquals("Order doesn't exists", message);
        ///////////////////

        responseEntity = restTemplate
                .postForEntity("/api/orderedItem/acceptOrderedItem/7", 7,String.class);

        message = responseEntity.getBody();

        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertEquals("You accepted order with id: 7", message);
    }

    @Test
    public void finishOrderedItemTest(){
        ResponseEntity<String> responseEntity = restTemplate
                .postForEntity("/api/orderedItem/finishOrderedItem/1", 1,String.class);

        String message = responseEntity.getBody();

        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertEquals("You finished order with id: 1", message);


        //////////////////
        responseEntity = restTemplate
                .postForEntity("/api/orderedItem/finishOrderedItem/-1", -1,String.class);

        message = responseEntity.getBody();

        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertEquals("Order doesn't exists", message);
        ///////////////////

        responseEntity = restTemplate
                .postForEntity("/api/orderedItem/finishOrderedItem/7", 7,String.class);

        message = responseEntity.getBody();

        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertEquals("You can't finish order if it is not in status in progres.", message);
    }

    @Test
    public void confirmPickupTest(){
        ResponseEntity<String> responseEntity = restTemplate
                .postForEntity("/api/orderedItem/confirmPickup/2", 2,String.class);

        String message = responseEntity.getBody();

        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertEquals("You delivered ordered item with id: 2", message);

        //////////////////
        responseEntity = restTemplate
                .postForEntity("/api/orderedItem/confirmPickup/-1", -1,String.class);

        message = responseEntity.getBody();

        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertEquals("Ordered item doesn't exists", message);
    }

    @Test
    public void deleteOrderedItemTest(){
        ResponseEntity<String> responseEntity = restTemplate
                .postForEntity("/api/orderedItem/setDeleted/7", 7,String.class);

        String message = responseEntity.getBody();

        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertEquals("You deleted ordered item with id: 7", message);

        //////////////////
        responseEntity = restTemplate
                .postForEntity("/api/orderedItem/setDeleted/-1", -1,String.class);

        message = responseEntity.getBody();

        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertEquals("Ordered item doesn't exists", message);

        //////////////////
        responseEntity = restTemplate
                .postForEntity("/api/orderedItem/setDeleted/8", 8,String.class);

        message = responseEntity.getBody();

        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertEquals("Already deleted ordered item with id: 8", message);

        //////////////////
        responseEntity = restTemplate
                .postForEntity("/api/orderedItem/setDeleted/6", 6,String.class);

        message = responseEntity.getBody();

        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertEquals("Can't delete ordered item with status!=ORDERED and id: 6", message);
    }

    @Test
    public void updateOrderedItemTest(){
        ResponseEntity<Object> responseEntity = restTemplate
                .postForEntity("/api/orderedItem/updateOrderedItem/8", ORDER_ITEM_DTO_1, Object.class);

        Assert.assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());

        //"Can't change order that is already paid."
        ////////////////////////////////////////////////////

        responseEntity = restTemplate
                .postForEntity("/api/orderedItem/updateOrderedItem/5", ORDER_ITEM_DTO_1, Object.class);

        Assert.assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());

        //"Can't change ordered item in preparation."
        ///////////////////////////////////////////////////

        responseEntity = restTemplate
                .postForEntity("/api/orderedItem/updateOrderedItem/9", ORDER_ITEM_DTO_1, Object.class);

        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        //Successful
    }

    @Test
    public void addOrderItemToOrderTest(){
        ResponseEntity<OrderItemDTO> responseEntity = restTemplate
                .postForEntity("/api/orderedItem/addOrderItemToOrder/7", ORDER_ITEM_DTO_1, OrderItemDTO.class);

        Assert.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

        //Successful
        ////////////////////////////////////////////////////

        responseEntity = restTemplate
                .postForEntity("/api/orderedItem/addOrderItemToOrder/6", ORDER_ITEM_DTO_1, OrderItemDTO.class);

        Assert.assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());

        //"Can't add order items to order that is already paid."
    }
}
