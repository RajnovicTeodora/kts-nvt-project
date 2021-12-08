package com.ftn.restaurant.service;

import static com.ftn.restaurant.constants.MenuItemConstants.*;

import com.ftn.restaurant.model.MenuItem;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class MenuItemServiceIntegrationTest {

    @Autowired
    private MenuItemService menuItemService;

    @Test
    public void testDeleteMenuItem(){
        MenuItem deleted = menuItemService.deleteMenuItem(DELETE_ITEM_ID);
        assertTrue(deleted.isDeleted());
    }
}
