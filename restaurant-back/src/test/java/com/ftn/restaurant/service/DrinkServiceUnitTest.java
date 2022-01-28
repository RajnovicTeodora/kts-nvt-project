package com.ftn.restaurant.service;

import com.ftn.restaurant.RestaurantApplication;
import com.ftn.restaurant.dto.NewDrinkDTO;
import com.ftn.restaurant.exception.DrinkExistsException;
import com.ftn.restaurant.exception.ForbiddenException;
import com.ftn.restaurant.model.Dish;
import com.ftn.restaurant.model.Drink;
import com.ftn.restaurant.model.MenuItemPrice;
import com.ftn.restaurant.model.enums.ContainerType;
import com.ftn.restaurant.model.enums.DishType;
import com.ftn.restaurant.model.enums.DrinkType;
import com.ftn.restaurant.repository.DrinkRepository;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.ftn.restaurant.constants.DishConstants.LIST_DISHES;
import static com.ftn.restaurant.constants.DrinkConstants.*;
import static com.ftn.restaurant.constants.NewDrinkDTOConstants.NEW_DRINK_DTO_1;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class DrinkServiceUnitTest {

    @Autowired
    private DrinkService drinkService;

    @MockBean
    private DrinkRepository drinkRepository;//mockBean

    @BeforeAll
    public void setup() {
        List<Drink> drinkList = new ArrayList<>();
        drinkList.add(DRINK_1);

        given(drinkRepository.findAll()).willReturn(drinkList);

        given(drinkRepository.findByNameAndDrinkTypeAndContainerType("Bloody Mary", DrinkType.ALCOHOLIC, ContainerType.GLASS))
                .willReturn(java.util.Optional.of(null));
        Drink drink = new Drink("Bloody Mary","a",false,false,new ArrayList<>(), DrinkType.ALCOHOLIC, ContainerType.GLASS, new ArrayList<>() );
        //given(drinkRepository.save(any())).willReturn(drink);
        //when(drinkRepository.save(any())).thenReturn(drink);
    }
    @Test
    public void addDrinkByBartenderTest(){
        NewDrinkDTO newDrinkDTO = NEW_DRINK_DTO_1;
        Drink created = drinkService.addDrinkByBartender(newDrinkDTO);

        verify(drinkRepository, times(1)).findByNameAndDrinkTypeAndContainerType(
                newDrinkDTO.getName(),
                newDrinkDTO.getDrinkType(),
                newDrinkDTO.getContainerType());

        verify(drinkRepository, times(1)).save(any());
    }

    // TODO T
    @Test(expected = DrinkExistsException.class)
    public void testAddDrinkAndExpectDrinkExistsExceptionWhenDrinkAlreadyExists(){
        NewDrinkDTO existingDrinkDTO = new NewDrinkDTO(EXISTING_DRINK_NAME, "some image", EXISTING_DRINK_TYPE, EXISTING_CONTAINER_TYPE);

        given(drinkRepository.findByNameAndDrinkTypeAndContainerType(EXISTING_DRINK_NAME, EXISTING_DRINK_TYPE, EXISTING_CONTAINER_TYPE)).willThrow(DrinkExistsException.class);

        drinkService.addDrink(existingDrinkDTO);
    }

    // TODO T
    @Test
    public void testAddDrink()
    {
        NewDrinkDTO drinkDTO = new NewDrinkDTO(NEW_DRINK_NAME, "some image", NEW_DRINK_TYPE, NEW_CONTAINER_TYPE);
        given(drinkRepository.findByNameAndDrinkTypeAndContainerType(NEW_DRINK_NAME, NEW_DRINK_TYPE, NEW_CONTAINER_TYPE)).willReturn(Optional.empty());

        Drink drink = drinkService.addDrink(drinkDTO);

        verify(drinkRepository, times(1)).findByNameAndDrinkTypeAndContainerType(NEW_DRINK_NAME, NEW_DRINK_TYPE, NEW_CONTAINER_TYPE);
        assertNotNull(drink);
        assertEquals(NEW_DRINK_NAME, drink.getName());
    }
    @Test
    public void testSearchDrink(){
        given(drinkRepository.getApprovedDrinks()).willReturn(DRINK_LIST);

        List<Drink> searched =drinkService.getSearchedOrFiltered("","");
        assertEquals(searched.size(), 2);

        searched =drinkService.getSearchedOrFiltered("","COLD_DRINK");
        assertEquals(1, searched.size());
        assertEquals("COLD_DRINK", searched.get(0).getDrinkType().name());

        searched =drinkService.getSearchedOrFiltered("milk sejk2","");
        assertEquals(1, searched.size());
        assertEquals("milk sejk2", searched.get(0).getName());

        searched =drinkService.getSearchedOrFiltered("milk sejk","COLD_DRINK");
        assertEquals(1, searched.size());
        assertEquals("COLD_DRINK", searched.get(0).getDrinkType().name());
        assertEquals("milk sejk", searched.get(0).getName());

        searched =drinkService.getSearchedOrFiltered("aaaa","aaaa");
        assertEquals(0, searched.size());


        //assertEquals(newDishDTO.getName(), created.getName()); //ovo mi vraca nulltj created je null
    }
}
