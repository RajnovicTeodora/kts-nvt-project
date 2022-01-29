package com.ftn.restaurant.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ftn.restaurant.dto.DishDTO;
import com.ftn.restaurant.dto.DrinkDTO;
import com.ftn.restaurant.model.Dish;
import com.ftn.restaurant.model.MenuItemPrice;
import com.ftn.restaurant.model.enums.DishType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.ArrayList;

import static org.hamcrest.Matchers.hasSize;
import static com.ftn.restaurant.constants.NewDishDTOConstants.*;
import static com.ftn.restaurant.constants.NewDrinkDTOConstants.NEW_DRINK_DTO_1;
import static com.ftn.restaurant.controller.DrinkControllerUnitTest.json;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WithMockUser(username="slavkoo", roles= {"HEAD_CHEF"})
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class DishControllerIntegrationTest {

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype());

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }


    @Autowired
    private WebApplicationContext webApplicationContext;

    @Test
    public void addDishTest() throws Exception {
        String json = json(NEW_DISH_DTO_1);
        mockMvc.perform(post("/api/dish/addDish").content(json).contentType(contentType)).andExpect(status().isCreated());
        //adding same should be bad
        mockMvc.perform(post("/api/dish/addDish").content(json).contentType(contentType)).andExpect(status().isForbidden());
    }

    @Test
    public void addDishEmptyNameAndImageTest() throws Exception {
        String json = json(NEW_DISH_DTO_2);
        mockMvc.perform(post("/api/dish/addDish").content(json).contentType(contentType)).andExpect(status().isForbidden());
    }

    @Test
    public void searchingDish() throws Exception {
        mockMvc.perform(get("/api/dish/getSearchedOrFiltered").param("searchName", "Pizza").contentType(contentType))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$.[0].name").value("Pizza"));

        mockMvc.perform(get("/api/dish/getSearchedOrFiltered").param("filterName", "MAIN_DISH").contentType(contentType))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$.[0].name").value("Pizza"));

    }
    public static String json(Object object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        return mapper.writeValueAsString(object);
    }
}
