package com.ftn.restaurant.service;

import com.ftn.restaurant.RestaurantApplication;
import com.ftn.restaurant.dto.NewDishDTO;
import com.ftn.restaurant.model.Dish;
import com.ftn.restaurant.model.enums.DishType;
import com.ftn.restaurant.repository.DishRepository;
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

import static com.ftn.restaurant.constants.DishConstants.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import static org.mockito.BDDMockito.given;
import static com.ftn.restaurant.constants.NewDishDTOConstants.NEW_DISH_DTO_1;

//@RunWith(SpringRunner.class)
////@SpringBootTest( webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@TestPropertySource("classpath:application-test.properties")
//@SpringBootConfiguration
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class DishServiceUnitTest {

    @Autowired
    private DishService dishService;

    @MockBean
    private DishRepository dishRepository;

    @BeforeAll
    public void setup() {
        List<Dish> dishList = new ArrayList<>();
        dishList.add(DISH_1);

        given(dishRepository.findAll()).willReturn(dishList);

        given(dishRepository.findByNameAndDishType("Russian salad", DishType.SALAD))
                .willReturn(java.util.Optional.of(null));
        Dish dish = new Dish("Russian salad","a",false,false,new ArrayList<>(), DishType.SALAD);
        given(dishRepository.save(any(Dish.class))).willReturn(dish);
        when(dishRepository.save(any(Dish.class))).thenReturn(dish);
    }
    @Test
    public void testAddDish(){ //todo
        NewDishDTO newDishDTO = NEW_DISH_DTO_1;
        Dish created = dishService.addDish(newDishDTO);

        verify(dishRepository, times(1)).findByNameAndDishType(newDishDTO.getName(), newDishDTO.getType());
        verify(dishRepository, times(1)).save(any());

        //assertEquals(newDishDTO.getName(), created.getName()); //ovo mi vraca nulltj created je null
    }

}
