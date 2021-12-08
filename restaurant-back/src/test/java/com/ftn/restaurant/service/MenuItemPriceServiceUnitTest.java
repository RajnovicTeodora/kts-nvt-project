package com.ftn.restaurant.service;

import static com.ftn.restaurant.constants.DateTimeConstants.*;
import static com.ftn.restaurant.constants.MenuItemPriceConstants.*;

import com.ftn.restaurant.dto.UpdateMenuItemPriceDTO;
import com.ftn.restaurant.model.Dish;
import com.ftn.restaurant.model.MenuItem;
import com.ftn.restaurant.model.MenuItemPrice;
import com.ftn.restaurant.repository.MenuItemPriceRepository;
import com.ftn.restaurant.repository.MenuItemRepository;
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

    @BeforeAll
    public void setup() {

        // Existing price
        MenuItem dish = new Dish(NEW_DISH_NAME, "some image", true, false, new ArrayList<>(), NEW_DISH_TYPE);
        dish.setId(NEW_DISH_ID);
        MenuItemPrice existingMenuItemPrice = new MenuItemPrice(TWO_DAYS_AGO, null, 20, true, 15, dish);

        given(menuItemPriceRepository.findByItemIdAndItemDeletedFalseAndItemApprovedTrueAndDateToIsNull(NEW_DISH_ID)).willReturn(Optional.of(existingMenuItemPrice));
        given(menuItemRepository.findByIdAndDeletedFalse(NEW_DISH_ID)).willReturn(Optional.of(dish));

        // Updated price
        MenuItemPrice menuItemPrice = new MenuItemPrice(TODAY, null, 10, true, 5, dish);
        MenuItemPrice savedMenuItemPrice = new MenuItemPrice(TODAY, null, 10, true, 5, dish);

        given(menuItemPriceRepository.save(menuItemPrice)).willReturn(savedMenuItemPrice);

        // Nonexistent price
        Dish dish1 = new Dish(NEW_DISH_NAME1, "", true, false, new ArrayList<>(), NEW_DISH_TYPE1);
        dish1.setId(NEW_DISH_ID1);

        given(menuItemPriceRepository.findByItemIdAndItemDeletedFalseAndItemApprovedTrueAndDateToIsNull(NEW_DISH_ID1)).willReturn(null);
        given(menuItemRepository.findByIdAndDeletedFalse(NEW_DISH_ID1)).willReturn(Optional.of(dish1));

    }

    @Test
    public void testUpdateMenuItemPriceExisting() {
        UpdateMenuItemPriceDTO updateMenuItemPriceDTO = new UpdateMenuItemPriceDTO(NEW_DISH_ID, 11, 6);

        MenuItemPrice updated = menuItemPriceService.updateMenuItemPrice(updateMenuItemPriceDTO);

        verify(menuItemPriceRepository, times(1)).findByItemIdAndItemDeletedFalseAndItemApprovedTrueAndDateToIsNull(NEW_DISH_ID);
        verify(menuItemRepository, times(1)).findByIdAndDeletedFalse(NEW_DISH_ID);

        assertEquals(10, updated.getPrice(), 0);
        assertEquals(5, updated.getPurchasePrice(), 0);
        assertEquals(2, updated.getItem().getPriceList().size());
    }

    // TODO T
    @Test
    public void testUpdateMenuItemPriceNew() {
        UpdateMenuItemPriceDTO updateMenuItemPriceDTO = new UpdateMenuItemPriceDTO(NEW_DISH_ID1, 10, 5);

        MenuItemPrice updated = menuItemPriceService.updateMenuItemPrice(updateMenuItemPriceDTO);

        verify(menuItemPriceRepository, times(1)).findByItemIdAndItemDeletedFalseAndItemApprovedTrueAndDateToIsNull(NEW_DISH_ID1);
        verify(menuItemRepository, times(1)).findByIdAndDeletedFalse(NEW_DISH_ID1);

        assertEquals(10, updated.getPrice(), 0);
        assertEquals(5, updated.getPurchasePrice(), 0);
        assertEquals(1, updated.getItem().getPriceList().size());

    }
}
