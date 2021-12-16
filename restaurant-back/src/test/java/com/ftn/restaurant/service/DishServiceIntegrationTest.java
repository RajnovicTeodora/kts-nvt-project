package com.ftn.restaurant.service;

import com.ftn.restaurant.dto.NewDishDTO;
import com.ftn.restaurant.exception.DishExistsException;
import com.ftn.restaurant.exception.ForbiddenException;
import com.ftn.restaurant.model.Dish;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static com.ftn.restaurant.constants.NewDishDTOConstants.NEW_DISH_DTO_1;
import static com.ftn.restaurant.constants.NewDishDTOConstants.NEW_DISH_DTO_2;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class DishServiceIntegrationTest {

    @Autowired
    private DishService dishService;

    @Test
    public void addDishTest() throws Exception {
        NewDishDTO newDishDTO = NEW_DISH_DTO_1;
        Dish created = dishService.addDish(newDishDTO);
        Assert.assertEquals(NEW_DISH_DTO_1.getName(), created.getName());

        //trying to add again
        Assert.assertThrows(DishExistsException.class, () -> {dishService.addDish(newDishDTO);});

    }

    @Test
    public void addDishTestWithoutName() throws Exception {
        NewDishDTO newDishDTO = NEW_DISH_DTO_2;
        Assert.assertThrows(ForbiddenException.class, () -> {dishService.addDish(newDishDTO);});

    }

}
