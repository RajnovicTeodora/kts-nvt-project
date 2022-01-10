package com.ftn.restaurant.repository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

    @Test
    public void findByAreaIdTest(){
        List<RestaurantTable> areaTables = tableRepository.findByAreaId(1L);
        Assert.assertEquals(3, areaTables.size());
        
        List<RestaurantTable> areaTables2 = tableRepository.findByAreaId(100L);
        Assert.assertEquals(0, areaTables2.size());
    }
    
    @Test
    public void findByPositionXAndPositionYTest() {
    	Optional<RestaurantTable> optionalTable1 = tableRepository.findByPositionXAndPositionY(5, 5);
    	Assert.assertTrue(optionalTable1.isPresent());
    	assertEquals(1L, optionalTable1.get().getId());
    	
    	Optional<RestaurantTable> optionalTable2 = tableRepository.findByPositionXAndPositionY(100, 100);
    	Assert.assertFalse(optionalTable2.isPresent());
    }
}
