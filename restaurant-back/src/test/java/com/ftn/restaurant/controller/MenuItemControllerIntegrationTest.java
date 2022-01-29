package com.ftn.restaurant.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
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

import static com.ftn.restaurant.constants.NewDrinkDTOConstants.NEW_DRINK_DTO_1;
import static com.ftn.restaurant.constants.NewDrinkDTOConstants.NEW_DRINK_DTO_2;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.io.IOException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WithMockUser(username="manager", roles= {"MANAGER"})
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class MenuItemControllerIntegrationTest {

    private final MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype());

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }


    @Test
    public void deleteMenuItem() throws Exception {

        this.mockMvc.perform(delete("/api/menuItem/deleteMenuItem/8")
                        .contentType(contentType))
                .andExpect(status().isOk());

        /////////////////////////////////////////
        this.mockMvc.perform(delete("/api/menuItem/deleteMenuItem/12")
                        .contentType(contentType))
                .andExpect(status().isNotFound());

        /////////////////////////////////////////
        this.mockMvc.perform(delete("/api/menuItem/deleteMenuItem/6")
                        .contentType(contentType))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testApproveMenuItem() throws Exception {
        this.mockMvc.perform(get("/api/menuItem/approveMenuItem/5")
                        .contentType(contentType))
                .andDo(print())
                .andExpect(status().isOk());

        ////////////////////////////////////////

        this.mockMvc.perform(get("/api/menuItem/approveMenuItem/6")
                        .contentType(contentType))
                .andExpect(status().isNotFound());
    }

    @Test
    public void addDrinkTest() throws Exception {
        String json = json(NEW_DRINK_DTO_1);
        mockMvc.perform(post("/api/menuItem/addDrink").content(json)
                .contentType(contentType)).andExpect(status().isCreated())
                .andDo(print());

    }

    @Test
    public void addDrinkFalseParameters() throws Exception {
        String json = json(NEW_DRINK_DTO_2);
        mockMvc.perform(post("/api/menuItem/addDrink").content(json)
                        .contentType(contentType))
                .andDo(print())
                .andExpect(status().isForbidden());

    }


    public static String json(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        return mapper.writeValueAsString(object);
    }
}
