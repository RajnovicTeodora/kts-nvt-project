package com.ftn.restaurant.service;

import static com.ftn.restaurant.constants.DrinkConstants.*;
import com.ftn.restaurant.dto.DrinkDTO;
import com.ftn.restaurant.dto.NewDrinkDTO;
import com.ftn.restaurant.exception.DrinkExistsException;
import com.ftn.restaurant.exception.ForbiddenException;
import com.ftn.restaurant.model.Drink;
import com.ftn.restaurant.model.enums.ContainerType;
import com.ftn.restaurant.model.enums.DrinkType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static com.ftn.restaurant.constants.NewDrinkDTOConstants.NEW_DRINK_DTO_1;
import static com.ftn.restaurant.constants.NumbersOfItemsConstant.*;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;

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
        //added drink
        assertThrows(DrinkExistsException.class, () -> {drinkService.addDrinkByBartender(newDrinkDTO);});
        //added existing drink
        assertEquals(newDrinkDTO.getName(), created.getName());
    }

    @Test
    public void getDrinksTest(){
        List<DrinkDTO> foundDrinks = drinkService.getDrinks();
        assertNotNull(foundDrinks);
        assertEquals(NUMBER_OF_DRINKS, foundDrinks.size());
    }


    @Test
    public void testAddDrinkByManager(){
        NewDrinkDTO drinkDTO = new NewDrinkDTO(NEW_DRINK_NAME, "some image", NEW_DRINK_TYPE, NEW_CONTAINER_TYPE);
        Drink created = drinkService.addDrinkByManager(drinkDTO);
        assertEquals(NEW_DRINK_NAME, created.getName());
    }

    @Test(expected = DrinkExistsException.class)
    public void testAddDrinkByManagerAndExpectDrinkExistsException(){
        NewDrinkDTO drinkDTO = new NewDrinkDTO(EXISTING_DRINK_NAME, "some image", EXISTING_DRINK_TYPE, EXISTING_CONTAINER_TYPE);
        drinkService.addDrinkByManager(drinkDTO);
    }

    @Test(expected = ForbiddenException.class)
    public void testAddDrinkByManagerAndExpectForbiddenExceptionWhenImageIsEmpty(){
        NewDrinkDTO drinkDTO = new NewDrinkDTO(EXISTING_DRINK_NAME, "", EXISTING_DRINK_TYPE, EXISTING_CONTAINER_TYPE);
        drinkService.addDrinkByManager(drinkDTO);
    }

    @Test(expected = ForbiddenException.class)
    public void testAddDrinkByManagerAndExpectForbiddenExceptionWhenNameIsEmpty(){
        NewDrinkDTO drinkDTO = new NewDrinkDTO("", "some image", EXISTING_DRINK_TYPE, EXISTING_CONTAINER_TYPE);
        drinkService.addDrinkByManager(drinkDTO);
    }

    @Test
    public void testSearchDrink(){

        List<Drink> searched =drinkService.getSearchedOrFiltered("","");
        assertEquals(searched.size(), 3);

        searched =drinkService.getSearchedOrFiltered("","COLD_DRINK");
        assertEquals(2, searched.size());
        assertEquals("COLD_DRINK", searched.get(0).getDrinkType().name());

        searched =drinkService.getSearchedOrFiltered("Ice Latte","");
        assertEquals(1, searched.size());
        assertEquals("Ice Latte", searched.get(0).getName());

        searched =drinkService.getSearchedOrFiltered("Ice Latte","COLD_DRINK");
        assertEquals(1, searched.size());
        assertEquals("COLD_DRINK", searched.get(0).getDrinkType().name());
        assertEquals("Ice Latte", searched.get(0).getName());

        searched =drinkService.getSearchedOrFiltered("aaaa","aaaa");
        assertEquals(0, searched.size());


        //assertEquals(newDishDTO.getName(), created.getName()); //ovo mi vraca nulltj created je null
    }
}
