package com.ftn.restaurant.service;

import com.ftn.restaurant.repository.OrderedItemRepository;
import com.ftn.restaurant.repository.TableRepository;
import com.ftn.restaurant.repository.TableRepositoryTest_SQL;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
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

    @Autowired
    private TableRepository tableRepository;

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
}
