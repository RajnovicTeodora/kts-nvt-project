package com.ftn.restaurant.service;

import com.ftn.restaurant.constants.NewTableDTOConstants;

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
public class TableServiceIntegrationTest {

    @Autowired
    private TableService tableService;

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
    public void addTableTest() {
        Assert.assertEquals(20, tableService.addTable(NewTableDTOConstants.NEW_TABLE_1).getPositionX());
        Assert.assertNull(tableService.addTable(NewTableDTOConstants.NEW_TABLE_1));
    }

    @Test
    public void deleteTableTest() {
        Assert.assertEquals(5, tableService.deleteTable(1L).getPositionX());
        Assert.assertNull(tableService.deleteTable(88L));
    }
    
}
