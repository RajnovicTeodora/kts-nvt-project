package com.ftn.restaurant.controller;

import com.ftn.restaurant.dto.DrinkDTO;
import com.ftn.restaurant.dto.NewDrinkDTO;
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

import static com.ftn.restaurant.constants.NewDrinkDTOConstants.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@TestPropertySource("classpath:application-test.properties")
public class DrinkControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void addDrinkTest(){

        ResponseEntity<DrinkDTO> responseEntity = restTemplate
                .postForEntity("/api/drink/addDrink", NEW_DRINK_DTO_1, DrinkDTO.class);

        DrinkDTO drink = responseEntity.getBody();

        Assert.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        Assert.assertEquals(NEW_DRINK_DTO_1.getName(), drink.getName());

    }

    @Test
    public void addDrinkFalse(){

        ResponseEntity<DrinkDTO> responseEntity = restTemplate
                .postForEntity("/api/drink/addDrink", NEW_DRINK_DTO_2, DrinkDTO.class);

        DrinkDTO drink = responseEntity.getBody();

        Assert.assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
        Assert.assertNull(drink.getName());

        ///
//        responseEntity = restTemplate
//                .postForEntity("/api/drink/addDrink", NEW_DRINK_DTO_3, DrinkDTO.class);
//
//        drink = responseEntity.getBody();
//
//        Assert.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
//        Assert.assertNull(drink.getName());


    }
}
