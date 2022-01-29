package com.ftn.restaurant.service;

import static com.ftn.restaurant.constants.DateTimeConstants.*;

import com.ftn.restaurant.dto.CurrentMenuItemPriceDTO;
import com.ftn.restaurant.exception.ForbiddenException;
import com.ftn.restaurant.exception.MenuItemNotFoundException;
import com.ftn.restaurant.model.Dish;
import com.ftn.restaurant.model.Drink;
import com.ftn.restaurant.model.MenuItem;
import com.ftn.restaurant.model.MenuItemPrice;
import com.ftn.restaurant.model.enums.ContainerType;
import com.ftn.restaurant.model.enums.DishType;
import com.ftn.restaurant.model.enums.DrinkType;
import com.ftn.restaurant.repository.DishRepository;
import com.ftn.restaurant.repository.DrinkRepository;
import com.ftn.restaurant.repository.MenuItemPriceRepository;
import com.ftn.restaurant.repository.MenuItemRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class MenuServiceUnitTest {

    @Autowired
    private MenuService menuService;

    @MockBean
    private MenuItemPriceRepository menuItemPriceRepository;
    
    @MockBean
    private MenuItemRepository menuItemRepository;
    
    @MockBean
    private DrinkRepository drinkRepository;
    
    @MockBean
    private DishRepository dishRepository;
    
    final Drink drink1 = new Drink("Lemonade", "img", true, false, new ArrayList<MenuItemPrice>(), DrinkType.COLD_DRINK, ContainerType.GLASS);
	final MenuItemPrice price1 = new MenuItemPrice(LocalDate.of(2021, 11, 12), null, 100.0, true, 200.0, drink1);
	
	final Dish dish1 = new Dish("Pizza", "img", true, false, null, DishType.MAIN_DISH);
	final MenuItemPrice price2 = new MenuItemPrice(LocalDate.of(2021, 11, 12), null, 100.0, true, 200.0, dish1);
	
	final List<MenuItem> allItems = new ArrayList<>();
	final List<Drink> allDrinks = new ArrayList<>();
	final List<Dish> allDishes = new ArrayList<>();
	
    @Before
    public void setup() {

        MenuItem item = new Drink();
        item.setId(15L);
        MenuItemPrice menuItemPriceTrue1 = new MenuItemPrice(TODAY, null, 10, true, 5, item);

        MenuItem item1 = new Drink();
        item1.setId(16L);
        MenuItemPrice menuItemPriceFalse1 = new MenuItemPrice(TODAY, null, 10, false, 5, item1);

        MenuItem item3 = new Drink();
        item3.setId(20L);
        MenuItemPrice menuItemPriceTrue2 = new MenuItemPrice(TODAY, null, 10, true, 5, item3);

        // Toggle T -> F
        given(menuItemPriceRepository.findByMenuItemIdAndDeletedNotAndApprovedAndHasPrice(15, TODAY)).willReturn(Optional.of(menuItemPriceTrue1));
        menuItemPriceTrue1.setActive(false);
        given(menuItemPriceRepository.save(menuItemPriceTrue1)).willReturn(menuItemPriceTrue1);
        menuItemPriceTrue1.setActive(true);

        // Toggle is active F -> T
        given(menuItemPriceRepository.findByMenuItemIdAndDeletedNotAndApprovedAndHasPrice(16, TODAY)).willReturn(Optional.of(menuItemPriceFalse1));
        menuItemPriceFalse1.setActive(true);
        given(menuItemPriceRepository.save(menuItemPriceFalse1)).willReturn(menuItemPriceFalse1);
        menuItemPriceFalse1.setActive(false);

        // Toggle item without a price
        MenuItem item2 = new Drink();
        item2.setId(17L);

        given(menuItemPriceRepository.findByMenuItemIdAndDeletedNotAndApprovedAndHasPrice(17, TODAY)).willReturn(Optional.empty());
        // Get active menu items

        List<MenuItem> menuItems = new ArrayList<>();
        menuItems.add(item);
        menuItems.add(item3);
        given(menuItemRepository.findByDeletedFalseAndApprovedTrueAndBySearchCriteria("")).willReturn(menuItems);
        given(menuItemPriceRepository.findByItemIdAndItemDeletedFalseAndItemApprovedTrueAndDateToIsNull(15)).willReturn(Optional.of(menuItemPriceTrue1));
        given(menuItemPriceRepository.findByItemIdAndItemDeletedFalseAndItemApprovedTrueAndDateToIsNull(20)).willReturn(Optional.of(menuItemPriceTrue2));
        given(menuItemPriceRepository.findByMenuItemIdAndDeletedNotAndApprovedAndHasPrice(15, TODAY)).willReturn(Optional.of(menuItemPriceTrue1));
        given(menuItemPriceRepository.findByMenuItemIdAndDeletedNotAndApprovedAndHasPrice(20, TODAY)).willReturn(Optional.of(menuItemPriceTrue2));

        // MenuItem not found
        drink1.setId(1L);
        dish1.setId(2L);
        
        allItems.add(dish1);
        allItems.add(drink1);
        allDrinks.add(drink1);
        allDishes.add(dish1);
        
        price1.setId(1L);
        price2.setId(2L);
        
        List<MenuItemPrice> drinkPrice = new ArrayList<>();
        drinkPrice.add(price1);
        
        List<MenuItemPrice> dishPrice = new ArrayList<>();
        dishPrice.add(price2);
        
        drink1.setPriceList(drinkPrice);
        dish1.setPriceList(dishPrice);
        
        Mockito.when(menuItemRepository.findAll()).thenReturn(allItems);
        Mockito.when(drinkRepository.findAll()).thenReturn(allDrinks);
        Mockito.when(dishRepository.findAll()).thenReturn(allDishes);
        
        Mockito.when(menuItemPriceRepository.findByItemIdAndItemDeletedFalseAndItemApprovedTrueAndDateToIsNull(1L)).thenReturn(Optional.of(price1));
        Mockito.when(menuItemPriceRepository.findByItemIdAndItemDeletedFalseAndItemApprovedTrueAndDateToIsNull(2L)).thenReturn(Optional.of(price2));

        // Delete
        MenuItem itemDelete = new Drink();
        itemDelete.setId(35L);
        MenuItem itemDelete1 = new Drink();
        itemDelete1.setId(36L);
        MenuItemPrice menuItemPriceDelete= new MenuItemPrice(TODAY, null, 10, true, 5, itemDelete1);

        given(menuItemRepository.findByIdAndDeletedFalse(34L)).willReturn(Optional.empty());

        given(menuItemRepository.findByIdAndDeletedFalse(35L)).willReturn(Optional.of(itemDelete));
        given(menuItemPriceRepository.findByItemIdAndItemDeletedFalseAndItemApprovedTrueAndDateToIsNull(35L)).willReturn(Optional.empty());
        itemDelete.setDeleted(true);
        given(menuItemRepository.save(itemDelete)).willReturn(itemDelete1);

        given(menuItemRepository.findByIdAndDeletedFalse(36L)).willReturn(Optional.of(itemDelete));
        given(menuItemPriceRepository.findByItemIdAndItemDeletedFalseAndItemApprovedTrueAndDateToIsNull(36L)).willReturn(Optional.of(menuItemPriceDelete));
        menuItemPriceDelete.setActive(false);
        given(menuItemPriceRepository.save(menuItemPriceDelete)).willReturn(menuItemPriceDelete);
        itemDelete1.setDeleted(true);
        given(menuItemRepository.save(itemDelete1)).willReturn(itemDelete1);
    }

    @Test(expected = ForbiddenException.class)
    public void testToggleIsMenuItemActiveShouldReturnForbiddenExceptionWhenNoPrice(){
        MenuItemPrice menuItemPrice = menuService.toggleIsMenuItemActive(17L);
        verify(menuItemPriceRepository, times(1)).findByMenuItemIdAndDeletedNotAndApprovedAndHasPrice(17L, TODAY);
    }

    @Test
    public void testToggleIsMenuItemActiveTrueToFalseShouldReturnMenuItemPrice(){
        MenuItemPrice menuItemPrice = menuService.toggleIsMenuItemActive(15L);
        verify(menuItemPriceRepository, times(1)).findByMenuItemIdAndDeletedNotAndApprovedAndHasPrice(15L, TODAY);
        assertFalse(menuItemPrice.isActive());
    }

    @Test
    public void testToggleIsMenuItemActiveFalseToTrueShouldReturnMenuItemPrice(){
        MenuItemPrice menuItemPrice = menuService.toggleIsMenuItemActive(16L);
        verify(menuItemPriceRepository, times(1)).findByMenuItemIdAndDeletedNotAndApprovedAndHasPrice(16, TODAY);
        assertTrue(menuItemPrice.isActive());

    }

    @Test
    public void getActiveMenuItem(){
        List<CurrentMenuItemPriceDTO> currentMenuItemPriceDTOS = menuService.getActiveMenuItem("");
        verify(menuItemRepository, times(1)).findByDeletedFalseAndApprovedTrueAndBySearchCriteria("");
        verify(menuItemPriceRepository, times(1)).findByItemIdAndItemDeletedFalseAndItemApprovedTrueAndDateToIsNull(15L);
        verify(menuItemPriceRepository, times(1)).findByItemIdAndItemDeletedFalseAndItemApprovedTrueAndDateToIsNull(20L);
        verify(menuItemPriceRepository, times(1)).findByMenuItemIdAndDeletedNotAndApprovedAndHasPrice(15L, TODAY);
        verify(menuItemPriceRepository, times(1)).findByMenuItemIdAndDeletedNotAndApprovedAndHasPrice(20L, TODAY);
        assertEquals(currentMenuItemPriceDTOS.size(), 2);
        assertEquals(currentMenuItemPriceDTOS.get(0).getCurrentPrice(), currentMenuItemPriceDTOS.get(0).getPrice(), 0);
    }

    @Test
    public void searchMenuItemsTest_Success() {
    	
    	assertEquals(2, menuService.searchMenuItems("...", "...").size());
    	assertEquals(1, menuService.searchMenuItems("drink", "...").size());
    	assertEquals(1, menuService.searchMenuItems("dish", "pizza").size());
    	assertEquals(1, menuService.searchMenuItems("COLD_DRINK", "...").size());
    	assertEquals(0, menuService.searchMenuItems("SOUP", "...").size());
        
    }
    
    @Test(expected = MenuItemNotFoundException.class)
    public void searchMenuItemsTest_Menu_Item_Not_Found_Exception() {
    	menuService.searchMenuItems("caj", "...");
    }

    @Test(expected = ForbiddenException.class)
    public void testDeleteMenuItemShouldReturnForbiddenExceptionWhenItemNotPresent(){
        MenuItem menuItem = menuService.deleteMenuItem(34L);
        verify(menuItemRepository, times(1)).findByIdAndDeletedFalse(34);
    }

    @Test
    public void testDeleteMenuItemShouldReturnMenuItem(){
        MenuItem menuItem = menuService.deleteMenuItem(35L);
        verify(menuItemRepository, times(1)).findByIdAndDeletedFalse(35);
        verify(menuItemPriceRepository, times(1)).findByItemIdAndItemDeletedFalseAndItemApprovedTrueAndDateToIsNull(35);
        assertTrue(menuItem.isDeleted());

        MenuItem menuItem1 = menuService.deleteMenuItem(36L);
        verify(menuItemRepository, times(1)).findByIdAndDeletedFalse(36);
        verify(menuItemPriceRepository, times(1)).findByItemIdAndItemDeletedFalseAndItemApprovedTrueAndDateToIsNull(36);
        assertTrue(menuItem1.isDeleted());
    }
}
