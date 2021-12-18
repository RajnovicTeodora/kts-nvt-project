package com.ftn.restaurant.service;

import static com.ftn.restaurant.constants.DrinkConstants.*;
import com.ftn.restaurant.dto.DrinkDTO;
import com.ftn.restaurant.dto.NewDrinkDTO;
import com.ftn.restaurant.exception.DrinkExistsException;
import com.ftn.restaurant.model.Drink;
import com.ftn.restaurant.model.enums.ContainerType;
import com.ftn.restaurant.model.enums.DrinkType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static com.ftn.restaurant.constants.NewDrinkDTOConstants.NEW_DRINK_DTO_1;
import static com.ftn.restaurant.constants.NumbersOfItemsConstant.*;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class DrinkServiceIntegrationTest {

    @Autowired
    private DrinkService drinkService;

    @Test
    public void addDrinkByBartenderTest(){
        NewDrinkDTO newDrinkDTO = NEW_DRINK_DTO_1;
        Drink created = drinkService.addDrinkByBartender(newDrinkDTO);


        assertEquals(newDrinkDTO.getName(), created.getName());
    }
    @Test
    public void getDrinkTest(){
        DrinkDTO foundDrink = drinkService.getDrink(7);
        assertNotNull(foundDrink);
        assertEquals("Sprite",foundDrink.getName());

        foundDrink = drinkService.getDrink(-1);
        assertNull(foundDrink);
    }

    @Test
    public void getDrinksTest(){
        List<DrinkDTO> foundDrinks = drinkService.getDrinks();
        assertNotNull(foundDrinks);
        assertEquals(NUMBER_OF_DRINKS, foundDrinks.size());
    }

    // TODO T
    @Test
    public void testAddDrinkByManager(){
        NewDrinkDTO drinkDTO = new NewDrinkDTO(NEW_DRINK_NAME, "some image", NEW_DRINK_TYPE, NEW_CONTAINER_TYPE);
        Drink created = drinkService.addDrinkByManager(drinkDTO);
        assertEquals(NEW_DRINK_NAME, created.getName());
    }

    // TODO T
    @Test(expected = DrinkExistsException.class)
    public void testAddDrinkByManagerAndExpectDrinkExistsException(){
        NewDrinkDTO drinkDTO = new NewDrinkDTO(EXISTING_DRINK_NAME, "some image", EXISTING_DRINK_TYPE, EXISTING_CONTAINER_TYPE);
        drinkService.addDrinkByManager(drinkDTO);
    }
}
