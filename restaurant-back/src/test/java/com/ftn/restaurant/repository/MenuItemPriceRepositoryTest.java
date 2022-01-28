package com.ftn.restaurant.repository;

import static com.ftn.restaurant.constants.MenuItemPriceConstants.*;
import static com.ftn.restaurant.constants.DateTimeConstants.*;
import static org.junit.Assert.*;

import com.ftn.restaurant.model.Drink;
import com.ftn.restaurant.model.MenuItem;
import com.ftn.restaurant.model.MenuItemPrice;
import com.ftn.restaurant.model.enums.ContainerType;
import com.ftn.restaurant.model.enums.DrinkType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
public class MenuItemPriceRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private MenuItemPriceRepository menuItemPriceRepository;

    @Autowired
    private MenuItemRepository menuItemRepository;

    //TODO T
    @Before
    public void setUp() {
        MenuItemPrice menuItemPrice1 = new MenuItemPrice(TWO_DAYS_AGO, YESTERDAY, 5, false, 3.5, null);
        MenuItemPrice menuItemPrice2 = new MenuItemPrice(TODAY, null, 15, true, 5.5, null);

        menuItemPrice1.setItem(menuItemRepository.findByIdAndDeletedFalse(DB_MENU_ITEM_ID).get());
        menuItemPrice2.setItem(menuItemRepository.findByIdAndDeletedFalse(DB_MENU_ITEM_ID).get());

        // ID will be 5
        MenuItem menuItem = new Drink("Name", "image", true, false, new ArrayList<>(), DrinkType.COLD_DRINK, ContainerType.GLASS);

        entityManager.persist(menuItemPrice1);
        entityManager.persist(menuItemPrice2);
        entityManager.persist(menuItem);
    }

    @Test
    public void testFindByMenuItemIdAndDeletedNotAndApprovedAndHasPrice() {
        Optional<MenuItemPrice> found = menuItemPriceRepository.findByMenuItemIdAndDeletedNotAndApprovedAndHasPrice(DB_MENU_ITEM_ID, LocalDate.now());
        assertEquals(15.0, found.get().getPrice(), 0);
    }

    @Test
    public void testShouldReturnEmptyFindByMenuItemIdAndDeletedNotAndApprovedAndHasPrice() {
        Optional<MenuItemPrice> found = menuItemPriceRepository.findByMenuItemIdAndDeletedNotAndApprovedAndHasPrice(DB_MENU_ITEM_WITHOUT_PRICE, LocalDate.now());
        assertFalse(found.isPresent());
    }

    @Test
    public void testFindByItemIdAndItemDeletedFalseAndItemApprovedTrueAndDateToIsNull() {
        Optional<MenuItemPrice> found = menuItemPriceRepository.findByItemIdAndItemDeletedFalseAndItemApprovedTrueAndDateToIsNull(DB_MENU_ITEM_ID);
        assertEquals(15.0, found.get().getPrice(), 0);
    }

    @Test
    public void testShouldReturnEmptyWhenFindByItemIdAndItemDeletedFalseAndItemApprovedTrueAndDateToIsNull() {
        Optional<MenuItemPrice> found = menuItemPriceRepository.findByItemIdAndItemDeletedFalseAndItemApprovedTrueAndDateToIsNull(DB_MENU_ITEM_WITHOUT_PRICE);
        assertFalse(found.isPresent());
    }

}
