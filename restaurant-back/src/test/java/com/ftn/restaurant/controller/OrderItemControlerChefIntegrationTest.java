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

import java.io.IOException;

import static com.ftn.restaurant.constants.NewDishDTOConstants.NEW_DISH_DTO_1;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithMockUser(username="chef", roles= {"CHEF"})
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class OrderItemControlerChefIntegrationTest {
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
    public void acceptItemTest() throws Exception {
        String json = json(5);//.content(json).contentType(contentType)
        mockMvc.perform(post("/api/orderedItem/acceptOrderedItem/5/chef")).andExpect(status().isOk())
                .andExpect(content().string("You accepted order Ice Latte"));

        mockMvc.perform(post("/api/orderedItem/acceptOrderedItem/5/chef")).andExpect(status().isOk())
                .andExpect(content().string("You can't accept order if it is not in status ordered."));

        mockMvc.perform(post("/api/orderedItem/acceptOrderedItem/-5/chef")).andExpect(status().isOk())
                .andExpect(content().string("Order doesn't exists"));
    }

        //findAllAcceptedByOrderIdDTO
//        @Test
//        public void findAllAcceptedItemTest() throws Exception {
//            String json = json(5);//.content(json).contentType(contentType)
//            mockMvc.perform(post("/api/orderedItem/getAccepted/3/chef")).andExpect(status().isOk())
//                    .andExpect(content().string("You finished order Ice Latte"));
//
//
//        }

    @Test
    public void finishItemTest() throws Exception {
        String json = json(5);//.content(json).contentType(contentType)
        mockMvc.perform(post("/api/orderedItem/finishOrderedItem/5")).andExpect(status().isOk())
                .andExpect(content().string("You finished order Ice Latte"));

        mockMvc.perform(post("/api/orderedItem/finishOrderedItem/5")).andExpect(status().isOk())
                .andExpect(content().string("You can't finish order if it is not in status in progres."));

        mockMvc.perform(post("/api/orderedItem/finishOrderedItem/-5")).andExpect(status().isOk())
                .andExpect(content().string("Order doesn't exists"));
    }


    public static String json(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        return mapper.writeValueAsString(object);
    }
}
