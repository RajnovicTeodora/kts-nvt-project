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
import java.nio.charset.Charset;

import static com.ftn.restaurant.constants.OrderDTOConstants.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithMockUser(username="waiter", roles= {"WAITER"})
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class OrderControllerIntegrationTest {

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }


    @Test
    public void createOrderTest() throws Exception {
        String dto = json(ORDER_DTO_1);

        this.mockMvc.perform(post("/api/order/createOrder")
                .contentType(contentType).content(dto))
                .andExpect(status().isCreated());

        /////////////////////////////////////////
        dto = json(ORDER_DTO_2);

        this.mockMvc.perform(post("/api/order/createOrder")
                .contentType(contentType).content(dto))
                .andExpect(status().isForbidden())
                .andExpect(content().string("Order has to contain ordered items."));

    }

    @Test
    public void updateOrderTest() throws Exception {
        String dto = json(ORDER_DTO_1);

        this.mockMvc.perform(post("/api/order/updateOrder/4")
                .contentType(contentType).content(dto))
                .andExpect(status().isOk());

        /////////////////////////////////////////

        this.mockMvc.perform(post("/api/order/updateOrder/5")
                .contentType(contentType).content(dto))
                .andExpect(status().isForbidden())
                .andExpect(content().string("Can't change order that is already paid."));

    }

    @Test
    public void setTotalPriceAndPayTest() throws Exception {
        mockMvc.perform(get("/api/order/payOrder/4"))
                .andExpect(status().isOk())
                .andExpect(content().string("Successfully paid order with id: 4"));

        /////////////////////////////////////////

        mockMvc.perform(get("/api/order/payOrder/5"))
                .andExpect(status().isForbidden())
                .andExpect(content().string("Order with id 5 is already paid."));

        /////////////////////////////////////////

        mockMvc.perform(get("/api/order/payOrder/-1"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Couldn't find order with id: -1"));

    }

    public static String json(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        return mapper.writeValueAsString(object);
    }
}
