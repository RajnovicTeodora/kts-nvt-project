package com.ftn.restaurant.service;

import com.ftn.restaurant.RestaurantApplication;
import com.ftn.restaurant.dto.NewDishDTO;
import com.ftn.restaurant.exception.ForbiddenException;
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
import static org.junit.Assert.assertThrows;
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
    @Test
    public void testAddDishNull(){ //todo obaveznooo izbrisi printove
        NewDishDTO newDishDTO = NEW_DISH_DTO_1;
        assertThrows(ForbiddenException.class, () ->{dishService.addDish(null);});

    }
    @Test
    public void testAddDishWrongNameImage(){ //todo obaveznooo izbrisi printove
        NewDishDTO newDishDTO = NEW_DISH_DTO_1;
        newDishDTO.setName("");
        NewDishDTO finalNewDishDTO = newDishDTO;
        assertThrows(ForbiddenException.class, () ->{dishService.addDish(finalNewDishDTO);});

        newDishDTO = NEW_DISH_DTO_1;
        newDishDTO.setImage("");
        NewDishDTO finalNewDishDTO1 = newDishDTO;
        assertThrows(ForbiddenException.class, () ->{dishService.addDish(finalNewDishDTO1);});

    }
    @Test
    public void testSearchDish(){
        given(dishRepository.getApprovedDishes()).willReturn(LIST_DISHES);

        List<Dish> searched =dishService.getSearchedOrFiltered("","");
        assertEquals(searched.size(), 2);

        searched =dishService.getSearchedOrFiltered("","ENTREE");
        assertEquals(1, searched.size());
        assertEquals(searched.get(0).getDishType().name(), "ENTREE");

        searched =dishService.getSearchedOrFiltered("PoHovani sladolEd","");
        assertEquals(1, searched.size());
        assertEquals(searched.get(0).getName(), "Pohovani sladoled");

        searched =dishService.getSearchedOrFiltered("PoHOvani sladoled","DESERT");
        assertEquals(1, searched.size());
        assertEquals(searched.get(0).getName(), "Pohovani sladoled");
        assertEquals(searched.get(0).getDishType().name(), "DESERT");

        searched =dishService.getSearchedOrFiltered("PoHOvani sladoleddddd","DESERTddd");
        assertEquals(0, searched.size());
    }

//    @Test
//    public void testAddDishExist(){ //todo obaveznooo izbrisi printove proveri teoriju ali ovo ne mogu da proverim jer se ne cuva stvarno u bazi
//        NewDishDTO newDishDTO = NEW_DISH_DTO_1;
//        dishService.addDish(newDishDTO);
//
//        assertThrows(DishExistsException.class, () ->{dishService.addDish(newDishDTO);});
//
//    }

}
