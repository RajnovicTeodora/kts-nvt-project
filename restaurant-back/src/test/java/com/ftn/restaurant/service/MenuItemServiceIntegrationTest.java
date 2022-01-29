package com.ftn.restaurant.service;

import static com.ftn.restaurant.constants.MenuItemConstants.*;

import com.ftn.restaurant.dto.MenuItemDTO;
import com.ftn.restaurant.exception.MenuItemNotFoundException;
import com.ftn.restaurant.model.MenuItem;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class MenuItemServiceIntegrationTest {

    @Autowired
    private MenuItemService menuItemService;

    @Test
    public void testGetAll(){
        List<MenuItemDTO> menuItems = menuItemService.getAll("");
        assertEquals(1, menuItems.size());

        menuItems = menuItemService.getAll("Ice");
        assertEquals(1, menuItems.size());

        menuItems = menuItemService.getAll("random");
        assertEquals(0, menuItems.size());
    }

    @Test
    public void testFindByIdShouldReturnMenuItem(){
        Optional<MenuItem> found = menuItemService.findByMenuItemId(1);
        assertTrue(found.isPresent());
    }

    @Test
    public void testFindByIdShouldReturnNull(){
        Optional<MenuItem> found = menuItemService.findByMenuItemId(NON_EXISTENT_MENU_ITEM_ID);
        assertFalse(found.isPresent());

        found = menuItemService.findByMenuItemId(6L);
        assertFalse(found.isPresent());
    }

    @Test(expected = MenuItemNotFoundException.class)
    public void testDeleteMenuItemAndExpectMenuItemNotFoundExceptionWhenMenuItemNotFound() {
        menuItemService.deleteMenuItem(NON_EXISTENT_MENU_ITEM_ID);
    }

    @Test(expected = MenuItemNotFoundException.class)
    public void testDeleteMenuItemAndExpectMenuItemNotFoundExceptionWhenMenuItemDeleted() {
        menuItemService.deleteMenuItem(6L);
    }

    @Test
    public void testDeleteMenuItem(){
        MenuItem deleted = menuItemService.deleteMenuItem(DELETE_ITEM_ID);
        assertTrue(deleted.isDeleted());
    }

    @Test(expected = MenuItemNotFoundException.class)
    public void testApproveMenuItemAndExpectMenuItemNotFoundWhenMenuItemNotExisting(){
        MenuItem found = menuItemService.approveMenuItem(NON_EXISTENT_MENU_ITEM_ID);

    }

    @Test(expected = MenuItemNotFoundException.class)
    public void testApproveMenuItemAndExpectMenuItemNotFoundWhenMenuItemDeleted(){
        MenuItem found = menuItemService.approveMenuItem(DELETE_ITEM_ID);
    }

    @Test
    public void testApproveMenuItem() {

        MenuItem approved = menuItemService.approveMenuItem(7L);
        assertTrue(approved.isApproved());
    }
}
