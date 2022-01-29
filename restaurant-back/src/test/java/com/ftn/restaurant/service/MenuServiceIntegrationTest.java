package com.ftn.restaurant.service;


import com.ftn.restaurant.dto.CurrentMenuItemPriceDTO;
import com.ftn.restaurant.exception.ForbiddenException;
import com.ftn.restaurant.exception.MenuItemNotFoundException;


import com.ftn.restaurant.model.MenuItem;
import com.ftn.restaurant.model.MenuItemPrice;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static com.ftn.restaurant.constants.DateTimeConstants.*;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class MenuServiceIntegrationTest {

    @Autowired
    private MenuService menuService;

    @Test(expected = ForbiddenException.class)
    public void testToggleIsMenuItemActiveShouldReturnForbiddenExceptionWhenNoPrice(){
        MenuItemPrice menuItemPrice = menuService.toggleIsMenuItemActive(2L);
    }

    @Test
    public void testToggleIsMenuItemActiveTrueToFalseShouldReturnMenuItemPrice(){
        MenuItemPrice menuItemPrice = menuService.toggleIsMenuItemActive(4);
       assertFalse(menuItemPrice.isActive());
    }

    @Test
    public void testToggleIsMenuItemActiveFalseToTrueShouldReturnMenuItemPrice(){
        MenuItemPrice menuItemPrice = menuService.toggleIsMenuItemActive(1);
        assertTrue(menuItemPrice.isActive());

    }

    @Test
    public void getActiveMenuItem(){
        List<CurrentMenuItemPriceDTO> currentMenuItemPriceDTOS = menuService.getActiveMenuItem("");
        assertEquals(5, currentMenuItemPriceDTOS.size());

        currentMenuItemPriceDTOS = menuService.getActiveMenuItem("Ice");
        assertEquals(1, currentMenuItemPriceDTOS.size());

        currentMenuItemPriceDTOS = menuService.getActiveMenuItem("random");
        assertEquals(0, currentMenuItemPriceDTOS.size());
    }

    @Test 
    public void searchMenuItemsTest() {
    	Assert.assertEquals(3, menuService.searchMenuItems("...", "...").size());
    	Assert.assertEquals(2, menuService.searchMenuItems("dish", "...").size());
    	Assert.assertEquals(1, menuService.searchMenuItems("drink", "...").size());
    	Assert.assertEquals(2, menuService.searchMenuItems("...", "a").size());
    	Assert.assertEquals(0, menuService.searchMenuItems("...", "xxx").size());
    }
    
    @Test(expected = MenuItemNotFoundException.class)
    public void searchMenuItemsTest_MenuItemNotFoundException() {
    	menuService.searchMenuItems("sdf", "...");
    }


    @Test(expected = ForbiddenException.class)
    public void testDeleteMenuItemShouldReturnForbiddenExceptionWhenItemNotPresent(){
        MenuItem menuItem = menuService.deleteMenuItem(34L);
    }

    @Test
    public void testDeleteMenuItemShouldReturnMenuItem(){
        MenuItem menuItem = menuService.deleteMenuItem(8L);
        assertTrue(menuItem.isDeleted());

    }

}
