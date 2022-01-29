package com.ftn.restaurant.service;

import static com.ftn.restaurant.constants.MenuItemPriceConstants.*;
import static org.junit.Assert.*;

import com.ftn.restaurant.dto.UpdateMenuItemPriceDTO;
import com.ftn.restaurant.exception.ForbiddenException;
import com.ftn.restaurant.exception.MenuItemNotFoundException;
import com.ftn.restaurant.model.MenuItemPrice;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class MenuItemPriceServiceIntegrationTest {

    @Autowired
    private MenuItemPriceService menuItemPriceService;

    @Test
    public void  testFindLastPriceShouldReturnPriceWhenPriceExisting(){
        MenuItemPrice menuItemPrice = menuItemPriceService.findLastPrice(4);
        assertNotNull(menuItemPrice);
    }

    @Test
    public void  testFindLastPriceShouldReturnNullWhenPriceNonExisting(){
        MenuItemPrice menuItemPrice = menuItemPriceService.findLastPrice(7);
        assertNull(menuItemPrice);
    }

    @Test(expected = ForbiddenException.class)
    public void testUpdateMenuItemPriceAndExpectForbiddenExceptionWhenPriceIsInvalid(){
        UpdateMenuItemPriceDTO menuItemPriceDTO = new UpdateMenuItemPriceDTO(UPDATE_ID, -1, UPDATE_PURCHASE_PRICE);
        menuItemPriceService.updateMenuItemPrice(menuItemPriceDTO);
    }

    @Test(expected = MenuItemNotFoundException.class)
    public void testUpdateMenuItemPriceAndMenuItemNotFoundExceptionWhenMenuItemIsNotPresent(){
        UpdateMenuItemPriceDTO menuItemPriceDTO = new UpdateMenuItemPriceDTO(NON_EXISTENT_MENU_ITEM_ID, UPDATE_PRICE, UPDATE_PURCHASE_PRICE);
        menuItemPriceService.updateMenuItemPrice(menuItemPriceDTO);
    }

    @Test
    public void testUpdateMenuItemPriceWhenPriceExistent(){
        MenuItemPrice oldMenuItemPrice = menuItemPriceService.findLastPrice(UPDATE_ID);
        UpdateMenuItemPriceDTO menuItemPriceDTO = new UpdateMenuItemPriceDTO(UPDATE_ID, UPDATE_PRICE, UPDATE_PURCHASE_PRICE);
        MenuItemPrice updated = menuItemPriceService.updateMenuItemPrice(menuItemPriceDTO);

        assertNotEquals(oldMenuItemPrice.getId(), updated.getId()); // updated id = +1
        assertNotEquals(oldMenuItemPrice.getPrice(), updated.getPrice());
        assertNotEquals(oldMenuItemPrice.getPurchasePrice(), updated.getPurchasePrice());

        assertEquals(UPDATE_PRICE, updated.getPrice(), 0);
        assertEquals(UPDATE_PURCHASE_PRICE, updated.getPurchasePrice(), 0);
    }

    @Test
    public void testUpdateMenuItemPriceWhenPriceNonExistent(){
        MenuItemPrice oldMenuItemPrice = menuItemPriceService.findLastPrice(2);
        UpdateMenuItemPriceDTO menuItemPriceDTO = new UpdateMenuItemPriceDTO(2, UPDATE_PRICE, UPDATE_PURCHASE_PRICE);
        MenuItemPrice updated = menuItemPriceService.updateMenuItemPrice(menuItemPriceDTO);

        assertNull(oldMenuItemPrice); // No price found
        assertNotNull(updated);
        assertEquals(UPDATE_PRICE, updated.getPrice(), 0);
        assertEquals(UPDATE_PURCHASE_PRICE, updated.getPurchasePrice(), 0);

    }

    //IF THIS TESTS FAILS IT'S BECAUSE THE PRICE DATE FROM NEEDS TO BE TODAY
    @Test
    public void testUpdateMenuItemPriceWhenPriceNotChangedToday(){
        MenuItemPrice oldMenuItemPrice = menuItemPriceService.findLastPrice(8);
        UpdateMenuItemPriceDTO menuItemPriceDTO = new UpdateMenuItemPriceDTO(8, UPDATE_PRICE, UPDATE_PURCHASE_PRICE);
        MenuItemPrice updated = menuItemPriceService.updateMenuItemPrice(menuItemPriceDTO);
        int newPrices = updated.getItem().getPriceList().size();

        assertEquals(1, newPrices);
        assertEquals(oldMenuItemPrice.getId(), updated.getId()); // updated id = old id
        assertNotEquals(oldMenuItemPrice.getPrice(), updated.getPrice());
        assertNotEquals(oldMenuItemPrice.getPurchasePrice(), updated.getPurchasePrice());

        assertEquals(UPDATE_PRICE, updated.getPrice(), 0);
        assertEquals(UPDATE_PURCHASE_PRICE, updated.getPurchasePrice(), 0);
    }

}
