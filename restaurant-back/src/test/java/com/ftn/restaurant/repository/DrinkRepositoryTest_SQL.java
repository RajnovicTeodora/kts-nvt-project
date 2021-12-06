package com.ftn.restaurant.repository;

import com.ftn.restaurant.model.Drink;
import com.ftn.restaurant.model.enums.ContainerType;
import com.ftn.restaurant.model.enums.DrinkType;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
//@TestPropertySource("classpath:application-test.properties")
public class DrinkRepositoryTest_SQL {
    @Autowired
    private DrinkRepository drinkRepository;

    @Test
    public void findByNameAndDrinkTypeAndContainerTypeTest(){
        Optional<Drink> maybeDrink = drinkRepository.findByNameAndDrinkTypeAndContainerType(
                "Wine",
                DrinkType.ALCOHOLIC,
                ContainerType.BOTTLE);

        Assert.assertTrue(maybeDrink.isPresent());

        maybeDrink = drinkRepository.findByNameAndDrinkTypeAndContainerType(
                "aaaaa",
                DrinkType.ALCOHOLIC,
                ContainerType.BOTTLE);

        Assert.assertFalse(maybeDrink.isPresent());

        drinkRepository.findByNameAndDrinkTypeAndContainerType(
                "",
                DrinkType.ALCOHOLIC,
                ContainerType.BOTTLE);
        Assert.assertFalse(maybeDrink.isPresent());

    }


}
