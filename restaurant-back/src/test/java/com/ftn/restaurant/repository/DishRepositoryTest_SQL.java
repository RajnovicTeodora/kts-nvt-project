package com.ftn.restaurant.repository;
import com.ftn.restaurant.RestaurantApplication;
import com.ftn.restaurant.model.Dish;
import com.ftn.restaurant.model.enums.DishType;
import com.ftn.restaurant.repository.DishRepository;
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
public class DishRepositoryTest_SQL {

    @Autowired
    private DishRepository dishRepository;

    @Test
    public void findByNameAndIdTest(){
        Optional<Dish> maybeDish = dishRepository.findByNameAndDishType("Pizza", DishType.MAIN_DISH);
        Assert.assertTrue(maybeDish.isPresent());
        maybeDish = dishRepository.findByNameAndDishType("aaaaa", DishType.MAIN_DISH);
        Assert.assertFalse(maybeDish.isPresent());
        maybeDish = dishRepository.findByNameAndDishType("", DishType.MAIN_DISH);
        Assert.assertFalse(maybeDish.isPresent());
    }
}
