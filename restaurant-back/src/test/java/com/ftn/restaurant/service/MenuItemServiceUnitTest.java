package com.ftn.restaurant.service;

import static com.ftn.restaurant.constants.MenuItemConstants.*;
import static com.ftn.restaurant.constants.DrinkConstants.*;

import com.ftn.restaurant.dto.MenuItemDTO;
import com.ftn.restaurant.exception.MenuItemNotFoundException;
import com.ftn.restaurant.model.Drink;
import com.ftn.restaurant.model.MenuItem;
import com.ftn.restaurant.repository.MenuItemRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.*;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class MenuItemServiceUnitTest {

    @Autowired
    private MenuItemService menuItemService;

    @MockBean
    private MenuItemRepository menuItemRepository;

    @Before
    public void setup() {
        Drink drink = new Drink(NEW_DRINK_NAME, "", false, false, new ArrayList<>(), NEW_DRINK_TYPE, NEW_CONTAINER_TYPE);
        drink.setId(15L);
        Drink drink1 = new Drink(NEW_DRINK_NAME1, "", false, false, new ArrayList<>(), NEW_DRINK_TYPE1, NEW_CONTAINER_TYPE1);
        drink1.setId(16L);

        given(menuItemRepository.findById(15)).willReturn(Optional.of(drink));
        given(menuItemRepository.findByIdAndDeletedFalse(15)).willReturn(Optional.of(drink));

        // Get all

        List<MenuItem> menuItems = new ArrayList<>();
        menuItems.add(drink);
        menuItems.add(drink1);
        given(menuItemRepository.findAll("")).willReturn(menuItems);

        drink.setApproved(true);
        // Approve
        given(menuItemRepository.save(drink)).willReturn(drink);

        // Deleted
        given(menuItemRepository.findByIdAndDeletedFalse(17L)).willReturn(Optional.empty());

        given(menuItemRepository.findByIdAndDeletedFalse(15L)).willReturn(Optional.of(drink));
        drink.setDeleted(true);
        given(menuItemRepository.save(drink)).willReturn(drink);

        // Non existent menu item
        given(menuItemRepository.findByIdAndDeletedFalse(NON_EXISTENT_MENU_ITEM_ID)).willReturn(Optional.empty());

        given(menuItemRepository.findById(NON_EXISTENT_MENU_ITEM_ID)).willReturn(Optional.empty());
    }

    @Test
    public void testFindByIdShouldReturnMenuItem(){
        Optional<MenuItem> found = menuItemService.findByMenuItemId(15);
        assertTrue(found.isPresent());
        verify(menuItemRepository, times(1)).findById(15);

    }

    @Test
    public void testFindByIdShouldReturnNull(){
        Optional<MenuItem> found = menuItemService.findByMenuItemId(NON_EXISTENT_MENU_ITEM_ID);
        assertFalse(found.isPresent());
        verify(menuItemRepository, times(1)).findById(NON_EXISTENT_MENU_ITEM_ID);

        found = menuItemService.findByMenuItemId(DELETE_ITEM_ID);
        assertFalse(found.isPresent());
        verify(menuItemRepository, times(1)).findById(NON_EXISTENT_MENU_ITEM_ID);

    }

    @Test(expected = MenuItemNotFoundException.class)
    public void testDeleteMenuItemAndExpectMenuItemNotFoundExceptionWhenMenuItemNotFound() {
        menuItemService.deleteMenuItem(NON_EXISTENT_MENU_ITEM_ID);
    }

    @Test(expected = MenuItemNotFoundException.class)
    public void testDeleteMenuItemAndExpectMenuItemNotFoundExceptionWhenMenuItemDeleted() {
        menuItemService.deleteMenuItem(17L);
    }

    @Test
    public void testDeleteMenuItem() {

        MenuItem deleted = menuItemService.deleteMenuItem(15L);
        assertTrue(deleted.isDeleted());
        verify(menuItemRepository, times(1)).findByIdAndDeletedFalse(15L);

    }

    @Test(expected = MenuItemNotFoundException.class)
    public void testApproveMenuItemAndExpectMenuItemNotFoundWhenMenuItemNotExisting(){
        MenuItem found = menuItemService.approveMenuItem(NON_EXISTENT_MENU_ITEM_ID);
        verify(menuItemRepository, times(1)).findById(NON_EXISTENT_MENU_ITEM_ID);

    }

    @Test(expected = MenuItemNotFoundException.class)
    public void testApproveMenuItemAndExpectMenuItemNotFoundWhenMenuItemDeleted(){
        MenuItem found = menuItemService.approveMenuItem(DELETE_ITEM_ID);
        verify(menuItemRepository, times(1)).findById(DELETE_ITEM_ID);
    }

    @Test
    public void testApproveMenuItem() {

        MenuItem approved = menuItemService.approveMenuItem(15L);
        assertTrue(approved.isApproved());
        verify(menuItemRepository, times(1)).findByIdAndDeletedFalse(15L);
    }

    @Test
    public void testGetAll(){
        List<MenuItemDTO> menuItems = menuItemService.getAll("");
        assertEquals(2, menuItems.size());
    }

}
