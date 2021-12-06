package com.ftn.restaurant.repository;

import com.ftn.restaurant.model.MenuItem;
import com.ftn.restaurant.model.MenuItemPrice;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

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

    //TODO: add menu item without any prices, replace with constants
    @Before
    public void setUp() {
        MenuItemPrice menuItemPrice1 = new MenuItemPrice(LocalDate.now().minusDays(2), LocalDate.now().minusDays(1), 5, false, 3.5, null);
        MenuItemPrice menuItemPrice2 = new MenuItemPrice(LocalDate.now(), null, 15, true, 5.5, null);
        menuItemPrice1.setItem(menuItemRepository.findByIdAndDeletedFalse(2).get());
        menuItemPrice2.setItem(menuItemRepository.findByIdAndDeletedFalse(2).get());

        entityManager.persist(menuItemPrice1);
        entityManager.persist(menuItemPrice2);
    }

    @Test
    public void testFindByMenuItemIdAndDeletedNotAndApprovedAndHasPrice() {
        Optional<MenuItemPrice> found = menuItemPriceRepository.findByMenuItemIdAndDeletedNotAndApprovedAndHasPrice(2, LocalDate.now());
        assertEquals(15.0, found.get().getPrice(), 0);
    }


    @Test
    public void testFindByItemIdAndItemDeletedFalseAndItemApprovedTrueAndDateToIsNull() {
        Optional<MenuItemPrice> found = menuItemPriceRepository.findByItemIdAndItemDeletedFalseAndItemApprovedTrueAndDateToIsNull(2);
        assertEquals(15.0, found.get().getPrice(), 0);
    }

}
