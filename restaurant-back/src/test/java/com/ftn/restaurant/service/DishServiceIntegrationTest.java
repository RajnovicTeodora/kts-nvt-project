package com.ftn.restaurant.service;

import com.ftn.restaurant.dto.NewDishDTO;
import com.ftn.restaurant.exception.DishExistsException;
import com.ftn.restaurant.model.Dish;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static com.ftn.restaurant.constants.DishConstants.LIST_DISHES;
import static com.ftn.restaurant.constants.NewDishDTOConstants.NEW_DISH_DTO_1;
import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;

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
        //added new dish
        Assert.assertEquals(NEW_DISH_DTO_1.getName(), created.getName());
        //Alredy exists
        Assert.assertThrows(DishExistsException.class, () -> {dishService.addDish(newDishDTO);});

    }

    @Test
    public void testSearchDish(){

        List<Dish> searched =dishService.getSearchedOrFiltered("","");
        assertEquals(3,searched.size());

        searched =dishService.getSearchedOrFiltered("","MAIN_DISH");
        assertEquals(3, searched.size());
        assertEquals(searched.get(0).getDishType().name(), "MAIN_DISH");

        searched =dishService.getSearchedOrFiltered("Pizza","");
        assertEquals(1, searched.size());
        assertEquals(searched.get(0).getName(), "Pizza");

        searched =dishService.getSearchedOrFiltered("Meatballs","MAIN_DISH");
        assertEquals(1, searched.size());
        assertEquals(searched.get(0).getName(), "Meatballs");
        assertEquals(searched.get(0).getDishType().name(), "MAIN_DISH");

        searched =dishService.getSearchedOrFiltered("PMeatballsvvvvvv","MAIN_DISHvvvv");
        assertEquals(0, searched.size());
    }
}
