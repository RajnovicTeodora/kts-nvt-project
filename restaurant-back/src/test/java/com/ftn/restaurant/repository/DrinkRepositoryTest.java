package com.ftn.restaurant.repository;

import static com.ftn.restaurant.constants.DateTimeConstants.*;
import static com.ftn.restaurant.constants.DrinkConstants.*;
import static com.ftn.restaurant.constants.MenuItemPriceConstants.DB_MENU_ITEM_ID;

import com.ftn.restaurant.model.Drink;
import com.ftn.restaurant.model.MenuItem;
import com.ftn.restaurant.model.MenuItemPrice;
import com.ftn.restaurant.model.enums.ContainerType;
import com.ftn.restaurant.model.enums.DrinkType;
import org.junit.Assert;
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
import java.util.Optional;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
public class DrinkRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private DrinkRepository drinkRepository;

    public DrinkRepositoryTest() {
    }

    @Before
    public void setUp() {

        Drink drink1 = new Drink(NEW_DRINK_NAME, "", true, false, new ArrayList<>(), NEW_DRINK_TYPE, NEW_CONTAINER_TYPE);
        Drink drink2 = new Drink(NEW_DRINK_NAME1, "", true, true, new ArrayList<>(), NEW_DRINK_TYPE1, NEW_CONTAINER_TYPE1);
        this.entityManager.persist(drink1);
        this.entityManager.persist(drink2);
    }

    @Test
    public void testFindByNameAndDrinkTypeAndContainerType() {
        Optional<Drink> found = this.drinkRepository.findByNameAndDrinkTypeAndContainerType(NEW_DRINK_NAME, NEW_DRINK_TYPE, NEW_CONTAINER_TYPE);
        Assert.assertTrue(found.isPresent());
        Assert.assertEquals(NEW_DRINK_NAME, found.get().getName());

    }

    @Test
    public void testFindByNameAndDrinkTypeAndContainerTypeShouldReturnNullWhenDrinkDeleted(){
        Optional<Drink> found = this.drinkRepository.findByNameAndDrinkTypeAndContainerType(NEW_DRINK_NAME1, NEW_DRINK_TYPE1, NEW_CONTAINER_TYPE1);
        Assert.assertFalse(found.isPresent());
    }

    @Test
    public void testGetApprovedDrinks(){
        List<Drink> found = this.drinkRepository.getApprovedDrinks();
        Assert.assertEquals(3, found.size());
    }
}
