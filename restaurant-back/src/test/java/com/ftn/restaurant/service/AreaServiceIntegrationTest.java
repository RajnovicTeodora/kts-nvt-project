package com.ftn.restaurant.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class AreaServiceIntegrationTest {

    @Autowired
    private AreaService areaService;

    @Test
    public void addAreaTest(){
        Assert.assertEquals("First floor", areaService.addArea("First floor").getName());
        Assert.assertNull(areaService.addArea("First"));
    }

    @Test
    public void deleteAreaTest(){
        Assert.assertNull(areaService.deleteArea(100L));
        Assert.assertEquals("Second", areaService.deleteArea(2L).getName());
    }
    
}
