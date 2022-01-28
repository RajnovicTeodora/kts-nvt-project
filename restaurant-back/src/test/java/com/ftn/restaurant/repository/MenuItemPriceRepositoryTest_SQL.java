package com.ftn.restaurant.repository;

import com.ftn.restaurant.model.MenuItemPrice;

import static com.ftn.restaurant.constants.MenuItemPriceConstants.*;
import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles({"test"})
public class MenuItemPriceRepositoryTest_SQL {
    @Autowired
    private MenuItemPriceRepository menuItemPriceRepository;

    @Test
    public void testFindByMenuItemIdAndDeletedNotAndApprovedAndHasPrice() {

        Optional<MenuItemPrice> found = menuItemPriceRepository.findByMenuItemIdAndDeletedNotAndApprovedAndHasPrice(DB_MENU_ITEM_ID1, DB_MENU_ITEM_DATE1);
        assertTrue(found.isPresent());

        Optional<MenuItemPrice> notFound = menuItemPriceRepository.findByItemIdAndItemDeletedFalseAndItemApprovedTrueAndDateToIsNull(DB_MENU_ITEM_ID2);
        assertFalse(notFound.isPresent());
    }

    @Test
    public void testFindByItemIdAndItemDeletedFalseAndItemApprovedTrueAndDateToIsNull() {

        Optional<MenuItemPrice> found = menuItemPriceRepository.findByItemIdAndItemDeletedFalseAndItemApprovedTrueAndDateToIsNull(DB_MENU_ITEM_ID1);
        assertTrue(found.isPresent());

        Optional<MenuItemPrice> notFound = menuItemPriceRepository.findByItemIdAndItemDeletedFalseAndItemApprovedTrueAndDateToIsNull(DB_MENU_ITEM_ID2);
        assertFalse(notFound.isPresent());
    }

}
