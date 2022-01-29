package com.ftn.restaurant.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ftn.restaurant.dto.OrderItemDTO;
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
import org.springframework.test.annotation.TestAnnotationUtils;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

import static com.ftn.restaurant.constants.OrderDTOConstants.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithMockUser(username="waiter", roles= {"WAITER"})
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class OrderedItemControllerIntegrationTest {

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Autowired
    private TestRestTemplate restTemplate;


    @Test
    public void confirmPickupTest() throws Exception {
        mockMvc.perform(get("/api/orderedItem/confirmPickup/2"))
                .andExpect(status().isOk())
                .andExpect(content().string("Successfully delivered ordered item with id: 2"));

        //////////////////

        mockMvc.perform(get("/api/orderedItem/confirmPickup/-1"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Couldn't find ordered item."));

        //////////////////

        mockMvc.perform(get("/api/orderedItem/confirmPickup/3"))
                .andExpect(status().isForbidden())
                .andExpect(content().string("Can't deliver ordered item when status is not READY."));

        //////////////////

        mockMvc.perform(get("/api/orderedItem/confirmPickup/6"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Can't deliver DELETED ordered item."));
    }

    @Test
    public void deleteOrderedItemTest() throws Exception {
        mockMvc.perform(get("/api/orderedItem/setDeleted/5"))
                .andExpect(status().isOk())
                .andExpect(content().string("Successfully deleted ordered item with id: 5"));

        //////////////////

        mockMvc.perform(get("/api/orderedItem/setDeleted/-1"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Couldn't find ordered item."));

        //////////////////

        mockMvc.perform(get("/api/orderedItem/setDeleted/6"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Already deleted ordered item with id: 6"));

        //////////////////

        mockMvc.perform(get("/api/orderedItem/setDeleted/4"))
                .andExpect(status().isForbidden())
                .andExpect(content().string("Can't delete ordered item with id: 4"));

    }

    @Test
    public void getOrderedItemsForOrderIdTest() throws Exception {
        mockMvc.perform(get("/api/orderedItem/getOrderedItemsForOrderId/6"))
                .andExpect(status().isOk());

        /////////////////

        mockMvc.perform(get("/api/orderedItem/getOrderedItemsForOrderId/-1"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Couldn't find order"));
    }

    public static String json(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        return mapper.writeValueAsString(object);
    }
}
