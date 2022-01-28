package com.ftn.restaurant.repository;

import com.ftn.restaurant.model.Dish;
import com.ftn.restaurant.model.Drink;
import com.ftn.restaurant.model.MenuItem;
import com.ftn.restaurant.model.enums.ContainerType;
import com.ftn.restaurant.model.enums.DishType;
import com.ftn.restaurant.model.enums.DrinkType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
public class MenuItemRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Before
    public void setUp() {
        // ID will be 5
        MenuItem menuItem1 = new Drink("Drink", "image", false, false, new ArrayList<>(), DrinkType.COLD_DRINK, ContainerType.GLASS);
        MenuItem menuItem2 = new Dish("Dish", "image", true, false, new ArrayList<>(), DishType.MAIN_DISH);
        MenuItem menuItem3 = new Dish("Dish2", "image", true, false, new ArrayList<>(), DishType.MAIN_DISH);
        MenuItem menuItem4 = new Dish("Deleted", "image", true, true, new ArrayList<>(), DishType.MAIN_DISH);
        entityManager.persist(menuItem1);
        entityManager.persist(menuItem2);
        entityManager.persist(menuItem3);
        entityManager.persist(menuItem4);
    }

    @Test
    public void testFindAllBySearchCriteria() {
        List<MenuItem> found = menuItemRepository.findAll("");
        assertEquals(3, found.size());

        found = menuItemRepository.findAll("Drink");
        assertEquals(1, found.size());

        found = menuItemRepository.findAll("Dish");
        assertEquals(0, found.size());

        found = menuItemRepository.findAll("no match");
        assertEquals(0, found.size());
    }

    @Test
    public void testFindByDeletedFalseAndApprovedTrueAndBySearchCriteria() {
        List<MenuItem> found = menuItemRepository.findByDeletedFalseAndApprovedTrueAndBySearchCriteria("");
        assertEquals(6, found.size());

        found = menuItemRepository.findByDeletedFalseAndApprovedTrueAndBySearchCriteria("Drink");
        assertEquals(0, found.size());

        found = menuItemRepository.findByDeletedFalseAndApprovedTrueAndBySearchCriteria("Dish");
        assertEquals(2, found.size());

        found = menuItemRepository.findByDeletedFalseAndApprovedTrueAndBySearchCriteria("no match");
        assertEquals(0, found.size());
    }
}
