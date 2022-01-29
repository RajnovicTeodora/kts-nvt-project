package com.ftn.restaurant.controller;

import static com.ftn.restaurant.controller.DrinkControllerUnitTest.json;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.nio.charset.Charset;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;

import static com.ftn.restaurant.constants.NewDrinkDTOConstants.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WithMockUser(username="misko", roles= {"BARTENDER"})
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class DrinkControllerIntegrationTest {

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype());

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }


    @Test
    public void addDrinkTest() throws Exception {
        String json = json(NEW_DRINK_DTO_1);
        mockMvc.perform(post("/api/drink/addDrink").content(json).contentType(contentType)).andExpect(status().isCreated());

        mockMvc.perform(post("/api/drink/addDrink").content(json)
                .contentType(contentType)).andExpect(status().isForbidden());
    }

    @Test
    public void addDrinkFalseParameters() throws Exception {
        String json = json(NEW_DRINK_DTO_2);
        mockMvc.perform(post("/api/drink/addDrink").content(json)
                .contentType(contentType))
                .andExpect(status().isForbidden());

    }
    public static String json(Object object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        return mapper.writeValueAsString(object);
    }

    @Test
    public void searchingDrink() throws Exception {

        mockMvc.perform(get("/api/drink/getSearchedOrFiltered").param("searchName", "Lemonade").contentType(contentType))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$.[0].name").value("Lemonade"));

        mockMvc.perform(get("/api/drink/getSearchedOrFiltered").param("filterName", "COLD_DRINK").contentType(contentType))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(2)));

    }
}
