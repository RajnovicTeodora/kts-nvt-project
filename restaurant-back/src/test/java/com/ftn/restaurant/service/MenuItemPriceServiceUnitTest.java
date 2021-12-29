package com.ftn.restaurant.service;

import static com.ftn.restaurant.constants.DateTimeConstants.*;
import static com.ftn.restaurant.constants.MenuItemPriceConstants.*;

import com.ftn.restaurant.dto.UpdateMenuItemPriceDTO;
import com.ftn.restaurant.exception.ForbiddenException;
import com.ftn.restaurant.exception.MenuItemNotFoundException;
import com.ftn.restaurant.model.Dish;
import com.ftn.restaurant.model.MenuItem;
import com.ftn.restaurant.model.MenuItemPrice;
import com.ftn.restaurant.repository.MenuItemPriceRepository;
import com.ftn.restaurant.repository.MenuItemRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class MenuItemPriceServiceUnitTest {

    @Autowired
    private MenuItemPriceService menuItemPriceService;

    @MockBean
    private MenuItemRepository menuItemRepository;

    @MockBean
    private MenuItemPriceRepository menuItemPriceRepository;

    @Before
    public void setup() {

        // Existing price
        MenuItem dish = new Dish(NEW_DISH_NAME, "some image", true, false, new ArrayList<>(), NEW_DISH_TYPE);
        dish.setId(NEW_DISH_ID);
        dish.setPriceList(new ArrayList<>());

        MenuItemPrice existingMenuItemPrice = new MenuItemPrice(TWO_DAYS_AGO, null, OLD_PRICE, true, OLD_PURCHASE_PRICE, dish);
        MenuItemPrice saveExistingMenuItemPrice = new MenuItemPrice(TWO_DAYS_AGO, TODAY, OLD_PRICE, true, OLD_PURCHASE_PRICE, dish);
        MenuItemPrice updatedMenuItemPrice = new MenuItemPrice(TOMORROW, null, UPDATE_PRICE, true, UPDATE_PURCHASE_PRICE, dish);
        MenuItemPrice savedUpdatedMenuItemPrice = new MenuItemPrice(TOMORROW, null, UPDATE_PRICE, true, UPDATE_PURCHASE_PRICE, dish);

        ArrayList<MenuItemPrice> menuItemPrices = new ArrayList<>();
        menuItemPrices.add(existingMenuItemPrice);

        given(menuItemPriceRepository.findByItemIdAndItemDeletedFalseAndItemApprovedTrueAndDateToIsNull(NEW_DISH_ID)).willReturn(Optional.of(existingMenuItemPrice));
        given(menuItemRepository.findByIdAndDeletedFalse(NEW_DISH_ID)).willReturn(Optional.of(dish));
        given(menuItemPriceRepository.findByItemId(NEW_DISH_ID)).willReturn(menuItemPrices);

        given(menuItemPriceRepository.save(existingMenuItemPrice)).willReturn(saveExistingMenuItemPrice);
        given(menuItemPriceRepository.save(updatedMenuItemPrice)).willReturn(savedUpdatedMenuItemPrice);

        // Updated price
        MenuItemPrice menuItemPrice = new MenuItemPrice(TODAY, null, UPDATE_PRICE, true, UPDATE_PURCHASE_PRICE, dish);
        MenuItemPrice savedMenuItemPrice = new MenuItemPrice(TODAY, null, UPDATE_PRICE, true, UPDATE_PURCHASE_PRICE, dish);

        given(menuItemPriceRepository.save(menuItemPrice)).willReturn(savedMenuItemPrice);

        // Nonexistent price
        Dish dish1 = new Dish(NEW_DISH_NAME1, "", true, false, new ArrayList<>(), NEW_DISH_TYPE1);
        dish1.setId(NEW_DISH_ID1);

        given(menuItemPriceRepository.findByItemIdAndItemDeletedFalseAndItemApprovedTrueAndDateToIsNull(NEW_DISH_ID1)).willReturn(Optional.empty());
        given(menuItemRepository.findByIdAndDeletedFalse(NEW_DISH_ID1)).willReturn(Optional.of(dish1));

        // Non existent menu item in db
        given(menuItemRepository.findByIdAndDeletedFalse(NON_EXISTENT_MENU_ITEM_ID)).willReturn(Optional.empty());
    }

    // TODO t
    @Test(expected = MenuItemNotFoundException.class)
    public void testUpdateMenuItemPriceAndMenuItemNotFoundExceptionWhenMenuItemIsNotPresent(){
        UpdateMenuItemPriceDTO menuItemPriceDTO = new UpdateMenuItemPriceDTO(NON_EXISTENT_MENU_ITEM_ID, UPDATE_PRICE, UPDATE_PURCHASE_PRICE);

        menuItemPriceService.updateMenuItemPrice(menuItemPriceDTO);
    }

    @Test
    public void testUpdateMenuItemPriceExisting() {
        UpdateMenuItemPriceDTO updateMenuItemPriceDTO = new UpdateMenuItemPriceDTO(NEW_DISH_ID, UPDATE_PRICE, UPDATE_PURCHASE_PRICE);

        MenuItemPrice updated = menuItemPriceService.updateMenuItemPrice(updateMenuItemPriceDTO);

        verify(menuItemPriceRepository, times(1)).findByItemIdAndItemDeletedFalseAndItemApprovedTrueAndDateToIsNull(NEW_DISH_ID);
        verify(menuItemRepository, times(1)).findByIdAndDeletedFalse(NEW_DISH_ID);

        assertEquals(UPDATE_PRICE, updated.getPrice(), 0);
        assertEquals(UPDATE_PURCHASE_PRICE, updated.getPurchasePrice(), 0);
        assertEquals(2, updated.getItem().getPriceList().size());
    }

    // TODO T
    @Test
    public void testUpdateMenuItemPriceNew() {
        UpdateMenuItemPriceDTO updateMenuItemPriceDTO = new UpdateMenuItemPriceDTO(NEW_DISH_ID1, UPDATE_PRICE, UPDATE_PURCHASE_PRICE);

        MenuItemPrice updated = menuItemPriceService.updateMenuItemPrice(updateMenuItemPriceDTO);

        verify(menuItemPriceRepository, times(1)).findByItemIdAndItemDeletedFalseAndItemApprovedTrueAndDateToIsNull(NEW_DISH_ID1);
        verify(menuItemRepository, times(1)).findByIdAndDeletedFalse(NEW_DISH_ID1);

        assertEquals(UPDATE_PRICE, updated.getPrice(), 0);
        assertEquals(UPDATE_PURCHASE_PRICE, updated.getPurchasePrice(), 0);
        assertEquals(1, updated.getItem().getPriceList().size());

    }
}
