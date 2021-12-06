package com.ftn.restaurant.controller;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

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


}
