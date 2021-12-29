package com.ftn.restaurant.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ftn.restaurant.dto.DrinkDTO;
import com.ftn.restaurant.dto.NewDrinkDTO;
import com.ftn.restaurant.model.Drink;
import com.ftn.restaurant.model.enums.ContainerType;
import com.ftn.restaurant.model.enums.DrinkType;
import com.ftn.restaurant.service.DrinkService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;
import java.util.ArrayList;

@RunWith(SpringRunner.class)
@WebMvcTest
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DrinkControllerUnitTest {
    private static final String URL_PREFIX = "/api/drink";

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype());

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DrinkService drinkService;

    @Test
    public void shouldGetDrinkDTO() throws Exception {
        Drink drink  = new Drink("Pica", "some img", false, false, new ArrayList<>(), DrinkType.COLD_DRINK, ContainerType.GLASS);
        NewDrinkDTO newDrinkDTO = new NewDrinkDTO("Pica", "some img", DrinkType.COLD_DRINK, ContainerType.GLASS);
        Mockito.when(drinkService.addDrinkByBartender(newDrinkDTO)).thenReturn(drink);

        String json = json(newDrinkDTO);
        mockMvc.perform(post(URL_PREFIX).content(json).contentType(contentType)).andExpect(status().isCreated());
    }




    public static String json(Object object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        return mapper.writeValueAsString(object);
    }

}
