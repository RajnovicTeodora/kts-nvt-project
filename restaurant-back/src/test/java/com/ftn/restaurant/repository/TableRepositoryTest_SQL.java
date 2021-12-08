package com.ftn.restaurant.repository;

import com.ftn.restaurant.model.RestaurantTable;
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
public class TableRepositoryTest_SQL {

    @Autowired
    private TableRepository tableRepository;

    @Test
    public void findOneWithWaiterTest(){
        RestaurantTable rt = tableRepository.findOneWithWaiter(1L, "waiter");
        Assert.assertNotNull(rt);
        rt = tableRepository.findOneWithWaiter(1L, "ww");
        Assert.assertNull(rt);
        rt = tableRepository.findOneWithWaiter(1000L, "waiter");
        Assert.assertNull(rt);
    }
}
