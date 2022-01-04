package com.ftn.restaurant.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.ftn.restaurant.dto.AreaDTO;
import com.ftn.restaurant.exception.AreaAlreadyExistsException;
import com.ftn.restaurant.exception.AreaNotFoundException;
import com.ftn.restaurant.exception.TableOccupiedException;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class AreaServiceIntegrationTest {

    @Autowired
    private AreaService areaService;

    @Test
    public void addAreaTest() throws AreaAlreadyExistsException{
        Assert.assertEquals("Roof", areaService.addArea("Roof").getName());
        Assert.assertThrows(AreaAlreadyExistsException.class,() -> {areaService.addArea("First floor");});
    }

    @Test
    public void deleteAreaTest() throws Exception{
        Assert.assertEquals("Second floor", areaService.deleteArea(2L).getName());
        Assert.assertThrows(AreaNotFoundException.class,() -> {areaService.deleteArea(123L);});
        Assert.assertThrows(TableOccupiedException.class,() -> {areaService.deleteArea(1L);});
    }
    
    @Test
    public void getAllAreasTest() throws Exception {
    	Assert.assertEquals(3,  areaService.getAllAreas().size());
    }
    
    @Test
    public void findByIdTest() throws Exception {
    	Assert.assertEquals("First floor", areaService.findByID(1L).getName());
    	Assert.assertThrows(AreaNotFoundException.class,() -> {areaService.findByID(100L);});
    }
    
}
