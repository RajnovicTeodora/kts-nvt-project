package com.ftn.restaurant.service;

import static com.ftn.restaurant.constants.MenuConstants.*;
import com.ftn.restaurant.dto.SelectedMenuItemsDTO;
import com.ftn.restaurant.exception.ForbiddenException;
import com.ftn.restaurant.exception.MenuItemNotFoundException;
import com.ftn.restaurant.model.MenuItemPrice;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class MenuServiceIntegrationTest {

    @Autowired
    private MenuService menuService;

    @Test
    public void testDefineActiveMenuItem(){
        SelectedMenuItemsDTO selectedMenuItemsDTO = new SelectedMenuItemsDTO(ITEM_ID_LIST);
        //List<MenuItemPrice> menuItemPriceList = menuService.defineActiveMenuItem(selectedMenuItemsDTO);
        //assertEquals(3, menuItemPriceList.size());
        
    }
    
    @Test 
    public void searchMenuItemsTest() {
    	Assert.assertEquals(2, menuService.searchMenuItems("...", "...").size());
    	Assert.assertEquals(1, menuService.searchMenuItems("dish", "...").size());
    	Assert.assertEquals(1, menuService.searchMenuItems("drink", "...").size());
    	Assert.assertEquals(2, menuService.searchMenuItems("...", "a").size());
    	Assert.assertEquals(0, menuService.searchMenuItems("...", "xxx").size());
    }
    
    @Test(expected = MenuItemNotFoundException.class)
    public void searchMenuItemsTest_MenuItemNotFoundException() {
    	menuService.searchMenuItems("sdf", "...");
    }

    @Test(expected = ForbiddenException.class)
    public void testDefineActiveMenuItemAndExpectForbiddenException(){
        SelectedMenuItemsDTO selectedMenuItemsDTO = new SelectedMenuItemsDTO(INVALID_ITEM_ID_LIST);
        //menuService.defineActiveMenuItem(selectedMenuItemsDTO);
    }

}
