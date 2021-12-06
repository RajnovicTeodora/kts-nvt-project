package com.ftn.restaurant.service;

import com.ftn.restaurant.dto.DrinkDTO;
import com.ftn.restaurant.dto.NewDrinkDTO;
import com.ftn.restaurant.model.Drink;
import com.ftn.restaurant.repository.DrinkRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static com.ftn.restaurant.constants.NewDrinkDTOConstants.NEW_DRINK_DTO_1;
import static com.ftn.restaurant.constants.NumbersOfItemsConstant.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
//@TestPropertySource("classpath:application-test.properties")
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
}
