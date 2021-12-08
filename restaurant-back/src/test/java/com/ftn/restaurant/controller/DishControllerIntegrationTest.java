package com.ftn.restaurant.controller;

import com.ftn.restaurant.dto.DishDTO;
import com.ftn.restaurant.dto.DrinkDTO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static com.ftn.restaurant.constants.NewDishDTOConstants.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class DishControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void addDishTest(){

        ResponseEntity<DishDTO> responseEntity = restTemplate
                .postForEntity("/api/dish/addDish", NEW_DISH_DTO_1, DishDTO.class);

        DishDTO dish = responseEntity.getBody();

        Assert.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        Assert.assertEquals(NEW_DISH_DTO_1.getName(), dish.getName());


        responseEntity = restTemplate
                .postForEntity("/api/dish/addDish", NEW_DISH_DTO_1, DishDTO.class);

        dish = responseEntity.getBody();

        Assert.assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
        Assert.assertNull(dish.getName());

    }

    @Test
    public void addDishTestNull(){
        ResponseEntity<DishDTO> responseEntity = restTemplate
                .postForEntity("/api/dish/addDish", NEW_DISH_DTO_2, DishDTO.class);

        DishDTO dish = responseEntity.getBody();
        Assert.assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
        Assert.assertNull(dish.getName());

        //// todo da li je ok da mi vrati 415 za null?
//        responseEntity = restTemplate
//                .postForEntity("/api/dish/addDish", NEW_DISH_DTO_3, DishDTO.class);
//
//        dish = responseEntity.getBody();
//        Assert.assertEquals(HttpStatus.UNSUPPORTED_MEDIA_TYPE, responseEntity.getStatusCode());
//        Assert.assertNull(dish.getName());
    }
}
