package com.ftn.restaurant.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.Charset;

@WithMockUser(username="waiter", roles= {"WAITER"})
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class TableControllerIntegrationTest {

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
    public void occupyTableTest() throws Exception {
        mockMvc.perform(get("/api/table/1/occupyTable/waiter"))
                .andExpect(status().isOk())
                .andExpect(content().string("Successfully occupied table with id: 1"));

        //////////////////////////////

        mockMvc.perform(get("/api/table/2/occupyTable/waiter"))
                .andExpect(status().isForbidden())
                .andExpect(content().string("Table is already occupied"));

        //////////////////////////////

        mockMvc.perform(get("/api/table/1000/occupyTable/waiter"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Couldn't find table with id: 1000"));

    }

    @Test
    public void clearTableTest() throws Exception {
        mockMvc.perform(get("/api/table/5/clearTable/waiter"))
                .andExpect(status().isOk())
                .andExpect(content().string("Successfully cleared table with id: 5"));

        //////////////////////////////

        mockMvc.perform(get("/api/table/4/clearTable/waiter"))
                .andExpect(status().isForbidden())
                .andExpect(content().string("Table is already cleared"));

        //////////////////////////////

        mockMvc.perform(get("/api/table/1000/clearTable/waiter"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Couldn't find table with id: 1000"));

    }

    @Test
    public void claimTableTest() throws Exception {
        mockMvc.perform(get("/api/table/3/claimTable/waiter"))
                .andExpect(status().isOk())
                .andExpect(content().string("Successfully claimed table with id: 3"));

        //////////////////////////////

        mockMvc.perform(get("/api/table/2/claimTable/waiter"))
                .andExpect(status().isForbidden())
                .andExpect(content().string("Table is already claimed"));

        //////////////////////////////

        mockMvc.perform(get("/api/table/1000/claimTable/waiter"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Couldn't find table with id: 1000"));

    }

    @Test
    public void leaveTableTest() throws Exception {
        mockMvc.perform(get("/api/table/6/leaveTable/waiter"))
                .andExpect(status().isOk())
                .andExpect(content().string("Successfully left table with id: 6"));

        //////////////////////////////

        mockMvc.perform(get("/api/table/2/leaveTable/waiter"))
                .andExpect(status().isForbidden())
                .andExpect(content().string("Can't leave table while its occupied"));

        //////////////////////////////

        mockMvc.perform(get("/api/table/1000/leaveTable/waiter"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Couldn't find table with id: 1000"));

    }

}
