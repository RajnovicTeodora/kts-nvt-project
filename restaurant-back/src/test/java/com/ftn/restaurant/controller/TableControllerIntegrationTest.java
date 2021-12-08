package com.ftn.restaurant.controller;

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

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class TableControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void occupyTableTest(){
        ResponseEntity<String> responseEntity = restTemplate
                .getForEntity("/api/table/1/occupyTable/waiter", String.class);

        String message = responseEntity.getBody();

        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertEquals("Successfully occupied table with id: 1", message);

        //////////////////////////////
        responseEntity = restTemplate
                .getForEntity("/api/table/2/occupyTable/waiter", String.class);

        message = responseEntity.getBody();

        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertEquals("Table is already occupied", message);

        //////////////////////////////
        responseEntity = restTemplate
                .getForEntity("/api/table/3/occupyTable/waiter", String.class);

        message = responseEntity.getBody();

        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertEquals("Couldn't find table with id: 3", message);
    }

    @Test
    public void clearTableTest(){
        ResponseEntity<String> responseEntity = restTemplate
                .getForEntity("/api/table/2/clearTable/waiter", String.class);

        String message = responseEntity.getBody();

        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertEquals("Successfully cleared table with id: 2", message);

        //////////////////////////////
        responseEntity = restTemplate
                .getForEntity("/api/table/1/clearTable/waiter", String.class);

        message = responseEntity.getBody();

        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertEquals("Table is already cleared", message);

        //////////////////////////////
        responseEntity = restTemplate
                .getForEntity("/api/table/3/clearTable/waiter", String.class);

        message = responseEntity.getBody();

        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertEquals("Couldn't find table with id: 3", message);
    }

    @Test
    public void claimTableTest(){
        ResponseEntity<String> responseEntity = restTemplate
                .getForEntity("/api/table/3/claimTable/waiter", String.class);

        String message = responseEntity.getBody();

        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertEquals("Successfully claimed table with id: 3", message);

        //////////////////////////////
        responseEntity = restTemplate
                .getForEntity("/api/table/1/claimTable/waiter", String.class);

        message = responseEntity.getBody();

        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertEquals("Table is already claimed", message);

        //////////////////////////////
        responseEntity = restTemplate
                .getForEntity("/api/table/1000/claimTable/waiter", String.class);

        message = responseEntity.getBody();

        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertEquals("Couldn't find table with id: 1000", message);
    }

    @Test
    public void leaveTableTest(){
        ResponseEntity<String> responseEntity = restTemplate
                .getForEntity("/api/table/1/leaveTable/waiter", String.class);

        String message = responseEntity.getBody();

        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertEquals("Successfully left table with id: 1", message);

        //////////////////////////////
        responseEntity = restTemplate
                .getForEntity("/api/table/2/leaveTable/waiter", String.class);

        message = responseEntity.getBody();

        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertEquals("Can't leave table while its occupied", message);

        //////////////////////////////
        responseEntity = restTemplate
                .getForEntity("/api/table/1000/leaveTable/waiter", String.class);

        message = responseEntity.getBody();

        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertEquals("Couldn't find table with id: 1000", message);
    }

}
