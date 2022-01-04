package com.ftn.restaurant.service;

import com.ftn.restaurant.dto.RestaurantTableDTO;
import com.ftn.restaurant.exception.AreaNotFoundException;
import com.ftn.restaurant.exception.TableNotFoundException;
import com.ftn.restaurant.exception.TableOccupiedException;
import com.ftn.restaurant.model.Area;
import com.ftn.restaurant.model.RestaurantTable;
import com.ftn.restaurant.repository.AreaRepository;
import com.ftn.restaurant.repository.TableRepository;

import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class TableServiceUnitTest {

    @Autowired
    private TableService tableService;

    @MockBean
    private TableRepository tableRepository;
    
    @MockBean
    private AreaRepository areaRepository;

    @BeforeAll
    public void setup() {

    }

    @Test
    public void occupyTableTest(){
        Assert.assertEquals("Successfully occupied table with id: 1",tableService.occupyTable("waiter", 1));
        Assert.assertEquals("Table is already occupied",tableService.occupyTable("waiter", 2));
        Assert.assertEquals("Couldn't find table with id: 1000",tableService.occupyTable("waiter", 1000));
    }

    @Test
    public void clearTableTest(){
        Assert.assertEquals("Successfully cleared table with id: 5",tableService.clearTable("waiter", 5));
        Assert.assertEquals("Table is already cleared",tableService.clearTable("waiter", 4));
        Assert.assertEquals("Couldn't find table with id: 1000",tableService.clearTable("waiter", 1000));
    }

    @Test
    public void claimTableTest(){
        Assert.assertEquals("Successfully claimed table with id: 3",tableService.claimTable("waiter", 3));
        Assert.assertEquals("Table is already claimed",tableService.claimTable("waiter", 2));
        Assert.assertEquals("Couldn't find table with id: 1000",tableService.claimTable("waiter", 1000));
    }

    @Test
    public void leaveTableTest(){
        Assert.assertEquals("Successfully left table with id: 6",tableService.leaveTable("waiter", 6));
        Assert.assertEquals("Can't leave table while its occupied",tableService.leaveTable("waiter", 2));
        Assert.assertEquals("Couldn't find table with id: 1000",tableService.leaveTable("waiter", 1000));
    }
    
    @Test
    public void addTableTest_Success() {
    	Area area = new Area("First floor");
    	area.setId(1L);
    	Mockito.when(tableRepository.findByPositionXAndPositionY(33, 33)).thenReturn(Optional.empty());
    	Mockito.when(areaRepository.findById(1L)).thenReturn(Optional.of(area));
    	Assert.assertEquals("First floor", tableService.addTable(new RestaurantTableDTO(33, 33, 1L)).getArea().getName());
    }
    
    @Test(expected = AreaNotFoundException.class )
    public void addTableTest_Area_Not_Found_Exception() {
    	Mockito.when(areaRepository.findById(2L)).thenReturn(Optional.empty());
    	tableService.addTable(new RestaurantTableDTO(33, 33, 1L));
    }
    
    @Test
    public void deleteTable_Success() throws Exception {
    	RestaurantTable table = new RestaurantTable(12, 12, new Area("abc"));
    	Mockito.when(tableRepository.findById(1L)).thenReturn(Optional.of(table));
    	tableService.deleteTable(1L);
    }
    
    @Test(expected = TableNotFoundException.class )
    public void deleteTable_TableNotFoundException() throws Exception {
    	Mockito.when(tableRepository.findById(1L)).thenReturn(Optional.empty());
    	tableService.deleteTable(1L);
    }
    
    @Test(expected = TableOccupiedException.class )
    public void deleteTable_TableOccupiedException() throws Exception {
    	RestaurantTable table = new RestaurantTable(12, 12, new Area("abc"));
    	table.setOccupied(true);
    	Mockito.when(tableRepository.findById(1L)).thenReturn(Optional.of(table));
    	tableService.deleteTable(1L);
    }
    
    
}
