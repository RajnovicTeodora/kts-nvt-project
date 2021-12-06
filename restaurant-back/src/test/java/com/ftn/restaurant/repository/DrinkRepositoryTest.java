package com.ftn.restaurant.repository;


import com.ftn.restaurant.model.Drink;
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
import java.util.Optional;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles({"test"})
public class DrinkRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private DrinkRepository drinkRepository;

    public DrinkRepositoryTest() {
    }

    @Before
    public void setUp() {
        this.entityManager.persist(new Drink("Hot dark chocolate", "some_image", true, false, new ArrayList<>(), DrinkType.HOT_DRINK, ContainerType.GLASS));
    }

    @Test
    public void testFindByNameAndDrinkTypeAndContainerType() {
        Optional<Drink> found = this.drinkRepository.findByNameAndDrinkTypeAndContainerType("Hot dark chocolate", DrinkType.HOT_DRINK, ContainerType.GLASS);
        Assert.assertTrue(found.isPresent());
        Assert.assertEquals("Hot dark chocolate", found.get().getName());
    }

}
