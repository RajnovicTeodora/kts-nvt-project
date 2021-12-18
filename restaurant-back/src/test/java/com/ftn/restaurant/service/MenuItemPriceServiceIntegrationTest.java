package com.ftn.restaurant.service;

import static com.ftn.restaurant.constants.MenuItemPriceConstants.*;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class MenuItemPriceServiceIntegrationTest {

    @Autowired
    private MenuItemPriceService menuItemPriceService;

    // TODO t
    @Test(expected = ForbiddenException.class)
    public void testUpdateMenuItemPriceAndExpectForbiddenExceptionWhenPriceIsInvalid(){
        UpdateMenuItemPriceDTO menuItemPriceDTO = new UpdateMenuItemPriceDTO(UPDATE_ID, -1, UPDATE_PURCHASE_PRICE);
        menuItemPriceService.updateMenuItemPrice(menuItemPriceDTO);
    }

    // TODO t
    @Test(expected = MenuItemNotFoundException.class)
    public void testUpdateMenuItemPriceAndMenuItemNotFoundExceptionWhenMenuItemIsNotPresent(){
        UpdateMenuItemPriceDTO menuItemPriceDTO = new UpdateMenuItemPriceDTO(NON_EXISTENT_MENU_ITEM_ID, UPDATE_PRICE, UPDATE_PURCHASE_PRICE);
        menuItemPriceService.updateMenuItemPrice(menuItemPriceDTO);
    }

    // TODO t
    @Test
    public void testUpdateMenuItemPrice(){
        MenuItemPrice oldMenuItemPrice = menuItemPriceService.findLastPrice(UPDATE_ID);
        UpdateMenuItemPriceDTO menuItemPriceDTO = new UpdateMenuItemPriceDTO(UPDATE_ID, UPDATE_PRICE, UPDATE_PURCHASE_PRICE);
        MenuItemPrice updated = menuItemPriceService.updateMenuItemPrice(menuItemPriceDTO);

        assertNotEquals(oldMenuItemPrice.getId(), updated.getId()); // updated id = +1
        assertNotEquals(oldMenuItemPrice.getPrice(), updated.getPrice());
        assertNotEquals(oldMenuItemPrice.getPurchasePrice(), updated.getPurchasePrice());

        assertEquals(UPDATE_PRICE, updated.getPrice(), 0);
        assertEquals(UPDATE_PURCHASE_PRICE, updated.getPurchasePrice(), 0);
    }

}
