package com.ftn.restaurant.repository;

import static com.ftn.restaurant.constants.DrinkConstants.*;
import com.ftn.restaurant.model.Drink;
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
        this.entityManager.persist(new Drink(NEW_DRINK_NAME, "", true, false, new ArrayList<>(), NEW_DRINK_TYPE, NEW_CONTAINER_TYPE));
    }

    //TODO T
    @Test
    public void testFindByNameAndDrinkTypeAndContainerType() {
        Optional<Drink> found = this.drinkRepository.findByNameAndDrinkTypeAndContainerType(NEW_DRINK_NAME, NEW_DRINK_TYPE, NEW_CONTAINER_TYPE);
        Assert.assertTrue(found.isPresent());
        Assert.assertEquals(NEW_DRINK_NAME, found.get().getName());
    }

}
