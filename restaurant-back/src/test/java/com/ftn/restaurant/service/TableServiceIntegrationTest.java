package com.ftn.restaurant.service;

import com.ftn.restaurant.constants.NewTableDTOConstants;
import com.ftn.restaurant.dto.RestaurantTableDTO;
import com.ftn.restaurant.exception.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class TableServiceIntegrationTest {

    @Autowired
    private TableService tableService;

    @Test
    public void occupyTableTest(){
        Assert.assertEquals("Successfully occupied table with table id: 1",tableService.occupyTable("waiter", 1L));
        Assertions.assertThrows(ForbiddenException.class, () -> {tableService.occupyTable("waiter", 2L);});
        Assertions.assertThrows(NotFoundException.class, () -> {tableService.occupyTable("waiter", 1000L);});
    }

    @Test
    public void clearTableTest(){
        Assert.assertEquals("Successfully cleared table with table id: 5",tableService.clearTable("waiter", 5L));
        Assertions.assertThrows(ForbiddenException.class, () -> {tableService.clearTable("waiter", 4L);});
        Assertions.assertThrows(NotFoundException.class, () -> {tableService.clearTable("waiter", 1000L);});
        Assertions.assertThrows(ActiveOrdersPresentException.class, () -> {tableService.clearTable("waiter", 2L);});
    }

    @Test
    public void claimTableTest(){
        Assert.assertEquals("Successfully claimed table with table id: 3",tableService.claimTable("waiter", 3L));
        Assertions.assertThrows(ForbiddenException.class, () -> {tableService.claimTable("waiter", 2L);});
        Assertions.assertThrows(NotFoundException.class, () -> {tableService.claimTable("waiter", 1000L);});
    }

    @Test
    public void leaveTableTest(){
        Assert.assertEquals("Successfully left table with table id: 6",tableService.leaveTable("waiter", 6L));
        Assertions.assertThrows(ForbiddenException.class, () -> {tableService.leaveTable("waiter", 2L);});
        Assertions.assertThrows(NotFoundException.class, () -> {tableService.leaveTable("waiter", 1000L);});
    }

    @Test
    public void getTableByTableIdTest(){
        Assert.assertFalse(tableService.getTableByTableId(6L).isOccupied());
        Assert.assertEquals(tableService.getTableByTableId(6L).getClass(), RestaurantTableDTO.class);
        Assertions.assertThrows(NotFoundException.class, () -> {tableService.leaveTable("waiter", 1000L);});
    }

    @Test
    public void addTableTest() {
        Assert.assertEquals(20, tableService.addTable(NewTableDTOConstants.NEW_TABLE_1).getPositionX());
    }
    
    @Test(expected = AreaNotFoundException.class )
    public void addTableTest_Area_Not_Found_Exception() {
    	tableService.addTable(new RestaurantTableDTO(33, 33, 123L));
    }
    
    @Test
    public void deleteTable_Success() throws Exception {
    	Assert.assertEquals(8, tableService.deleteTable(3L).getPositionX());
        
    }
    
    @Test(expected = TableNotFoundException.class )
    public void deleteTable_TableNotFoundException() throws Exception {
    	tableService.deleteTable(112L);
    }
    
    @Test(expected = TableOccupiedException.class )
    public void deleteTable_TableOccupiedException() throws Exception {
    	tableService.deleteTable(2L);
    }

    
}
