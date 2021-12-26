package com.ftn.restaurant.service;

import com.ftn.restaurant.RestaurantApplication;
import com.ftn.restaurant.dto.NewDrinkDTO;
import com.ftn.restaurant.exception.DrinkExistsException;
import com.ftn.restaurant.exception.ForbiddenException;
import com.ftn.restaurant.model.Dish;
import com.ftn.restaurant.model.Drink;
import com.ftn.restaurant.model.enums.ContainerType;
import com.ftn.restaurant.model.enums.DishType;
import com.ftn.restaurant.model.enums.DrinkType;
import com.ftn.restaurant.repository.DrinkRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.ftn.restaurant.constants.DrinkConstants.*;
import static com.ftn.restaurant.constants.NewDrinkDTOConstants.NEW_DRINK_DTO_1;
import static com.ftn.restaurant.constants.NewDrinkDTOConstants.NEW_DRINK_DTO_2;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
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
    private DrinkRepository drinkRepository;

    @Before
    public void setup() {
        List<Drink> drinkList = new ArrayList<>();
        drinkList.add(DRINK_1);

        given(drinkRepository.findAll()).willReturn(drinkList);

        Optional<Drink> optDrink = Optional.ofNullable(null);
        given(drinkRepository.findByNameAndDrinkTypeAndContainerType("Bloody Mary", DrinkType.ALCOHOLIC, ContainerType.GLASS))
                .willReturn(optDrink);

    }
    @Test
    public void addDrinkByBartenderTest(){
        Drink drink = new Drink("Bloody Mary","a",false,false,new ArrayList<>(), DrinkType.ALCOHOLIC, ContainerType.GLASS, new ArrayList<>() );
        when(drinkRepository.save(any(Drink.class))).thenReturn(drink);

        NewDrinkDTO newDrinkDTO = NEW_DRINK_DTO_1;
        Drink created = drinkService.addDrinkByBartender(newDrinkDTO);

        verify(drinkRepository, times(1)).findByNameAndDrinkTypeAndContainerType(
                newDrinkDTO.getName(),
                newDrinkDTO.getType(),
                newDrinkDTO.getContainerType());

        //verify(drinkRepository, times(1)).save(any());

        assertEquals(newDrinkDTO.getName(), created.getName());

    }
    @Test
    public void addDrinkByBartenderAlreadyeExistTest(){
        Drink drink = new Drink("Bloody Mary","a",false,false,new ArrayList<>(), DrinkType.ALCOHOLIC, ContainerType.GLASS, new ArrayList<>() );
        Optional<Drink> optDrink = Optional.of(drink);
        given(drinkRepository.findByNameAndDrinkTypeAndContainerType("Bloody Mary", DrinkType.ALCOHOLIC, ContainerType.GLASS))
                .willReturn(optDrink);
        assertThrows(DrinkExistsException.class, () -> drinkService.addDrinkByBartender(NEW_DRINK_DTO_1));

    }
    @Test
    public void addDrinkByBartenderEmptyNameTest(){

        assertThrows(ForbiddenException.class, () -> drinkService.addDrinkByBartender(NEW_DRINK_DTO_2));

    }
}
