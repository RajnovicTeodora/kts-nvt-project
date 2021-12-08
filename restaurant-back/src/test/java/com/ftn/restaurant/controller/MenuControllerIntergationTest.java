package com.ftn.restaurant.controller;

import java.util.List;
import java.util.Map;

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

import com.ftn.restaurant.dto.AreaDTO;
import com.ftn.restaurant.dto.MenuItemPriceDTO;
import com.ftn.restaurant.model.Area;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class MenuControllerIntergationTest {
    
    @Autowired
    private TestRestTemplate restTemplate;

    
    @Test
    public void searchMenuItemsTest() {
    	ResponseEntity<List> responseEntity = restTemplate
                .getForEntity("/api/menu/searchMenuItems/.../...", List.class);
    	
    	List<MenuItemPriceDTO> items = responseEntity.getBody();
    	
    	Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    	Assert.assertEquals(3,  items.size());
    	
    	//----------------------------------//
    	responseEntity = restTemplate
                .getForEntity("/api/menu/searchMenuItems/drink/...", List.class);
    	
    	items = responseEntity.getBody();
    	
    	Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    	Assert.assertEquals(1,  items.size());
    	
    	//----------------------------------//
    	responseEntity = restTemplate
                .getForEntity("/api/menu/searchMenuItems/dish/...", List.class);
    	
    	items = responseEntity.getBody();
    	
    	Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    	Assert.assertEquals(2,  items.size());
    	
    	//----------------------------------//
    	responseEntity = restTemplate
                .getForEntity("/api/menu/searchMenuItems/COLD_DRINK/...", List.class);
    	
    	items = responseEntity.getBody();
    	
    	Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    	Assert.assertEquals(1,  items.size());

    	//----------------------------------//
    	responseEntity = restTemplate
                .getForEntity("/api/menu/searchMenuItems/MAIN_DISH/...", List.class);
    	
    	items = responseEntity.getBody();
    	
    	Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    	Assert.assertEquals(2,  items.size());
    	
    	//----------------------------------//
    	responseEntity = restTemplate
                .getForEntity("/api/menu/searchMenuItems/.../Sp", List.class);
    	
    	items = responseEntity.getBody();
    	
    	Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    	Assert.assertEquals(2,  items.size());
    	
    }
    
    

}

