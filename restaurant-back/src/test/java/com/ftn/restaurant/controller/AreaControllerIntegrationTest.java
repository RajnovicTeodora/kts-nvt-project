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
public class AreaControllerIntegrationTest {
    
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void addAreaTest(){
        
        ResponseEntity<AreaDTO> responseEntity = restTemplate.postForEntity("/api/area/addArea", "Garden", AreaDTO.class);
        AreaDTO area = responseEntity.getBody();

        Assert.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        Assert.assertEquals("Garden", area.getName());                     
    }
    
    @Test
    public void addAreaTestNull() {
    	ResponseEntity<AreaDTO> responseEntity = restTemplate.postForEntity("/api/area/addArea", "First", AreaDTO.class);
        AreaDTO area = responseEntity.getBody();

        Assert.assertNull(area.getName()); 
    }

    
}
