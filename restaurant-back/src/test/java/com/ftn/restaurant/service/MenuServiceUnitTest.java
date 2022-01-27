package com.ftn.restaurant.service;

import static com.ftn.restaurant.constants.DateTimeConstants.*;

import com.ftn.restaurant.dto.SelectedMenuItemsDTO;
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

import static com.ftn.restaurant.constants.MenuConstants.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;

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
	
	final List<MenuItem> allItems = new ArrayList<MenuItem>();
	final List<Drink> allDrinks = new ArrayList<Drink>();
	final List<Dish> allDishes = new ArrayList<Dish>();
	
    @Before
    public void setup() {

        MenuItem item = new Drink();
        MenuItemPrice menuItemPriceTrue1 = new MenuItemPrice(TODAY, null, 10, true, 5, item);
        MenuItemPrice savedMenuItemPriceTrue1 = new MenuItemPrice(TODAY, null, 10, true, 5, item);

        MenuItemPrice menuItemPriceFalse1 = new MenuItemPrice(TODAY, null, 10, true, 5, item);
        MenuItemPrice savedMenuItemPriceFalse1 = new MenuItemPrice(TODAY, null, 10, false, 5, item);


        MenuItemPrice menuItemPrice2 = new MenuItemPrice(TODAY, null, 10, true, 5, item);
        MenuItemPrice savedMenuItemPrice2 = new MenuItemPrice(TODAY, null, 10, false, 5, item);

        // One active item
        given(menuItemPriceRepository.findByMenuItemIdAndDeletedNotAndApprovedAndHasPrice(ITEM_ID_LIST.get(0), TODAY)).willReturn(Optional.of(menuItemPriceTrue1));
        given(menuItemPriceRepository.save(menuItemPriceTrue1)).willReturn(savedMenuItemPriceTrue1);

        // Empty id list

        List<MenuItemPrice> menuItemPriceList = new ArrayList<MenuItemPrice>()
        {
            {
                add(menuItemPriceFalse1);
                add(menuItemPrice2);
            }
        };
        given(menuItemPriceRepository.findAll()).willReturn(menuItemPriceList);
        given(menuItemPriceRepository.save(menuItemPriceFalse1)).willReturn(savedMenuItemPriceFalse1);
        given(menuItemPriceRepository.save(menuItemPrice2)).willReturn(savedMenuItemPrice2);

        // MenuItem not found
        given(menuItemPriceRepository.findByMenuItemIdAndDeletedNotAndApprovedAndHasPrice(INVALID_ITEM_ID_LIST.get(0), TODAY)).willReturn(Optional.empty());
    
        drink1.setId(1L);
        dish1.setId(2L);
        
        allItems.add(dish1);
        allItems.add(drink1);
        allDrinks.add(drink1);
        allDishes.add(dish1);
        
        price1.setId(1L);
        price2.setId(2L);
        
        List<MenuItemPrice> drinkPrice = new ArrayList<MenuItemPrice>();
        drinkPrice.add(price1);
        
        List<MenuItemPrice> dishPrice = new ArrayList<MenuItemPrice>();
        dishPrice.add(price2);
        
        drink1.setPriceList(drinkPrice);
        dish1.setPriceList(dishPrice);
        
        Mockito.when(menuItemRepository.findAll()).thenReturn(allItems);
        Mockito.when(drinkRepository.findAll()).thenReturn(allDrinks);
        Mockito.when(dishRepository.findAll()).thenReturn(allDishes);
        
        Mockito.when(menuItemPriceRepository.findByItemIdAndItemDeletedFalseAndItemApprovedTrueAndDateToIsNull(1L)).thenReturn(Optional.of(price1));
        Mockito.when(menuItemPriceRepository.findByItemIdAndItemDeletedFalseAndItemApprovedTrueAndDateToIsNull(2L)).thenReturn(Optional.of(price2));
    }

    @Test
    public void testDefineActiveMenuItem(){
        SelectedMenuItemsDTO selectedMenuItemsDTO = new SelectedMenuItemsDTO(ITEM_ID_LIST);
        //List<MenuItemPrice> menuItemPriceList = menuService.defineActiveMenuItem(selectedMenuItemsDTO);

        //assertEquals(1, menuItemPriceList.size());
    }

    @Test
    public void testDefineEmptyActiveMenuItem(){
        SelectedMenuItemsDTO selectedMenuItemsDTO = new SelectedMenuItemsDTO(EMPTY_ITEM_ID_LIST);
        //List<MenuItemPrice> menuItemPrices = menuService.defineActiveMenuItem(selectedMenuItemsDTO);
        //assertEquals(2, menuItemPrices.size());

    }

    @Test(expected = ForbiddenException.class)
    public void testDefineActiveMenuItemAndExpectForbiddenException(){
        SelectedMenuItemsDTO selectedMenuItemsDTO = new SelectedMenuItemsDTO(INVALID_ITEM_ID_LIST);

        //menuService.defineActiveMenuItem(selectedMenuItemsDTO);
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

}
